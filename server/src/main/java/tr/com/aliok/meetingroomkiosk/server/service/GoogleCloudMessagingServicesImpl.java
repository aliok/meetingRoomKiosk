package tr.com.aliok.meetingroomkiosk.server.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.com.aliok.meetingroomkiosk.server.Constants;
import tr.com.aliok.meetingroomkiosk.server.model.Display;
import tr.com.aliok.meetingroomkiosk.server.service.model.DisplayMessageData;
import tr.com.aliok.meetingroomkiosk.server.service.model.GoogleCloudMessage;
import tr.com.aliok.meetingroomkiosk.server.service.model.GoogleCloudMessageResponse;
import tr.com.aliok.meetingroomkiosk.server.service.model.GoogleCloudMessageResponseResult;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
@Component
public class GoogleCloudMessagingServicesImpl implements GoogleCloudMessagingServices {

    @Autowired
    GoogleCloudMessagingRestClient googleCloudMessagingRestClient;

    static final Logger logger = LogManager.getLogger(GoogleCloudMessagingServicesImpl.class.getName());

    @Override
    public void notifyDisplayInBackground(Display display, String eventType) {
        final GoogleCloudMessage message = new GoogleCloudMessage();

        message.setCollapse_key(Constants.GCM_MESSAGE_COLLAPSE_KEY);
        message.setTime_to_live(Constants.GCM_MESSAGE_TIME_TO_LIVE);
        message.setRegistration_ids(new String[]{display.getGcmRegistrationId()});
        message.setData(new DisplayMessageData(eventType));

        final GoogleCloudMessageResponse response = googleCloudMessagingRestClient.sendMessage(message);

        for (GoogleCloudMessageResponseResult result : response.getResults()) {
            final String registration_id = result.getRegistration_id();
            final String message_id = result.getMessage_id();
            final String error = result.getError();
            if (StringUtils.isNotBlank(error)) {
                logger.error("Error pushing message. DeviceRegistrationId: " + registration_id + ", messageId: " + message_id + ", error:" + error);
            }
        }

    }
}
