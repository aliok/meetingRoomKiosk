package tr.com.aliok.meetingroomkiosk.android.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

public class DateTimeUtils {

    final static DateFormat HOUR_DATE_FORMAT = new SimpleDateFormat("HH:mm");
    final static DateFormat LONG_DATE_FORMAT = new SimpleDateFormat("EEE, MMM dd");

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

    /**
     * Returns "22:00 - 23:00" for params "2014-02-09 22:00" and "2017-12-31 23:00"
     *
     * @param startDate range start
     * @param endDate   range end
     * @return range str
     */
    public static String getTimeRangeStr(Date startDate, Date endDate) {
        return getTimeStr(startDate) + " - " + getTimeStr(endDate);
    }

    /**
     * Returns "22:00" for param "2014-02-09 22:00"
     *
     * @param date the datetime
     * @return time str
     */
    public static String getTimeStr(Date date) {
        return HOUR_DATE_FORMAT.format(date);
    }

    /**
     * Returns "2014-02-09 00:00" for param "2014-02-09 22:00"
     *
     * @param date the datetime
     * @return 00:00 of that day
     */
    public Date getDayStart(Date date) {
        return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
    }


    /**
     * Returns a sorted set of days in a time range. It is inclusive on both start and on end.
     * <p/>
     * Example input : "2012-04-10 22:21:11" - "2012-04-13 01:00:00"
     * Example output : {"2012-04-10 00:00:00", "2012-04-11 00:00:00", "2012-04-12 00:00:00", "2012-04-13 00:00:00"}
     *
     * @param startDatetime range start
     * @param endDatetime   range end
     * @return sorted set
     */
    public static TreeSet<Date> getDaysInDateTimeRange(Date startDatetime, Date endDatetime) {
        final Date startDay = DateUtils.truncate(startDatetime, Calendar.DAY_OF_MONTH);
        final Date endDay = DateUtils.addMilliseconds(DateUtils.ceiling(endDatetime, Calendar.DAY_OF_MONTH), -1);

        final TreeSet<Date> dates = new TreeSet<Date>();

        Date temp = startDay;
        while (!temp.after(endDay)) {
            dates.add(temp);
            temp = DateUtils.addDays(temp, 1);
        }

        return dates;
    }

    /**
     * Converts given date time to long date string.
     * <p/>
     * Example input: "2014-09-10 22:15:12"
     * Example output: "Wed, Sep 10"
     *
     * @param date to be converted
     * @return str
     */
    public static String getLongDateStr(Date date) {
        return LONG_DATE_FORMAT.format(date);
    }

    /**
     * Returns the day of week as zero indexed.
     * That means, a dateTime in monday will return 0;
     *
     * @param dateTime to get zero-indexed day of week
     * @return zero-indexed day of week
     */
    public static int getDayOfWeekZeroIndexed(Date dateTime) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);

        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                throw new IllegalStateException();
        }
    }
}
