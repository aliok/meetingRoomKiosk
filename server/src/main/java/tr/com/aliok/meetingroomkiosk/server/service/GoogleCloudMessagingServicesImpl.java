package tr.com.aliok.meetingroomkiosk.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tr.com.aliok.meetingroomkiosk.server.model.Display;
import tr.com.aliok.meetingroomkiosk.server.service.model.DisplayMessageData;
import tr.com.aliok.meetingroomkiosk.server.service.model.GoogleCloudMessage;
import tr.com.aliok.meetingroomkiosk.server.service.model.GoogleCloudMessageResponse;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
@Component
public class GoogleCloudMessagingServicesImpl implements GoogleCloudMessagingServices {

    @Autowired
    GoogleCloudMessagingRestClient googleCloudMessagingRestClient;

    @Override
    public void notifyDisplayInBackground(Display display, String eventType) {
        final GoogleCloudMessage message = new GoogleCloudMessage();

        message.setCollapse_key("tickle");
        message.setTime_to_live(30);
        message.setRegistration_ids(new String[]{"asd"});
        message.setData(new DisplayMessageData("fgh"));

        final GoogleCloudMessageResponse response = googleCloudMessagingRestClient.sendMessage(message);
        System.out.println(response);
    }
}
