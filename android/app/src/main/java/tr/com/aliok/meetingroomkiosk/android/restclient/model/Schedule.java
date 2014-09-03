package tr.com.aliok.meetingroomkiosk.android.restclient.model;

import java.util.Date;
import java.util.List;

public class Schedule {

    private String roomId;
    private String roomName;
    private Date scheduleServerSyncTime;
    private List<Event> events;

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public Date getScheduleServerSyncTime() {
        return scheduleServerSyncTime;
    }

    public List<Event> getEvents() {
        return events;
    }

    public static class ScheduleBuilder {
        private String roomId;
        private String roomName;
        private Date scheduleServerSyncTime;
        private List<Event> events;

        public ScheduleBuilder setRoomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public ScheduleBuilder setRoomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public ScheduleBuilder setScheduleServerSyncTime(Date scheduleServerSyncTime) {
            this.scheduleServerSyncTime = scheduleServerSyncTime;
            return this;
        }

        public ScheduleBuilder setEvents(List<Event> events) {
            this.events = events;
            return this;
        }

        public Schedule createSchedule() {
            final Schedule schedule = new Schedule();
            schedule.roomId = this.roomId;
            schedule.roomName = this.roomName;
            schedule.scheduleServerSyncTime = this.scheduleServerSyncTime;
            schedule.events = this.events;
            return schedule;
        }
    }

}
