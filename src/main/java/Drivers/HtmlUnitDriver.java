package Drivers;

import Settings.Config;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thaison1496 on 14/7/16.
 */
public class HtmlUnitDriver implements Driver {
    private WebClient driver;
    private HtmlPage page;
    private String url;

    public HtmlUnitDriver(boolean withScript){
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.setJavaScriptTimeout(0);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(withScript);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(Config.pageLoadTimeout * 1000);

        driver = webClient;
    }

    public void goTo(String url) throws Exception{
        driver.close();
        page = driver.getPage(url);
    }

    public List<String> getLinks(String xpath) {
        List<String> result = new ArrayList<String>();
        try{
            List<?> elements = page.getByXPath(xpath);
            for(Object element: elements){
                HtmlAnchor htmlElement = (HtmlAnchor) element;
                url = htmlElement.getHrefAttribute();
                if (!url.contains(page.getUrl().getHost()))
                    url = page.getUrl().getHost() + url;
                if (!url.contains("http:")){
                    if (url.startsWith("/")) url = "http:" + url;
                    else url = "http://" + url;
                }
                result.add( url );
            }
            return result;
        }
        catch (Exception e){ return null; }
    }

    public String getText(String id) {
        try{
            return page.getElementById(id).asText();
        }
        catch (Exception e){ return null; }
    }

    public String getTextByClass(String className) {
        try{
            DomElement element = (DomElement) page.getByXPath("//*[@class='" + className + "']").get(0);
            return element.asText();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void click(String xpath) {
        try{
            HtmlOption element = (HtmlOption) page.getByXPath(xpath).get(0);
            element.setSelected(true);
            goTo(page.getBaseURL().toString());
        }
        catch (Exception e){ }
    }

    public void close() {
        driver.close();
    }
}
