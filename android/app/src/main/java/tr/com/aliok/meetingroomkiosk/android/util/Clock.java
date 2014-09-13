package tr.com.aliok.meetingroomkiosk.android.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import tr.com.aliok.meetingroomkiosk.android.Constants;

/**
 * Allows freezing the time for the development mode.
 */
public class Clock {

    // see http://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
    // for the 'Z' at the end
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final long DATETIME_1;       // before event_today_08oclock
    private static final long DATETIME_2;       // in interval of event_today_08oclock
    private static final long DATETIME_3;       // between event_today_08oclock and event_today_15oclock
    private static final long DATETIME_4;       // after event_today_15oclock --> no upcoming for today

    static {
        try {
            DATETIME_1 = DATE_FORMAT.parse("2014-09-11T07:45:00Z").getTime();
            DATETIME_2 = DATE_FORMAT.parse("2014-09-11T08:45:00Z").getTime();
            DATETIME_3 = DATE_FORMAT.parse("2014-09-11T13:45:00Z").getTime();
            DATETIME_4 = DATE_FORMAT.parse("2014-09-11T17:12:00Z").getTime();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final long FROZEN_TIME = DATETIME_2;

    public static long nowMs() {
        if (Constants.MOCK_DATA)
            return FROZEN_TIME;
        else
            return System.currentTimeMillis();
    }

    public static Date now() {
        return new Date(nowMs());
    }

}
