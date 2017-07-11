package Data;

/**
 * Created by thaison1496 on 12/7/16.
 */
public class TargetURL {
    public enum URLTYPE {POST, LISTPAGE};
    private String url;
    private URLTYPE type;

    public TargetURL(String url, URLTYPE type){
        this.url = url;
        this.type = type;
    }

    public String getUrl(){return url;}
    public URLTYPE getType(){return type;}
}
