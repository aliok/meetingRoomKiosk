package tr.com.aliok.meetingroomkiosk.android.service;

import tr.com.aliok.meetingroomkiosk.android.client.ScheduleServiceClient;
import tr.com.aliok.meetingroomkiosk.android.model.ScheduleInformation;

public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleServiceClient scheduleServiceClient;

    public ScheduleServiceImpl(ScheduleServiceClient scheduleServiceClient) {
        this.scheduleServiceClient = scheduleServiceClient;
    }

    @Override
    public ScheduleInformation getSchedule(String token) {
        return scheduleServiceClient.getSchedule(token);
    }
}
