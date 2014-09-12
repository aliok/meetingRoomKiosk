package tr.com.aliok.meetingroomkiosk.android.model;

import java.util.Date;
import java.util.List;

import tr.com.aliok.meetingroomkiosk.model.api.EventModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Event implements EventModel, Comparable<Event> {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!eventKey.equals(event.eventKey)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return eventKey.hashCode();
    }

    @Override
    public int compareTo(Event other) {
        if (other == null)
            return -1;

        int i = this.getEventStart().compareTo(other.getEventStart());
        if (i == 0)
            i = this.getEventEnd().compareTo(other.getEventEnd());
        if (i == 0)
            i = this.getEventKey().compareTo(other.getEventKey());

        return i;
    }
}