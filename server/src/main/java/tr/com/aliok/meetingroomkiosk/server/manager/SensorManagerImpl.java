package tr.com.aliok.meetingroomkiosk.server.manager;

import tr.com.aliok.meetingroomkiosk.server.model.Room;
import tr.com.aliok.meetingroomkiosk.server.model.Sensor;
import tr.com.aliok.meetingroomkiosk.server.rest.SampleData;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class SensorManagerImpl implements SensorManager {
    private final SampleData sampleData = new SampleData();

    @Override
    public Sensor findBySensorKeyAndPassword(String sensorKey, String password) {
        return sampleData.sensor1;
    }

    @Override
    public String registerSensor(Sensor sensor, Room room) {
        return "KLMNOP";
    }

    @Override
    public Sensor findByToken(String token) {
        if ("KLMNOP".equals(token))
            return sampleData.sensor1;
        else
            return null;
    }
}
