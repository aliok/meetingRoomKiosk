package tr.com.aliok.meetingroomkiosk.android.restclient;


import tr.com.aliok.meetingroomkiosk.android.restclient.model.DisplayInformation;
import tr.com.aliok.meetingroomkiosk.android.restclient.model.SensorInformation;

public class RegistrationServiceClient {

    public DisplayInformation fetchDisplayInformation() {
        final SensorInformation sensorInformation = new SensorInformation.SensorInformationBuilder()
                .setRoomId("quartz")
                .setSensorId("5678")
                .createSensorInformation();

        return new DisplayInformation.DisplayInformationBuilder()
                .setDeviceId("1234")
                .setRoomId("quartz")
                .setSensorInformation(sensorInformation)
                .createDisplayInformation();
    }

}
