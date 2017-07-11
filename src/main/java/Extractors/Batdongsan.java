package Extractors;

import Crawler.URLManager;
import Data.Page;
import Data.TargetURL;
import Drivers.Driver;
import Settings.Config;
import org.openqa.selenium.*;

import java.util.List;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class Batdongsan extends PageExtractor {
    public Batdongsan(){
        super();
        this.name = "Batdongsan";
        listPageSelector = "//div[contains(@class,'search-productItem')]/div[1]/a";
        postSelector = "product-detail";
    }

    @Override
    public void getLinks(Page page, Driver driver){
        try{
            driver.goTo(page.getTargetUrl().getUrl());
            driver.click("//div[contains(@class,'Repeat')]/div[1]/select/option[2]");
            List<String> urls = driver.getLinks(listPageSelector);
            for(String url: urls){
                TargetURL newTarget = new TargetURL(url, TargetURL.URLTYPE.POST);
                URLManager.getInstance().addFreshURL(newTarget);
            }
        }
        catch (Exception e){
            Config.pageFail ++;
            System.out.println("FAIL LIST " + Integer.toString(Config.pageFail));
            if (!(e instanceof TimeoutException) && !(e instanceof NoSuchElementException))
                System.out.println(e.getMessage());
        }
    }
}
