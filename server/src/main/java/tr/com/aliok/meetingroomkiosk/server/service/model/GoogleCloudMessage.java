package tr.com.aliok.meetingroomkiosk.server.service.model;

/**
 * A Java POJO conforming http://developer.android.com/google/gcm/http.html
 *
 * @author Ali Ok (ali.ok@apache.org)
 */
public class GoogleCloudMessage {
    private String[] registration_ids = new String[0];
    private Data data;
    private int time_to_live;
    private String collapse_key;

    public String[] getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(String[] registration_ids) {
        this.registration_ids = registration_ids;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getTime_to_live() {
        return time_to_live;
    }

    public void setTime_to_live(int time_to_live) {
        this.time_to_live = time_to_live;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }
}
