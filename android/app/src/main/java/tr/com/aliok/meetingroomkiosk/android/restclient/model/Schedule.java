package tr.com.aliok.meetingroomkiosk.android.restclient.model;

import java.util.Date;
import java.util.List;

import tr.com.aliok.meetingroomkiosk.model.api.ScheduleModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Schedule implements ScheduleModel {

    private Room room;
    private Date scheduleServerSyncTime;
    private List<Event> events;

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public Date getScheduleServerSyncTime() {
        return scheduleServerSyncTime;
    }

    @Override
    public List<Event> getEvents() {
        return events;
    }

}