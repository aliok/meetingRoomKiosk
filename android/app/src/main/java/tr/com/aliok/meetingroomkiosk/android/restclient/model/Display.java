package tr.com.aliok.meetingroomkiosk.android.restclient.model;

import tr.com.aliok.meetingroomkiosk.model.api.DisplayModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Display implements DisplayModel {

    private String displayKey;
    private Room room;
    private Sensor sensor;

    @Override
    public String getDisplayKey() {
        return displayKey;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public Sensor getSensor() {
        return sensor;
    }

}
