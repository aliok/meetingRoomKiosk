package tr.com.aliok.meetingroomkiosk.android.model;

import java.util.Date;
import java.util.List;

import tr.com.aliok.meetingroomkiosk.model.api.EventModel;

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

}