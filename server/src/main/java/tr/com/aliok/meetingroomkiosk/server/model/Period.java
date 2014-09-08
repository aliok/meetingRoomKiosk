package tr.com.aliok.meetingroomkiosk.server.model;

import tr.com.aliok.meetingroomkiosk.model.api.PeriodModel;
import tr.com.aliok.meetingroomkiosk.model.api.PeriodType;

import java.util.Date;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Period implements PeriodModel {

    private Date startDateTime;
    private Date endDateTime;
    private PeriodType periodType;

    @Override
    public Date getStartDateTime() {
        return startDateTime;
    }

    @Override
    public Date getEndDateTime() {
        return endDateTime;
    }

    @Override
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
