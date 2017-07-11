package Settings;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class DateThread extends Thread {
    @Override
    public void run(){
        while (true){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            String today = LocalDateTime.now().format(formatter);
            if (today.equals(Config.today)){
                try { sleep(300 * 1000); } catch (InterruptedException e) {}
                continue;
            }
            try{
                Config.today = today;
                Config.outputPath = "result/" + Config.today + ".txt";
                Config.outputInfoPath = "result/" + Config.today + "_info.txt";

                if (Config.writer != null)
                    Config.writer.close();
                Config.writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(Config.outputPath, true), "utf-8"));

                if (Config.writer2 != null)
                    Config.writer2.close();
                Config.writer2 = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(Config.outputInfoPath, true), "utf-8"));

            }
            catch (Exception e){e.printStackTrace();}
        }

    }
}
