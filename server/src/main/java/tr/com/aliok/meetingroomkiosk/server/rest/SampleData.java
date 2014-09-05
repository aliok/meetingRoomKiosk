package tr.com.aliok.meetingroomkiosk.server.rest;

import org.apache.commons.lang3.time.DateUtils;
import tr.com.aliok.meetingroomkiosk.server.model.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class SampleData {

    public final Date now = new Date();
    public final Date today_00oclock = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
    public final Date today_08oclock = DateUtils.addHours(today_00oclock, 8);
    public final Date today_12oclock = DateUtils.addHours(today_00oclock, 12);
    public final Date today_24oclock = DateUtils.ceiling(now, Calendar.DAY_OF_MONTH);

    public final Attendee attendee1 = new Attendee.Builder()
            .setAttendeeStatus(AttendeeStatus.ACCEPTED)
            .setEmail("ali@example.com")
            .setFirstName("Ali")
            .setLastName("Ok")
            .create();

    public final Attendee attendee2 = new Attendee.Builder()
            .setAttendeeStatus(AttendeeStatus.ACCEPTED)
            .setEmail("rene@example.com")
            .setFirstName("Rene")
            .setLastName("Gunther")
            .create();


    public final Attendee organizer1 = attendee1;

    public final Event event1 = new Event.Builder()
            .setEventKey("ABC-DEF")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Innbound Sprint Planning Meeting")
            .setEventDescription("Let's do it on 14:30 since Christoph is not available in the morning")
            .setOrganizer(organizer1)
            .setEventStart(today_08oclock)
            .setEventEnd(today_12oclock)
            .create();

    public final Room room1 = new Room.Builder()
            .setKey("ROOM1")
            .setName("Quartz B1.01")
            .create();


    public final List<Event> events = Arrays.asList(event1);

    public final Schedule daySchedule = new Schedule.Builder()
            .setRoom(room1)
            .setScheduleServerSyncTime(new Date())
            .setEvents(events)
            .create();

    public final Period dayPeriod = new Period.Builder()
            .setStartDateTime(today_00oclock)
            .setEndDateTime(today_24oclock)
            .setPeriodType(PeriodType.DAY)
            .create();

    public final PeriodSchedule periodSchedule = new PeriodSchedule.Builder()
            .setPeriod(dayPeriod)
            .setSchedule(daySchedule)
            .create();

    public final Sensor sensor1 = new Sensor.Builder()
            .setRoom(room1)
            .setSensorKey("SENSOR1")
            .create();

    public final Display display1 = new Display.Builder()
            .setDisplayKey("DISPLAY1")
            .setRoom(room1)
            .setSensor(sensor1)
            .create();

    public final ScheduleInformation scheduleInformation = new ScheduleInformation.Builder().setPeriodSchedules(Arrays.asList(periodSchedule)).create();

}
