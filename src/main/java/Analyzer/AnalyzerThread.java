package Analyzer;

import Data.Page;
import Data.TargetURL;
import Settings.Config;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class AnalyzerThread extends Thread{
    @Override
    public void run(){
        while(true){
            Page page = ScrapedDataManager.getInstance().getWebPage();
            if(page == null){
                // If there is no more web page(s) in DB, do a medium sleep
                try { sleep(Config.analyzerMediumSleep * 1000); } catch (InterruptedException e) {}
                continue;
            }

            //TODO: Extract/Analyze information such as: phone, email and new fresh url(s)
            if(page.getTargetUrl().getType() == TargetURL.URLTYPE.LISTPAGE){
                //PageExtractor.getInstance().getLinks(page);
            } else {

            }

            try { sleep(Config.analyzerShortSleep * 1000); } catch (InterruptedException e) {}
        }
    }
}
