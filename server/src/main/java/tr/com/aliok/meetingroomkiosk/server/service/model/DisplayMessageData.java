package tr.com.aliok.meetingroomkiosk.server.service.model;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class DisplayMessageData extends Data {
    private String event;

    public DisplayMessageData() {
    }

    public DisplayMessageData(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
