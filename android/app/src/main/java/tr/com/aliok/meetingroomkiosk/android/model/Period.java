package tr.com.aliok.meetingroomkiosk.android.model;

import java.util.Date;

import tr.com.aliok.meetingroomkiosk.model.api.PeriodModel;
import tr.com.aliok.meetingroomkiosk.model.api.PeriodType;

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

}
