package tr.com.aliok.meetingroomkiosk.model.api;

import java.util.Date;
import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface EventModel {

    public String getEventKey();

    public String getEventTitle();

    public String getEventDescription();

    public Date getEventStart();

    public Date getEventEnd();

    public boolean isAllDayEvent();

    public AttendeeModel getOrganizer();

    public List<? extends AttendeeModel> getAttendees();

}
