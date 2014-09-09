package tr.com.aliok.meetingroomkiosk.android.service;

import tr.com.aliok.meetingroomkiosk.android.model.ScheduleInformation;

public interface ScheduleService {

    /**
     * Fetches the schedule for current display which is assigned to the provided token.
     *
     * @param token display token
     * @return schedule information
     */
    ScheduleInformation getSchedule(String token);

}
