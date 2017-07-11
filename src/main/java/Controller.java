import Data.TargetURL;
import Crawler.URLManager;
import Settings.ConfigThread;
import Settings.DateThread;

/**
 * Created by thaison1496 on 11/7/16.
 */
public class Controller{
    public static void main(String args[]){
        //URLManager.getInstance().addFreshURL(
        //        new TargetURL("http://rongbay.com/Mua-Ban-nha-dat-c15.html", TargetURL.URLTYPE.LISTPAGE));
        //URLManager.getInstance().addFreshURL(
        //        new TargetURL("http://enbac.com/Toan-Quoc/c103/Mua-ban-nha-dat", TargetURL.URLTYPE.LISTPAGE));
        URLManager.getInstance().addFreshURL(
                new TargetURL("http://batdongsan.com.vn/nha-dat-ban", TargetURL.URLTYPE.LISTPAGE));

        ConfigThread configThread = new ConfigThread();
        configThread.start();
    }
}
