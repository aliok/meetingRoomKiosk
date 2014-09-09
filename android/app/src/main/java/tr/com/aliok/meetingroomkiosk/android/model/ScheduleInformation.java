package tr.com.aliok.meetingroomkiosk.android.model;

import java.util.List;

import tr.com.aliok.meetingroomkiosk.model.api.ScheduleInformationModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class ScheduleInformation implements ScheduleInformationModel {
    private List<PeriodSchedule> periodSchedules;

    @Override
    public List<PeriodSchedule> getPeriodSchedules() {
        return periodSchedules;
    }

}
