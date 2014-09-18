package tr.com.aliok.meetingroomkiosk.server.model;

import tr.com.aliok.meetingroomkiosk.model.api.SensorModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Sensor implements SensorModel {

    private String sensorKey;
    private Room room;
    private int threshold1;
    private int threshold2;
    private int secondsToIgnoreSameEvents;

    @Override
    public String getSensorKey() {
        return sensorKey;
    }

    @Override
    public Room getRoom() {
        return room;
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

    public static class Builder {
        private String sensorKey;
        private Room room;
        private int threshold1;
        private int threshold2;
        private int secondsToIgnoreSameEvents;

        public Builder setSensorKey(String sensorKey) {
            this.sensorKey = sensorKey;
            return this;
        }

        public Builder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder setThreshold1(int threshold1) {
            this.threshold1 = threshold1;
            return this;
        }

        public Builder setThreshold2(int threshold2) {
            this.threshold2 = threshold2;
            return this;
        }

        public Builder setSecondsToIgnoreSameEvents(int secondsToIgnoreSameEvents) {
            this.secondsToIgnoreSameEvents = secondsToIgnoreSameEvents;
            return this;
        }

        public Sensor create() {
            final Sensor sensor = new Sensor();
            sensor.sensorKey = this.sensorKey;
            sensor.room = this.room;
            sensor.threshold1 = this.threshold1;
            sensor.threshold2 = this.threshold2;
            sensor.secondsToIgnoreSameEvents = this.secondsToIgnoreSameEvents;
            return sensor;
        }
    }
}
