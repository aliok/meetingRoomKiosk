package tr.com.aliok.meetingroomkiosk.server.model;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Display {

    private String displayKey;
    private Room room;
    private Sensor sensor;

    public String getDisplayKey() {
        return displayKey;
    }

    public Room getRoom() {
        return room;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public static class Builder {
        private String displayKey;
        private Room room;
        private Sensor sensor;

        public Builder setDisplayKey(String displayKey) {
            this.displayKey = displayKey;
            return this;
        }

        public Builder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder setSensor(Sensor sensor) {
            this.sensor = sensor;
            return this;
        }

        public Display create() {
            final Display display = new Display();
            display.displayKey = this.displayKey;
            display.room = this.room;
            display.sensor = this.sensor;
            return display;
        }
    }

}
