package tr.com.aliok.meetingroomkiosk.server.model;

import java.util.Date;
import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Event {

    private String eventKey;
    private String eventTitle;
    private String eventDescription;
    private Date eventStart;
    private Date eventEnd;
    private boolean allDayEvent;
    private Attendee organizer;
    private List<Attendee> attendees;

    public String getEventKey() {
        return eventKey;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public Date getEventStart() {
        return eventStart;
    }

    public Date getEventEnd() {
        return eventEnd;
    }

    public boolean isAllDayEvent() {
        return allDayEvent;
    }

    public Attendee getOrganizer() {
        return organizer;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public static class Builder {
        private String eventKey;
        private String eventTitle;
        private String eventDescription;
        private Date eventStart;
        private Date eventEnd;
        private boolean allDayEvent;
        private Attendee organizer;
        private List<Attendee> attendees;

        public Builder setEventKey(String eventKey) {
            this.eventKey = eventKey;
            return this;
        }

        public Builder setEventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
            return this;
        }

        public Builder setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
            return this;
        }

        public Builder setEventStart(Date eventStart) {
            this.eventStart = eventStart;
            return this;
        }

        public Builder setEventEnd(Date eventEnd) {
            this.eventEnd = eventEnd;
            return this;
        }

        public Builder setAllDayEvent(boolean allDayEvent) {
            this.allDayEvent = allDayEvent;
            return this;
        }

        public Builder setOrganizer(Attendee organizer) {
            this.organizer = organizer;
            return this;
        }

        public Builder setAttendees(List<Attendee> attendees) {
            this.attendees = attendees;
            return this;
        }

        public Event create() {
            final Event event = new Event();

            event.eventKey = this.eventKey;
            event.eventTitle = eventTitle;
            event.eventDescription = this.eventDescription;
            event.eventStart = this.eventStart;
            event.eventEnd = this.eventEnd;
            event.allDayEvent = this.allDayEvent;
            event.organizer = this.organizer;
            event.attendees = this.attendees;

            return event;
        }
    }
}