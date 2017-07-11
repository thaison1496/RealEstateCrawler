package Crawler;

import Data.TargetURL;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class URLManager {
    private ConcurrentLinkedQueue<TargetURL> freshURLQueue;
    private TreeSet<String> scrapedURLSet;

    private static URLManager instance = null;
    public static URLManager getInstance(){
        if(instance == null) instance = new URLManager();
        return instance;
    }

    public URLManager(){
        freshURLQueue = new ConcurrentLinkedQueue<TargetURL>();
        scrapedURLSet = new TreeSet<String>();
    }

    public synchronized void addFreshURL(TargetURL targetUrl){
        if (freshURLQueue.size() > 200 && freshURLQueue.size() % 50 == 0)
            System.out.println("QUEUE: " + Integer.toString(freshURLQueue.size()));
        freshURLQueue.add(targetUrl);
    }

    public synchronized TargetURL getFreshURL(){
        return freshURLQueue.poll();
    }

    public synchronized void markAsScraped(String url){
        scrapedURLSet.add(url);
    }

    public synchronized boolean isScraped(String url){
        return scrapedURLSet.contains(url);
    }
}
