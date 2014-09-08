package tr.com.aliok.meetingroomkiosk.server.model;

import tr.com.aliok.meetingroomkiosk.model.api.ScheduleInformationModel;

import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class ScheduleInformation implements ScheduleInformationModel {
    private List<PeriodSchedule> periodSchedules;

    @Override
    public List<PeriodSchedule> getPeriodSchedules() {
        return periodSchedules;
    }

    public static class Builder {
        private List<PeriodSchedule> periodSchedules;

        public Builder setPeriodSchedules(List<PeriodSchedule> periodSchedules) {
            this.periodSchedules = periodSchedules;
            return this;
        }

        public ScheduleInformation create() {
            final ScheduleInformation scheduleInformation = new ScheduleInformation();
            scheduleInformation.periodSchedules = this.periodSchedules;
            return scheduleInformation;
        }
    }
}
