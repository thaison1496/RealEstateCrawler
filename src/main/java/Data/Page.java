package Data;

import Drivers.Driver;
import Extractors.PageExtractor;
import Settings.Config;
import com.google.gson.*;
import org.elasticsearch.action.index.IndexRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class Page {
    private TargetURL targetUrl;
    private PageExtractor extractor;
    private String content;
    private Driver driver;

    public Page(TargetURL url, Driver driver1, Driver driver2){
        this.targetUrl = url;
        this.extractor = PageExtractor.getSite(url.getUrl());
        if (Config.needScript.contains(extractor.name + this.targetUrl.getType().toString())){
            this.driver = driver1;
        } else
            this.driver = driver2;
    }

    public TargetURL getTargetUrl() { return targetUrl; }

    public void process(){
        if (extractor == null) return;
        if (targetUrl.getType() == TargetURL.URLTYPE.LISTPAGE){
            extractor.getLinks(this, driver);
        }
        else{
            content = extractor.getContent(this, driver);
            if (content != null){
                Config.pageCount ++;
                writeToDB(Config.pageCount);
            }
        }
    }

    public void writeToFile(int index){
        System.out.println(index);
        System.out.println(getTargetUrl().getUrl());
        try {
            Config.writer.write("# " + Integer.toString(index));
            Config.writer.newLine();
            Config.writer.write(content);
            Config.writer.newLine();
            Config.writer.flush();

            Config.writer2.write("# " + Integer.toString(index));
            Config.writer2.newLine();
            Config.writer2.write(extractor.getInfo(content));
            Config.writer2.newLine();
            Config.writer2.flush();
        } catch (IOException e) {e.printStackTrace();}
    }

    public void writeToDB(int index){
        System.out.println(index);
        System.out.println(getTargetUrl().getUrl());

        String info = extractor.getInfo(content);
        JsonObject json = new JsonObject();

        try{
            JsonArray phones = new JsonArray();
            for(String phone: info.split("\\n")[0].split(" "))
                phones.add(new JsonPrimitive(phone));
            json.add("phones",phones);
        }
        catch (Exception e){ json.add("phones",new JsonArray()); }

        try{
            JsonArray emails = new JsonArray();
            for(String email: info.split("\\n")[1].split(" "))
                emails.add(new JsonPrimitive(email));
            json.add("emails",emails);
        }
        catch (Exception e){ json.add("emails",new JsonArray()); }


        json.add("no",new JsonPrimitive(index));
        json.add("url",new JsonPrimitive(getTargetUrl().getUrl()));
        json.add("date",new JsonPrimitive(
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())) ));
        json.add("content",new JsonPrimitive(content));

        Gson builder = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        //System.out.println(builder.toJson(json));

        IndexRequest indexRequest = new IndexRequest("scraped", "nhadat",
                UUID.randomUUID().toString());
        indexRequest.source(builder.toJson(json));
        Config.elasticClient.index(indexRequest).actionGet();
    }
}
