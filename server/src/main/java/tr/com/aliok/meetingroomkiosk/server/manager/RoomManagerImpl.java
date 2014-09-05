package tr.com.aliok.meetingroomkiosk.server.manager;

import tr.com.aliok.meetingroomkiosk.server.model.Room;
import tr.com.aliok.meetingroomkiosk.server.rest.SampleData;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class RoomManagerImpl implements RoomManager {
    private final SampleData sampleData = new SampleData();

    @Override
    public Room findByKey(String roomKey) {
        if ("ROOM1".equals(roomKey))
            return sampleData.room1;
        else
            return null;
    }
}
