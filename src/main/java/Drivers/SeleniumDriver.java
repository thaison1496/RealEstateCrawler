package Drivers;

import Settings.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by thaison1496 on 14/7/16.
 */
public class SeleniumDriver implements Driver {
    private WebDriver driver;

    public SeleniumDriver(boolean withScript){
        File pathToBinary = new File(Config.firefoxPath);
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);

        FirefoxProfile firefoxProfile = new FirefoxProfile();

        //firefoxProfile.setPreference("javascript.enabled", false);
        firefoxProfile.setPreference("plugin.state.flash", 0);
        firefoxProfile.setPreference("permissions.default.stylesheet", 2);
        firefoxProfile.setPreference("permissions.default.image", 2);
        firefoxProfile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);

        if (!withScript){
            File addOnFile = new File("noscript-2.9.0.11.xpi");
            try {
                firefoxProfile.addExtension( addOnFile);
            } catch (IOException e) { System.err.println("Cannot add addon"); }
        }

        driver = new FirefoxDriver(firefoxBinary, firefoxProfile);
        driver.manage().timeouts().pageLoadTimeout(Config.pageLoadTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
    }

    public void goTo(String url) throws Exception{
        driver.navigate().to(url);
    }

    public List<String> getLinks(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        List<String> urls = new ArrayList<String>();
        for(WebElement element: elements){
            urls.add(element.getAttribute("href"));
        }
        return urls;
    }

    public String getText(String id) {
        try{
            WebElement element = driver.findElement(By.id(id));
            return element.getText();
        }
        catch (Exception e){
            Config.pageFail ++;
            System.out.println("FAIL " + Integer.toString(Config.pageFail));
        }
        return null;
    }

    public String getTextByClass(String className) {
        try{
            WebElement element = driver.findElement(By.className(className));
            return element.getText();
        }
        catch (Exception e){
            Config.pageFail ++;
            System.out.println("FAIL " + Integer.toString(Config.pageFail));
        }
        return null;
    }

    public void click(String xpath) {
        try{
            WebElement element = driver.findElement(By.xpath(xpath));
            element.click();
        }
        catch (Exception e){}
    }

    public void close() {
        driver.close();
    }
}
