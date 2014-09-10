package tr.com.aliok.meetingroomkiosk.server.rest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import tr.com.aliok.meetingroomkiosk.model.api.AttendeeStatus;
import tr.com.aliok.meetingroomkiosk.model.api.PeriodType;
import tr.com.aliok.meetingroomkiosk.server.model.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class SampleData {

    public final DateTime now = new DateTime();
    public final DateTime monday_00oclock = now.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay();
    public final DateTime tuesday_00oclock = now.withDayOfWeek(DateTimeConstants.TUESDAY).withTimeAtStartOfDay();
    public final DateTime wednesday_00oclock = now.withDayOfWeek(DateTimeConstants.WEDNESDAY).withTimeAtStartOfDay();
    public final DateTime thursday_00oclock = now.withDayOfWeek(DateTimeConstants.THURSDAY).withTimeAtStartOfDay();
    public final DateTime friday_00oclock = now.withDayOfWeek(DateTimeConstants.FRIDAY).withTimeAtStartOfDay();
    public final DateTime friday_24oclock = monday_00oclock.withDayOfWeek(DateTimeConstants.FRIDAY).plusDays(1).minusMillis(1);

    public final DateTime today_00oclock = now.withTimeAtStartOfDay();
    public final DateTime today_24oclock = today_00oclock.plusDays(1).minusMillis(1);

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

    public final Event event_today_08oclock = new Event.Builder()
            .setEventKey("event_today_08oclock")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Innbound Sprint Planning Meeting")
            .setEventDescription("Let's do it on 08:30 since Christoph is not available in the morning")
            .setOrganizer(organizer1)
            .setEventStart(today_00oclock.withHourOfDay(8).toDate())
            .setEventEnd(today_00oclock.withHourOfDay(11).toDate())
            .create();

    public final Event event_today_15oclock = new Event.Builder()
            .setEventKey("event_today_15oclock")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Book club")
            .setEventDescription("Join the club")
            .setOrganizer(organizer1)
            .setEventStart(today_00oclock.withHourOfDay(15).toDate())
            .setEventEnd(today_00oclock.withHourOfDay(16).withMinuteOfHour(15).toDate())
            .create();

    public final Event event_monday_12oclock = new Event.Builder()
            .setEventKey("event_monday_12oclock")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Review meeting")
            .setEventDescription("Review and demo")
            .setOrganizer(organizer1)
            .setEventStart(monday_00oclock.withHourOfDay(12).toDate())
            .setEventEnd(monday_00oclock.withHourOfDay(13).withMinuteOfHour(30).toDate())
            .create();

    public final Event event_tuesday_11oclock = new Event.Builder()
            .setEventKey("event_tuesday_11oclock")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Review meeting")
            .setEventDescription("Review and demo")
            .setOrganizer(organizer1)
            .setEventStart(tuesday_00oclock.withHourOfDay(11).toDate())
            .setEventEnd(tuesday_00oclock.withHourOfDay(13).withMinuteOfHour(0).toDate())
            .create();

    public final Event event_wednesday_11oclock = new Event.Builder()
            .setEventKey("event_wednesday_11oclock")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Scrum meeting")
            .setEventDescription("Daily scrum")
            .setOrganizer(organizer1)
            .setEventStart(wednesday_00oclock.withHourOfDay(11).toDate())
            .setEventEnd(wednesday_00oclock.withHourOfDay(14).withMinuteOfHour(0).toDate())
            .create();

    public final Event event_thursday_11oclock = new Event.Builder()
            .setEventKey("event_thursday_11oclock")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Scrum meeting")
            .setEventDescription("Daily scrum")
            .setOrganizer(organizer1)
            .setEventStart(thursday_00oclock.withHourOfDay(11).toDate())
            .setEventEnd(thursday_00oclock.withHourOfDay(14).withMinuteOfHour(0).toDate())
            .create();

    public final Event event_friday_11oclock = new Event.Builder()
            .setEventKey("event_friday_11oclock")
            .setAllDayEvent(false)
            .setAttendees(Arrays.asList(attendee1, attendee2))
            .setEventTitle("Scrum meeting")
            .setEventDescription("Daily scrum")
            .setOrganizer(organizer1)
            .setEventStart(friday_00oclock.withHourOfDay(11).toDate())
            .setEventEnd(friday_00oclock.withHourOfDay(14).withMinuteOfHour(0).toDate())
            .create();

    public final Room room1 = new Room.Builder()
            .setKey("ROOM1")
            .setName("Quartz B1.01")
            .create();


    public final List<Event> dayEvents = Arrays.asList(event_today_08oclock, event_today_15oclock);

    public final List<Event> weekEvents = Arrays.asList(
            event_today_08oclock,
            event_today_15oclock,
            event_monday_12oclock,
            event_tuesday_11oclock,
            event_wednesday_11oclock,
            event_thursday_11oclock,
            event_friday_11oclock);

    public final Schedule daySchedule = new Schedule.Builder()
            .setRoom(room1)
            .setScheduleServerSyncTime(new Date())
            .setEvents(dayEvents)
            .create();

    public final Schedule weekSchedule = new Schedule.Builder()
            .setRoom(room1)
            .setScheduleServerSyncTime(new Date())
            .setEvents(weekEvents)
            .create();

    public final Period dayPeriod = new Period.Builder()
            .setStartDateTime(today_00oclock.toDate())
            .setEndDateTime(today_24oclock.toDate())
            .setPeriodType(PeriodType.DAY)
            .create();

    public final Period weekPeriod = new Period.Builder()
            .setStartDateTime(monday_00oclock.toDate())
            .setEndDateTime(friday_24oclock.toDate())
            .setPeriodType(PeriodType.WEEK)
            .create();

    public final PeriodSchedule dayPeriodSchedule = new PeriodSchedule.Builder()
            .setPeriod(dayPeriod)
            .setSchedule(daySchedule)
            .create();

    public final PeriodSchedule weekPeriodSchedule = new PeriodSchedule.Builder()
            .setPeriod(weekPeriod)
            .setSchedule(weekSchedule)
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

    public final ScheduleInformation scheduleInformation = new ScheduleInformation.Builder()
            .setPeriodSchedules(Arrays.asList(dayPeriodSchedule, weekPeriodSchedule))
            .create();

}
