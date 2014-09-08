package tr.com.aliok.meetingroomkiosk.server.model;

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

    public static class Builder {
        private String sensorKey;
        private Room room;

        public Builder setSensorKey(String sensorKey) {
            this.sensorKey = sensorKey;
            return this;
        }

        public Builder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public Sensor create() {
            final Sensor sensor = new Sensor();
            sensor.sensorKey = this.sensorKey;
            sensor.room = this.room;
            return sensor;
        }
    }
}
