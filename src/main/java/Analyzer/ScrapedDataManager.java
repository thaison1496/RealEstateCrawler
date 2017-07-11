package Analyzer;

import Data.Page;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class ScrapedDataManager {
    private ConcurrentLinkedQueue<Page> ScrapedData;

    private static ScrapedDataManager instance = null;

    public static ScrapedDataManager getInstance(){
        if(instance == null) instance = new ScrapedDataManager();
        return instance;
    }

    public ScrapedDataManager(){
        ScrapedData = new ConcurrentLinkedQueue<Page>();
    }

    public synchronized void addWebPage(Page wp){ ScrapedData.add(wp); }

    public synchronized Page getWebPage(){
        return ScrapedData.poll();
    }
}
