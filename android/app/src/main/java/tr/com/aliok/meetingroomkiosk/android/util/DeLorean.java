package tr.com.aliok.meetingroomkiosk.android.util;

import com.google.common.collect.ImmutableMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import tr.com.aliok.meetingroomkiosk.android.Constants;

/**
 * Allows freezing the time for the development mode.
 */
public class DeLorean {

    // see http://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
    // for the 'Z' at the end
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final ImmutableMap<Integer, Long> DATETIME_MAP;

    static {
        try {

            DATETIME_MAP = new ImmutableMap.Builder<Integer, Long>()
                    // before event_today_08oclock
                    .put(0, DATE_FORMAT.parse("2014-09-11T07:45:00Z").getTime())

                            // just before event_today_08oclock
                    .put(1, DATE_FORMAT.parse("2014-09-11T07:59:45Z").getTime())

                            // in interval of event_today_08oclock
                    .put(2, DATE_FORMAT.parse("2014-09-11T08:45:00Z").getTime())

                            // between event_today_08oclock and event_today_15oclock
                    .put(3, DATE_FORMAT.parse("2014-09-11T13:45:00Z").getTime())

                            // after event_today_15oclock --> no upcoming for today
                    .put(4, DATE_FORMAT.parse("2014-09-11T17:12:00Z").getTime())

                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int frozenTimeIndex = 1;
    private static long frozenTime = DATETIME_MAP.get(frozenTimeIndex);

    public static long nowMs() {
        if (Constants.MOCK_DATA)
            return frozenTime;
        else
            return System.currentTimeMillis();
    }

    public static Date now() {
        return new Date(nowMs());
    }

    public static void advanceTime() {
        frozenTimeIndex = (frozenTimeIndex + 1) % DATETIME_MAP.size();
        frozenTime = DATETIME_MAP.get(frozenTimeIndex);
    }

}
