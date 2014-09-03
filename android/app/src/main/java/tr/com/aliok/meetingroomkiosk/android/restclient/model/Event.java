package tr.com.aliok.meetingroomkiosk.android.restclient.model;

import java.util.Date;
import java.util.List;

public class Event {

    private Long eventId;
    private String eventDescription;
    private Date eventStart;
    private Date eventEnd;
    private boolean allDayEvent;
    private Attendee organizer;
    private List<Attendee> attendees;

    public Long getEventId() {
        return eventId;
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

    public static class EventBuilder {
        private Long eventId;
        private String eventDescription;
        private Date eventStart;
        private Date eventEnd;
        private boolean allDayEvent;
        private Attendee organizer;
        private List<Attendee> attendees;

        public EventBuilder setEventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public EventBuilder setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
            return this;
        }

        public EventBuilder setEventStart(Date eventStart) {
            this.eventStart = eventStart;
            return this;
        }

        public EventBuilder setEventEnd(Date eventEnd) {
            this.eventEnd = eventEnd;
            return this;
        }

        public EventBuilder setAllDayEvent(boolean allDayEvent) {
            this.allDayEvent = allDayEvent;
            return this;
        }

        public EventBuilder setOrganizer(Attendee organizer) {
            this.organizer = organizer;
            return this;
        }

        public EventBuilder setAttendees(List<Attendee> attendees) {
            this.attendees = attendees;
            return this;
        }

        public Event createEvent() {
            final Event event = new Event();

            event.eventId = this.eventId;
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
