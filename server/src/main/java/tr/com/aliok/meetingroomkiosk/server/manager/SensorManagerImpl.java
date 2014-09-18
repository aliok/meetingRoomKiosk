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
        if (sampleData.sensor1.getSensorKey().equals(sensorKey) && "admin".equals(password))
            return sampleData.sensor1;
        return null;
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
