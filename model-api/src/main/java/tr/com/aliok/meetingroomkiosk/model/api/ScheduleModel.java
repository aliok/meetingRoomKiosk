package tr.com.aliok.meetingroomkiosk.model.api;

import java.util.Date;
import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface ScheduleModel {

    public RoomModel getRoom();

    public Date getScheduleServerSyncTime();

    public List<? extends EventModel> getEvents();

}
