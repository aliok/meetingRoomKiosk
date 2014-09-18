package tr.com.aliok.meetingroomkiosk.server.model.dto;

import tr.com.aliok.meetingroomkiosk.server.model.Sensor;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class SensorSettings {
    private String token;
    private int threshold1;
    private int threshold2;
    private int secondsToIgnoreSameEvents;

    public SensorSettings(String token, Sensor sensor) {
        this.token = token;
        this.threshold1 = sensor.getThreshold1();
        this.threshold2 = sensor.getThreshold2();
        this.secondsToIgnoreSameEvents = sensor.getSecondsToIgnoreSameEvents();
    }

    public String getToken() {
        return token;
    }

    public int getThreshold1() {
        return threshold1;
    }

    public int getThreshold2() {
        return threshold2;
    }

    public int getSecondsToIgnoreSameEvents() {
        return secondsToIgnoreSameEvents;
    }
}
