package Settings;

import Crawler.ScraperThread;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.*;
import java.net.InetAddress;

/**
 * Created by Admin on 18/7/2016.
 */
public class ConfigThread extends Thread {
    private enum Variable {
        firefoxPath, scraperThreadsNum, pageLoadTimeout, DRIVERTYPE;
    }

    private boolean readConfig() throws Exception{
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("config.txt"), "utf-8"));
        String line, value;
        Variable variable;
        int i = 0;
        boolean modified = false;

        while (true){
            line = reader.readLine();
            if (line == null || line == "") break;
            if (!line.equals(Config.configLines[i])){
                Config.configLines[i] = line;
                modified = true;
                System.out.println(line);
            }
            variable = Variable.valueOf(line.split(" ")[0]);
            value = line.split(" ")[1];
            switch (variable){
                case firefoxPath: Config.firefoxPath = value; break;
                case scraperThreadsNum: Config.scraperThreadsNum = Integer.parseInt(value); break;
                case pageLoadTimeout: Config.pageLoadTimeout = Integer.parseInt(value); break;
                case DRIVERTYPE: Config.driverType = Config.DRIVERTYPE.valueOf(value); break;
            }
            i ++ ;
        }
        reader.close();
        return modified;
    }

    public void run(){
        try{
            Config.elasticClient = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        }
        catch (Exception e){}

        while (true) {
            try {
                if (!readConfig()){
                    try { sleep(20 * 1000); } catch (InterruptedException e) {}
                    continue;
                }
                while (Config.scraperThreads.size() > Config.scraperThreadsNum){
                    ScraperThread lastThread = Config.scraperThreads.get(Config.scraperThreads.size());
                    lastThread.running = false;
                    Config.scraperThreads.remove(lastThread);
                }

                for(ScraperThread scraperThread: Config.scraperThreads){
                    scraperThread.setDrivers();
                }

                while (Config.scraperThreads.size() < Config.scraperThreadsNum){
                    ScraperThread scraperThread = new ScraperThread();
                    Config.scraperThreads.add(scraperThread);
                    scraperThread.setDrivers();
                    scraperThread.start();
                }
            } catch (Exception e) {
                System.out.println("CONFIG FAILED");
                e.printStackTrace();
            }
            try { sleep(60 * 1000); } catch (InterruptedException e) {}
        }
    }
}
