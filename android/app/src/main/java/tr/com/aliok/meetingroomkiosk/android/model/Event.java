package tr.com.aliok.meetingroomkiosk.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.com.aliok.meetingroomkiosk.model.api.EventModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Event implements EventModel, Comparable<Event>, Parcelable {

    private String eventKey;
    private String eventTitle;
    private String eventDescription;
    private Date eventStart;
    private Date eventEnd;
    private boolean allDayEvent;
    private Attendee organizer;
    private List<Attendee> attendees;

    //region getters
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
    //endregion

    //region equals-hashCode-compareTo
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
    //endregion

    //region parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.eventKey);
        parcel.writeString(this.eventTitle);
        parcel.writeString(this.eventDescription);
        parcel.writeLong(this.eventStart.getTime());
        parcel.writeLong(this.eventEnd.getTime());
        // see http://stackoverflow.com/questions/6201311/how-to-read-write-a-boolean-when-implementing-the-parcelable-interface
        parcel.writeByte((byte) (this.allDayEvent ? 1 : 0));
        parcel.writeParcelable(organizer, 0);
        parcel.writeTypedList(attendees);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel pc) {
            return new Event(pc);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public Event(Parcel parcel) {
        eventKey = parcel.readString();
        eventTitle = parcel.readString();
        eventDescription = parcel.readString();
        eventStart = new Date(parcel.readLong());
        eventEnd = new Date(parcel.readLong());
        // see http://stackoverflow.com/questions/6201311/how-to-read-write-a-boolean-when-implementing-the-parcelable-interface
        allDayEvent = parcel.readByte() != 0;
        organizer = parcel.readParcelable(Attendee.class.getClassLoader());
        // see http://stackoverflow.com/questions/6774645/android-how-to-use-readtypedlist-method-correctly-in-a-parcelable-class
        attendees = new ArrayList<Attendee>();
        parcel.readTypedList(attendees, Attendee.CREATOR);
    }
    //endregion
}