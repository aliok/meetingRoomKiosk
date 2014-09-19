package tr.com.aliok.meetingroomkiosk.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import tr.com.aliok.meetingroomkiosk.model.api.AttendeeModel;
import tr.com.aliok.meetingroomkiosk.model.api.AttendeeStatus;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Attendee implements AttendeeModel, Parcelable {

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

    //region parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
        parcel.writeString(attendeeStatus.name());
    }

    public static final Parcelable.Creator<Attendee> CREATOR = new Parcelable.Creator<Attendee>() {
        public Attendee createFromParcel(Parcel parcel) {
            return new Attendee(parcel);
        }

        public Attendee[] newArray(int size) {
            return new Attendee[size];
        }
    };

    public Attendee(Parcel parcel) {
        firstName = parcel.readString();
        lastName = parcel.readString();
        email = parcel.readString();
        attendeeStatus = AttendeeStatus.valueOf(parcel.readString());
    }
    //endregion
}
