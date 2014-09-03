package tr.com.aliok.meetingroomkiosk.android.restclient.model;

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

    public static class AttendeeBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private AttendeeStatus attendeeStatus;

        public AttendeeBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AttendeeBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AttendeeBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AttendeeBuilder setAttendeeStatus(AttendeeStatus attendeeStatus) {
            this.attendeeStatus = attendeeStatus;
            return this;
        }

        public Attendee createAttendee() {
            Attendee attendee = new Attendee();
            attendee.firstName = this.firstName;
            attendee.lastName = this.lastName;
            attendee.email = this.email;
            attendee.attendeeStatus = this.attendeeStatus;
            return attendee;
        }
    }
}
