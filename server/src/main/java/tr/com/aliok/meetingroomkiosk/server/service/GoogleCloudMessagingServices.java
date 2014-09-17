package tr.com.aliok.meetingroomkiosk.server.service;

import tr.com.aliok.meetingroomkiosk.server.model.Display;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface GoogleCloudMessagingServices {

    void notifyDisplayInBackground(Display display, String eventType);
}
