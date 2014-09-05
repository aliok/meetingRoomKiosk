package tr.com.aliok.meetingroomkiosk.server.model;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Sensor {

    private String sensorKey;
    private Room room;

    public String getSensorKey() {
        return sensorKey;
    }

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
