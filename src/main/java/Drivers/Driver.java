package Drivers;

import java.util.List;

/**
 * Created by thaison1496 on 14/7/16.
 */
public interface Driver {
    void goTo(String url) throws Exception;
    List<String> getLinks(String xpath);
    String getText(String id);
    String getTextByClass(String id);
    void click(String xpath);
    void close();
}
