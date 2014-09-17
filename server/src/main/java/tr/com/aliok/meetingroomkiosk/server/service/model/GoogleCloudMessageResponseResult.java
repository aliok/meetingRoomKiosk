package tr.com.aliok.meetingroomkiosk.server.service.model;

/**
 * A Java POJO conforming http://developer.android.com/google/gcm/http.html
 *
 * @author Ali Ok (ali.ok@apache.org)
 */
public class GoogleCloudMessageResponseResult {
    private String message_id;
    private String registration_id;
    private String error;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
