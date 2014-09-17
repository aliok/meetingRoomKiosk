package tr.com.aliok.meetingroomkiosk.server.manager;

import tr.com.aliok.meetingroomkiosk.server.model.Display;
import tr.com.aliok.meetingroomkiosk.server.model.Room;
import tr.com.aliok.meetingroomkiosk.server.rest.SampleData;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class DisplayManagerImpl implements DisplayManager {
    private final SampleData sampleData = new SampleData();

    @Override
    public Display findByDisplayKeyAndPassword(String displayKey, String password) {
        return sampleData.display1;
    }

    @Override
    public String registerDisplay(Display display, String gcmRegistrationId, Room room) {
        return "ASDFGH";
    }

    @Override
    public Display findByToken(String token) {
        if ("ASDFGH".equals(token))
            return sampleData.display1;
        else
            return null;
    }

    @Override
    public Display findByRoomKey(String roomKey) {
        if ("ROOM1".equals(roomKey))
            return sampleData.display1;
        else
            return null;
    }
}
