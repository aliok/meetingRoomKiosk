package tr.com.aliok.meetingroomkiosk.android.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    final static DateFormat HOUR_DATE_FORMAT = new SimpleDateFormat("HH:mm");

    /**
     * Converts "2013-09-08 15:30" to 15.5
     *
     * @return hour in float
     */
    public static float getHourInFloat(Date date) {
        final long hours = DateUtils.getFragmentInHours(date, Calendar.DAY_OF_MONTH);
        final long minutes = DateUtils.getFragmentInMinutes(date, Calendar.HOUR_OF_DAY);

        return hours + (minutes / 60.f);
    }

    public static String getTimeRangeStr(Date startDate, Date endDate) {
        return getTimeStr(startDate) + " - " + getTimeStr(endDate);
    }

    public static String getTimeStr(Date date) {
        return HOUR_DATE_FORMAT.format(date);
    }

}
