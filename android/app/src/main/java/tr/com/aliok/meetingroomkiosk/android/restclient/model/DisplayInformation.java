package tr.com.aliok.meetingroomkiosk.android.restclient.model;

public class DisplayInformation {

    private String deviceId;
    private String roomId;
    private SensorInformation sensorInformation;

    public String getDeviceId() {
        return deviceId;
    }

    public String getRoomId() {
        return roomId;
    }

    public SensorInformation getSensorInformation() {
        return sensorInformation;
    }

    public static class DisplayInformationBuilder {
        private String deviceId;
        private String roomId;
        private SensorInformation sensorInformation;

        public DisplayInformationBuilder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public DisplayInformationBuilder setRoomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public DisplayInformationBuilder setSensorInformation(SensorInformation sensorInformation) {
            this.sensorInformation = sensorInformation;
            return this;
        }

        public DisplayInformation createDisplayInformation() {
            final DisplayInformation displayInformation = new DisplayInformation();
            displayInformation.deviceId = this.deviceId;
            displayInformation.roomId = this.roomId;
            displayInformation.sensorInformation = this.sensorInformation;
            return displayInformation;
        }
    }

}
