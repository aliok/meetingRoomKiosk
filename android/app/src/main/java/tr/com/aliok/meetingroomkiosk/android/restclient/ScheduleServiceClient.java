package tr.com.aliok.meetingroomkiosk.android.restclient;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tr.com.aliok.meetingroomkiosk.android.restclient.model.Attendee;
import tr.com.aliok.meetingroomkiosk.android.restclient.model.AttendeeStatus;
import tr.com.aliok.meetingroomkiosk.android.restclient.model.Event;
import tr.com.aliok.meetingroomkiosk.android.restclient.model.Schedule;

public class ScheduleServiceClient {

    public Schedule fetchSchedule() {
        final Date now = new Date();
        final Date today_00oclock = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        final Date today_08oclock = DateUtils.addHours(today_00oclock, 8);
        final Date today_12oclock = DateUtils.addHours(today_00oclock, 12);

        final Attendee attendee1 = new Attendee.AttendeeBuilder()
                .setAttendeeStatus(AttendeeStatus.ACCEPTED)
                .setEmail("ali@example.com")
                .setFirstName("Ali")
                .setLastName("Ok")
                .createAttendee();

        final Attendee attendee2 = new Attendee.AttendeeBuilder()
                .setAttendeeStatus(AttendeeStatus.ACCEPTED)
                .setEmail("rene@example.com")
                .setFirstName("Rene")
                .setLastName("Gunther")
                .createAttendee();


        final Attendee organizer1 = attendee1;

        final Event event1 = new Event.EventBuilder()
                .setEventId(192102L)
                .setAllDayEvent(false)
                .setAttendees(Arrays.asList(attendee1, attendee2))
                .setEventDescription("Innbound Sprint Planning Meeting")
                .setOrganizer(organizer1)
                .setEventStart(today_08oclock)
                .setEventEnd(today_12oclock)
                .createEvent();


        final List<Event> events = Arrays.asList(event1);

        return new Schedule.ScheduleBuilder()
                .setRoomId("quartz")
                .setRoomName("Quartz B1.01")
                .setScheduleServerSyncTime(new Date())
                .setEvents(events)
                .createSchedule();
    }

}
