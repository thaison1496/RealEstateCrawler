package Crawler;

import Data.Page;
import Data.TargetURL;
import Drivers.Driver;
import Settings.Config;
import org.openqa.selenium.WebDriver;

/**
 * Created by thaison1496 on 11/7/16.
 */
public class ScraperThread extends Thread {
    private Driver driver1;
    private Driver driver2;
    public boolean running = true;

    public void setDrivers(){
        closeDrivers();
        this.driver1 = Config.getDriver(true);
        this.driver2 = Config.getDriver(false);
    }

    public void closeDrivers(){
        if (this.driver1 != null ) this.driver1.close();
        if (this.driver2 != null ) this.driver2.close();
    }

    @Override
    public void run(){
        try { sleep(Config.scraperLongSleep * 1000); } catch (InterruptedException e) {}
        while (running){
            TargetURL targetUrl = URLManager.getInstance().getFreshURL();
            if(targetUrl == null){
                // If there is no more fresh  url(s) in DB, do a medium sleep
                try { sleep(Config.scraperShortSleep * 1000); } catch (InterruptedException e) {}
                continue;
            } else if(URLManager.getInstance().isScraped(targetUrl.getUrl())){
                try { sleep(Config.scraperShortSleep * 1000); } catch (InterruptedException e) {}
                continue;
            }

            if (targetUrl.getType() == TargetURL.URLTYPE.POST)
                URLManager.getInstance().markAsScraped(targetUrl.getUrl());
            else{
                URLManager.getInstance().addFreshURL(new TargetURL(targetUrl.getUrl(), targetUrl.getType()));
            }

            Page page = new Page(targetUrl, driver1, driver2);
            page.process();

            try { sleep(Config.scraperShortSleep * 1000); } catch (InterruptedException e) {}
        }

    }
}
