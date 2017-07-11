package Extractors;

import org.openqa.selenium.By;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class Rongbay extends PageExtractor {
    public Rongbay(){
        super();
        this.name = "Rongbay";
        listPageSelector = "//tr[contains(@class,'show_hide_ad')]/td[1]/a";
        postSelector = "content_detail";
    }
}
