package tr.com.aliok.meetingroomkiosk.model.api;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface AttendeeModel {
    public String getFirstName();

    public String getLastName();

    public String getEmail();

    public AttendeeStatus getAttendeeStatus();
}
