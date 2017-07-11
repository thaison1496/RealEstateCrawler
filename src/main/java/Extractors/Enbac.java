package Extractors;

import Data.Page;
import Drivers.Driver;
import Settings.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class Enbac extends PageExtractor {
    public Enbac(){
        super();
        this.name = "Enbac";
        listPageSelector = "//li[contains(@class,'rd_view')]/div[1]/h2/a";
        postSelector = "center_bds_detailnew";
    }

    public String getContent(Page page, Driver driver){
        try{
            driver.goTo(page.getTargetUrl().getUrl());
            return  driver.getTextByClass(postSelector);
        }
        catch (Exception e){
            Config.pageFail ++;
            System.out.println("FAIL POST " + Integer.toString(Config.pageFail));
            if (!(e instanceof TimeoutException) && !(e instanceof NoSuchElementException))
                System.out.println(e.getMessage());
            return null;
        }
    }
}
