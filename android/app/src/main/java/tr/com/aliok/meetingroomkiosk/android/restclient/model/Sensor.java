package tr.com.aliok.meetingroomkiosk.android.restclient.model;

import tr.com.aliok.meetingroomkiosk.model.api.SensorModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Sensor implements SensorModel {

    private String sensorKey;
    private Room room;

    @Override
    public String getSensorKey() {
        return sensorKey;
    }

    @Override
    public Room getRoom() {
        return room;
    }

}
