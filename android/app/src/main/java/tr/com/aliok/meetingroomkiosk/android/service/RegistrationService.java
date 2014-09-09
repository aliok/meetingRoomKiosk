package tr.com.aliok.meetingroomkiosk.android.service;

import tr.com.aliok.meetingroomkiosk.android.model.Display;

/**
 * Provides services to register the display and also to get stored information about the display.
 */
public interface RegistrationService {

    /**
     * Registers display on the server side and fetches the token. It is important to keep the token safe.
     * <p/>
     * Token is to be used for future requests.
     *
     * @param displayKey        key of current display. to be used as a username.
     * @param password          password for the display account on the server
     * @param gcmRegistrationId GCM registration id of the display
     * @param roomKey           room key defined by the user. To be used for associating the sensor with the display
     * @return token token generated by the server
     */
    String registerDisplay(String displayKey, String password, String gcmRegistrationId, String roomKey);

    /**
     * Checks if the token is valid.
     *
     * @param token Token to check validity
     * @return true if valid
     */
    boolean isDisplayTokenValid(String token);

    /**
     * Fetches the server-side stored display information for the display.
     *
     * @param token Token of the display
     * @return display information
     */
    Display getDisplayInformation(String token);

}
