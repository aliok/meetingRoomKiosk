package tr.com.aliok.meetingroomkiosk.model.api;

import java.util.Date;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface PeriodModel {

    public Date getStartDateTime();

    public Date getEndDateTime();

    public PeriodType getPeriodType();

}
