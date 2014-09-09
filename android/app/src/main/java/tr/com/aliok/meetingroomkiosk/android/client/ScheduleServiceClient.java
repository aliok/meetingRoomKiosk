package tr.com.aliok.meetingroomkiosk.android.client;

import tr.com.aliok.meetingroomkiosk.android.model.ScheduleInformation;

/**
 * Provides services to get schedule information for the display.
 */
public interface ScheduleServiceClient {

    /**
     * Fetches the schedule for current display which is assigned to the provided token.
     *
     * @param token display token
     * @return schedule information
     */
    ScheduleInformation getSchedule(String token);

}
