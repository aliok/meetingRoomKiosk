package tr.com.aliok.meetingroomkiosk.server.manager;

import tr.com.aliok.meetingroomkiosk.server.model.Room;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface RoomManager {
    Room findByKey(String roomKey);
}
