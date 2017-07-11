package Extractors;

import Data.Page;
import Data.TargetURL;
import Crawler.URLManager;
import Drivers.Driver;
import Settings.Config;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class PageExtractor {
    protected String listPageSelector;
    protected String postSelector;
    public String name;

    public void getLinks(Page page, Driver driver){
        try{
            driver.goTo(page.getTargetUrl().getUrl());
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

    public String getContent(Page page, Driver driver){
        try{
            driver.goTo(page.getTargetUrl().getUrl());
            return  driver.getText(postSelector);
        }
        catch (Exception e){
            Config.pageFail ++;
            System.out.println("FAIL POST " + Integer.toString(Config.pageFail));
            if (!(e instanceof TimeoutException) && !(e instanceof NoSuchElementException))
                System.out.println(e.getMessage());
            return null;
        }
    }

    public synchronized static PageExtractor getSite(String url){
        if (url.contains("rongbay.com"))
            return new Rongbay();
        if (url.contains("enbac.com"))
            return new Enbac();
        return new Batdongsan();
    }

    public String getInfo(String text){
        List<String> phones = new ArrayList<String>();
        List<String> emails = new ArrayList<String>();
        String result = "", tmp;

        text = text.replaceAll("(\\d) (\\d)","$1$2").replaceAll("[\\n ]","_");

        Matcher m = Pattern.compile("(84|0)(9\\d{7,8}|1\\d{9})")
                .matcher(text);
        while (m.find()) { tmp = m.group(); if (!phones.contains(tmp)) phones.add(tmp); }

        m = Pattern.compile("[a-zA-Z0-9.+]*@[a-zA-Z0-9.]*")
                .matcher(text);
        while (m.find()) { tmp = m.group(); if (!emails.contains(tmp)) emails.add(tmp); }

        for(String phone: phones) result += phone + " ";
        result += "\n";
        for(String email: emails) result += email + " ";
        return result;
    }
}
