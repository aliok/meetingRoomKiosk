package tr.com.aliok.meetingroomkiosk.server.manager;

import tr.com.aliok.meetingroomkiosk.server.model.Display;
import tr.com.aliok.meetingroomkiosk.server.model.Room;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface DisplayManager {
    Display findByDisplayKeyAndPassword(String displayKey, String password);

    /**
     * return a hash of "deviceId + password + salt" so that if the token is compromised, password change is enough
     */
    String registerDisplay(Display display, String gcmRegistrationId, Room room);

    Display findByToken(String token);
}
