package tr.com.aliok.meetingroomkiosk.android.restclient.model;

import tr.com.aliok.meetingroomkiosk.model.api.PeriodScheduleModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class PeriodSchedule implements PeriodScheduleModel {
    private Period period;
    private Schedule schedule;

    @Override
    public Period getPeriod() {
        return period;
    }

    @Override
    public Schedule getSchedule() {
        return schedule;
    }

}
