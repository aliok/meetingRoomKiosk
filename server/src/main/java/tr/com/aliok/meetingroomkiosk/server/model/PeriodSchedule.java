package tr.com.aliok.meetingroomkiosk.server.model;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class PeriodSchedule {
    private Period period;
    private Schedule schedule;

    public Period getPeriod() {
        return period;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public static class Builder {
        private Period period;
        private Schedule schedule;

        public Builder setPeriod(Period period) {
            this.period = period;
            return this;
        }

        public Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public PeriodSchedule create() {
            final PeriodSchedule periodSchedule = new PeriodSchedule();

            periodSchedule.period = this.period;
            periodSchedule.schedule = this.schedule;

            return periodSchedule;
        }
    }
}
