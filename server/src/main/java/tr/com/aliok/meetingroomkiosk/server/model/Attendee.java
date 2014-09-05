package tr.com.aliok.meetingroomkiosk.server.model;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Attendee {

    private String firstName;
    private String lastName;
    private String email;
    private AttendeeStatus attendeeStatus;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public AttendeeStatus getAttendeeStatus() {
        return attendeeStatus;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private AttendeeStatus attendeeStatus;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAttendeeStatus(AttendeeStatus attendeeStatus) {
            this.attendeeStatus = attendeeStatus;
            return this;
        }

        public Attendee create() {
            Attendee attendee = new Attendee();
            attendee.firstName = this.firstName;
            attendee.lastName = this.lastName;
            attendee.email = this.email;
            attendee.attendeeStatus = this.attendeeStatus;
            return attendee;
        }
    }
}
