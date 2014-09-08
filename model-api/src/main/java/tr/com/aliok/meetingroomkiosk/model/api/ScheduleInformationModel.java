package tr.com.aliok.meetingroomkiosk.model.api;

import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface ScheduleInformationModel {

    public List<? extends PeriodScheduleModel> getPeriodSchedules();

}
