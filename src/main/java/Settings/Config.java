package Settings;

import Crawler.ScraperThread;
import Drivers.Driver;
import Drivers.HtmlUnitDriver;
import Drivers.SeleniumDriver;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.BufferedWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class Config {
    public static String firefoxPath = "../../firefox46/firefox/firefox.exe";
    public static String outputPath = "result/out.txt";
    public static String outputInfoPath = "result/out.txt";

    public static  String today = "";

    public static BufferedWriter writer = null;
    public static BufferedWriter writer2 = null;

    public static int scraperThreadsNum = 1;
    public static int analyzerThreadsNum = 1;

    public static int scraperShortSleep = 1;
    public static int scraperMediumSleep = 5;
    public static int scraperLongSleep = 10;

    public static int analyzerShortSleep = 1;
    public static int analyzerMediumSleep = 5;

    public static int pageCount = 0;
    public static int pageFail = 0;

    public static int pageLoadTimeout;

    public static Client elasticClient;

    public static enum DRIVERTYPE {SELENIUM, HTMLUNIT}
    public static DRIVERTYPE driverType = DRIVERTYPE.HTMLUNIT;

    public static TreeSet<String> needScript = new TreeSet<String>() {{
        add("BatdongsanLISTPAGE"); add("BatdongsanPOST");}};

    public static List<ScraperThread> scraperThreads = new ArrayList<ScraperThread>();

    public static String[] configLines = new String[10];

    public synchronized static Driver getDriver(boolean withScript){
        switch (driverType){
            case SELENIUM: return new SeleniumDriver(withScript);
            default: HTMLUNIT: return new HtmlUnitDriver(withScript);
        }
    }
}
