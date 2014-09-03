package tr.com.aliok.meetingroomkiosk.android.restclient.model;

public class SensorInformation {

    private String sensorId;
    private String roomId;

    public String getSensorId() {
        return sensorId;
    }

    public String getRoomId() {
        return roomId;
    }

    public static class SensorInformationBuilder {
        private String sensorId;
        private String roomId;

        public SensorInformationBuilder setSensorId(String sensorId) {
            this.sensorId = sensorId;
            return this;
        }

        public SensorInformationBuilder setRoomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public SensorInformation createSensorInformation() {
            final SensorInformation sensorInformation = new SensorInformation();
            sensorInformation.sensorId = this.sensorId;
            sensorInformation.roomId = this.roomId;
            return sensorInformation;
        }
    }
}
