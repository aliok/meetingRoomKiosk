package tr.com.aliok.meetingroomkiosk.server.model;

import java.util.Date;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Period {

    private Date startDateTime;
    private Date endDateTime;
    private PeriodType periodType;

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public static class Builder {
        private Date startDateTime;
        private Date endDateTime;
        private PeriodType periodType;

        public Builder setStartDateTime(Date startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public Builder setEndDateTime(Date endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public Builder setPeriodType(PeriodType periodType) {
            this.periodType = periodType;
            return this;
        }

        public Period create() {
            final Period period = new Period();

            period.startDateTime = this.startDateTime;
            period.endDateTime = this.endDateTime;
            period.periodType = this.periodType;

            return period;
        }
    }


}
