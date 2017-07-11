import Data.TargetURL;
import Drivers.HtmlUnitDriver;
import Extractors.PageExtractor;
import Settings.Config;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import com.gargoylesoftware.htmlunit.html.impl.SimpleRange;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thaison1496 on 11/7/16.
 */
public class Test {
    public static List<String> phones = new ArrayList<String>();

    public static void main(String args[]) throws Exception{
        HtmlUnitDriver driver = new HtmlUnitDriver(true);
        driver.goTo("http://enbac.com/Ho-Chi-Minh/Mua-ban-nha-dat/p2752879/Ban-nha-dien-tich-nho-3-5x8m-hem-836-Huong-Lo-2.html");
        System.out.println(driver.getTextByClass("center_bds_detailnew"));

/*        List<String> phones = new ArrayList<String>();
        phones.add("\"12331\"");
        phones.add("222");
        System.out.println(phones.toString());

        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        String json = "{\n" +
                "\t\"name\": \"ame\",\n" +
                "\t\"phone\":[12345,22226],\n" +
                "\t\"game\": {\"type1\": \"acc\",\"type2\":\"gosh\"}\n" +
                "}";
        IndexRequest indexRequest = new IndexRequest("places", "restaurant",
                UUID.randomUUID().toString());
        indexRequest.source(json);
        IndexResponse response = client.index(indexRequest).actionGet();
        System.out.println(response.isCreated());*/

        /*SearchResponse searchResponse =
                client.prepareSearch("scraped").setTypes("nhadat").execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for(SearchHit hit: hits){
            try{
                System.out.println( hit.getSource().toString());
            }
            catch (Exception e){}
        }*/
    }


    public static String getInfo(String text){
        //List<String> phones = new ArrayList<String>();
        String result = "", tmp;

        text = text.replaceAll("(\\d) (\\d)","$1$2").replaceAll("[\\n ]","_");

        Matcher m = Pattern.compile("(84|0)(9\\d{7,8}|1\\d{9})")
                .matcher(text);
        while (m.find()) { tmp = m.group(); if (!phones.contains(tmp)) phones.add(tmp); }


        //for(String phone: phones) result += phone + "\n";
        return result;
    }

    public static void readfile(String filename, BufferedWriter writer) throws Exception{
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), "utf-8"));
        String line;

        while (true) {
            line = reader.readLine();
            if (line == null) break;
            line = getInfo(line);
            if (line == "") continue;
            boolean flag = true;
            //for(String phone: phones) if (phone.equals(line)){
            //    flag = false;
            //    break;
            //}
            //if (flag)
            //    phones.add(line);
        }
    }
}
/**
 * server rat ko on dinh, ko the chay crawler ca ngay
 *
 */

/*
WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
			webClient.getOptions().setTimeout(5000);

			HtmlPage page = webClient.getPage("https://m.facebook.com");
			HtmlTextInput usernameInput = page.getElementByName("email");
			usernameInput.setValueAttribute(loginRequest.getUsername());

			HtmlPasswordInput passwordInput = page.getElementByName("pass");
			passwordInput.setValueAttribute(loginRequest.getPassword());

			HtmlSubmitInput submitButton = page.getElementByName("login");
			HtmlPage page2 = submitButton.click();

			Response response = new Response(-1, "Login failed for user!");;
			String cookieStr = "";

			for(Cookie cookie: webClient.getCookieManager().getCookies()){
				cookieStr += cookie.toString();
			}
 */

/*          try {
                    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
                    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

                    WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    webClient.getOptions().setCssEnabled(false);
                    //webClient.getOptions().setAppletEnabled(false);

                    long st = System.currentTimeMillis();
                    HtmlPage page = webClient.getPage("http://rongbay.com/Ha-Noi/Mua-Ban-nha-dat-c15.html");
                    webClient.getPage("http://rongbay.com/Ha-Noi/Mua-Ban-nha-dat-c15.html");
                    System.out.println(System.currentTimeMillis() - st);
                //System.out.println(page.asXml());

            }
            catch (Exception e){
                    System.out.println("ZZZZ");
                    e.printStackTrace();};

        try {
            long st = System.currentTimeMillis();
            WebDriver driver = Config.ffdriver(false);
            driver.navigate().to("http://rongbay.com/Ha-Noi/Mua-Ban-nha-dat-c15.html");
            driver.navigate().to("http://rongbay.com/Ha-Noi/Mua-Ban-nha-dat-c15.html");
            WebElement body = driver.findElement(By.xpath("/html"));
            System.out.println(System.currentTimeMillis() - st);
            //System.out.println(body.getText());
        }
        catch (Exception e){
            System.out.println("ZZZZ");
            e.printStackTrace();};*/
/*
        File folder = new File("../scraped_data");
        File[] listOfFiles = folder.listFiles();

        try{
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("../phones1.txt"), "utf-8"));

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    readfile("../scraped_data/"+listOfFiles[i].getName(), writer);
                }
            }
            System.out.println(phones.size());
            int i = 0;
            for(String phone: phones){
                System.out.println(phone);
                writer.write(phone + "\n");
                i++;
            }

            writer.close();
        }
        catch (Exception e){}

 */