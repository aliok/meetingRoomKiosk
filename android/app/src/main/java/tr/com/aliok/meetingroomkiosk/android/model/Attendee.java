package tr.com.aliok.meetingroomkiosk.android.model;

import tr.com.aliok.meetingroomkiosk.model.api.AttendeeModel;
import tr.com.aliok.meetingroomkiosk.model.api.AttendeeStatus;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Attendee implements AttendeeModel {

    private String firstName;
    private String lastName;
    private String email;
    private AttendeeStatus attendeeStatus;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public AttendeeStatus getAttendeeStatus() {
        return attendeeStatus;
    }

}
