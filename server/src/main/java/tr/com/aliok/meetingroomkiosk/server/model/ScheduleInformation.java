package tr.com.aliok.meetingroomkiosk.server.model;

import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class ScheduleInformation {
    private List<PeriodSchedule> periodSchedules;

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
