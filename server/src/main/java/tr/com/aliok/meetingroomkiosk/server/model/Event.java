package tr.com.aliok.meetingroomkiosk.server.model;

import tr.com.aliok.meetingroomkiosk.model.api.EventModel;

import java.util.Date;
import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Event implements EventModel {

    private String eventKey;
    private String eventTitle;
    private String eventDescription;
    private Date eventStart;
    private Date eventEnd;
    private boolean allDayEvent;
    private Attendee organizer;
    private List<Attendee> attendees;

    @Override
    public String getEventKey() {
        return eventKey;
    }

    @Override
    public String getEventTitle() {
        return eventTitle;
    }

    @Override
    public String getEventDescription() {
        return eventDescription;
    }

    @Override
    public Date getEventStart() {
        return eventStart;
    }

    @Override
    public Date getEventEnd() {
        return eventEnd;
    }

    @Override
    public boolean isAllDayEvent() {
        return allDayEvent;
    }

    @Override
    public Attendee getOrganizer() {
        return organizer;
    }

    @Override
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