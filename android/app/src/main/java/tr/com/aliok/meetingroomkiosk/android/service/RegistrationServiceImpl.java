package tr.com.aliok.meetingroomkiosk.android.service;

import tr.com.aliok.meetingroomkiosk.android.client.RegistrationServiceClient;
import tr.com.aliok.meetingroomkiosk.android.model.Display;

/**
 * Provides services to register the display and also to get stored information about the display.
 */
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationServiceClient registrationServiceClient;

    public RegistrationServiceImpl(RegistrationServiceClient registrationServiceClient) {
        this.registrationServiceClient = registrationServiceClient;
    }

    @Override
    public String registerDisplay(String displayKey, String password, String gcmRegistrationId, String roomKey) {
        return registrationServiceClient.registerDisplay(displayKey, password, gcmRegistrationId, roomKey);
    }

    @Override
    public boolean isDisplayTokenValid(String token) {
        return registrationServiceClient.isDisplayTokenValid(token);
    }

    @Override
    public Display getDisplayInformation(String token) {
        return registrationServiceClient.getDisplayInformation(token);
    }
}
