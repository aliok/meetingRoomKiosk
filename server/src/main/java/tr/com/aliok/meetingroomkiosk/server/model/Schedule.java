package tr.com.aliok.meetingroomkiosk.server.model;

import tr.com.aliok.meetingroomkiosk.model.api.ScheduleModel;

import java.util.Date;
import java.util.List;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Schedule implements ScheduleModel {

    private Room room;
    private Date scheduleServerSyncTime;
    private List<Event> events;

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public Date getScheduleServerSyncTime() {
        return scheduleServerSyncTime;
    }

    @Override
    public List<Event> getEvents() {
        return events;
    }

    public static class Builder {
        private Room room;
        private Date scheduleServerSyncTime;
        private List<Event> events;

        public Builder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder setScheduleServerSyncTime(Date scheduleServerSyncTime) {
            this.scheduleServerSyncTime = scheduleServerSyncTime;
            return this;
        }

        public Builder setEvents(List<Event> events) {
            this.events = events;
            return this;
        }

        public Schedule create() {
            final Schedule schedule = new Schedule();
            schedule.room = this.room;
            schedule.scheduleServerSyncTime = this.scheduleServerSyncTime;
            schedule.events = this.events;
            return schedule;
        }
    }

}