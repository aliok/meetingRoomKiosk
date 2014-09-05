package tr.com.aliok.meetingroomkiosk.server.manager;

import tr.com.aliok.meetingroomkiosk.server.model.Room;
import tr.com.aliok.meetingroomkiosk.server.model.Sensor;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface SensorManager {
    Sensor findBySensorKeyAndPassword(String sensorKey, String password);

    /**
     * return a hash of "deviceId + password + salt" so that if the token is compromised, password change is enough
     *
     * @param sensor
     * @param room
     * @return
     */
    String registerSensor(Sensor sensor, Room room);

    Sensor findByToken(String token);
}
