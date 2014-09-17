package tr.com.aliok.meetingroomkiosk.server.rest;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.aliok.meetingroomkiosk.server.manager.DisplayManager;
import tr.com.aliok.meetingroomkiosk.server.manager.SensorManager;
import tr.com.aliok.meetingroomkiosk.server.model.Display;
import tr.com.aliok.meetingroomkiosk.server.model.Sensor;
import tr.com.aliok.meetingroomkiosk.server.service.GoogleCloudMessagingServices;

/**
 * Provides services for sensors to connect and interact with the server.
 *
 * @author Ali Ok (ali.ok@apache.org)
 */
@RestController
public class SensorService {

    @Autowired
    SensorManager sensorManager;

    @Autowired
    DisplayManager displayManager;

    @Autowired
    GoogleCloudMessagingServices googleCloudMessagingServices;

    @RequestMapping("/notifySensorEvent")
    public void notifySensorEvent(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "eventType", required = true) String eventType) {

        final Sensor sensor = sensorManager.findByToken(token);
        Validate.notNull(sensor);

        final String roomKey = sensor.getRoom().getKey();
        final Display display = displayManager.findByRoomKey(roomKey);
        Validate.notNull(display);

        googleCloudMessagingServices.notifyDisplayInBackground(display, eventType);
    }
}
