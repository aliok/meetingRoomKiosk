var MeetingRoomKioskUtils = function () {
    var self = this;

    self.getToday00oclock = function () {
        return Date.today();
    };

    self.formatDateLong = function (dateTime) {
        return dateTime.toString("dddd, MMMM d yyyy");
    };

    self.formatTime = function (dateTime) {
        return dateTime.toString("HH:mm");
    };

    self.formatTimeRange = function (startDateTime, endDateTime) {
        return self.formatTime(startDateTime) + " - " + self.formatTime(endDateTime);
    };

    /**
     * Converts "2012-10-30 10:30:22.111" to 10.5
     * @param dateTime
     */
    self.getTimeInFloat = function (dateTime) {
        var hours = dateTime.getHours();
        var minutes = dateTime.getMinutes();

        return hours + minutes / 60;
    };
};

var MeetingRoomKiosk = function () {
    var utils = new MeetingRoomKioskUtils();

    var heightOfHeader = 100;
    var heightOfFooter = 20;
    var heightOfViewPort = $(window).height();


    var heightOfDayCalendarHeader = 50;

    var dayStartHour = 6;
    var dayEndHour = 22;

    var initialHoursToShowOnCalendar = 18 - 8;      // 8:00 - 18:00 will be shown by default. I mean scroll of calendar will be set like that

    var heightOfDayCalendar = heightOfViewPort - heightOfHeader - heightOfFooter - heightOfDayCalendarHeader;
    var heightOfDayCalendarEachOur = heightOfDayCalendar / initialHoursToShowOnCalendar;
    var heightOfDayCalendarEachOurPart = heightOfDayCalendarEachOur / 2;      // one hour consists of 2 hour parts

    var self = this;

    // public method
    self.initialize = function (initialView) {
        $('#dailyCalendarContainer').hide();
        $('#weeklyCalendarContainer').hide();

        initializeDayView();

        if (initialView === 'DAY') {
            $('#dailyCalendarContainer').show();

            // offset from #calendar div
            var offsetOf8Oclock = heightOfDayCalendarEachOur * (8 - dayStartHour);

            $('#dailyCalendar').animate({scrollTop: offsetOf8Oclock}, 500);
        }
    };

    // public method
    self.fetchSchedule = function () {
        var scheduleInfo =
        {"periodSchedules": [
            {
                "period": {
                    "startDateTime": 1409781600000,
                    "endDateTime": 1409868000000,
                    "periodType": "DAY"
                },
                "schedule": {
                    "room": {
                        "key": "ROOM1",
                        "name": "Quartz B1.01"
                    },
                    "scheduleServerSyncTime": 1409857464875,
                    "events": [
                        {
                            "eventKey": "ABC-DEF",
                            "eventTitle": "Innbound Sprint Planning Meeting",
                            "eventDescription": "Let's do it on 14:30 since Christoph is not available in the morning",
                            "eventStart": 1409810400000,
                            "eventEnd": 1409824800000,
                            "allDayEvent": false,
                            "organizer": {
                                "firstName": "Ali",
                                "lastName": "Ok",
                                "email": "ali@example.com",
                                "attendeeStatus": "ACCEPTED"
                            },
                            "attendees": [
                                {
                                    "firstName": "Ali",
                                    "lastName": "Ok",
                                    "email": "ali@example.com",
                                    "attendeeStatus": "ACCEPTED"
                                },
                                {
                                    "firstName": "Rene",
                                    "lastName": "Gunther",
                                    "email": "rene@example.com",
                                    "attendeeStatus": "ACCEPTED"
                                }
                            ]
                        }
                    ]
                }
            }
        ]};

        function mockAsyncRequest(callback) {
            callback(scheduleInfo);
        }

        mockAsyncRequest(function (scheduleInfo) {
            var periodSchedules = scheduleInfo["periodSchedules"];

            for (var i = 0; i < periodSchedules.length; i++) {
                var periodSchedule = periodSchedules[i];
                if (periodSchedule.period.periodType === "DAY") {
                    var room = periodSchedule.schedule.room;
                    var events = periodSchedule.schedule.events;

                    if (events && events.length) {
                        for (var j = 0; j < events.length; j++) {
                            var event = events[j];
                            var organizer = event.organizer;

                            self.addDayEventOnUI(
                                event.eventKey,
                                new Date(event.eventStart),
                                new Date(event.eventEnd),
                                event.eventTitle,
                                organizer.firstName + " " + organizer.lastName,
                                "info"
                            );
                        }
                    }
                }
            }

        });

    };

    var initializeDayView = function () {
        $('#header').css('height', heightOfHeader);
        $('#footer').css('height', heightOfFooter);

        $('#dailyCalendarHeader').css('height', heightOfDayCalendarHeader);
        $('#dailyCalendar').css('height', heightOfDayCalendar);

        $('#cal-day-box').find('.cal-day-hour-part').css('height', heightOfDayCalendarEachOurPart);


        self.clearDayEvents();
//        self.addDayEventOnUI(123,
//            Date.today().set({minute: 45, hour: 10, day: 15, month: 6, year: 2008}),
//            Date.today().set({minute: 0, hour: 12, day: 15, month: 6, year: 2008}),
//            "Asdasd ads", "Ali Ok", "info");

//        self.addDayEventOnUI(789, 7, 8, "Asdasd ads 3 asdasdnkj adfihksjdnfm  aoijksdf,m  23ojilksdf", "Hidayet Turkoglu", "warning");
//        self.addDayEventOnUI(789, 12, 17, "Asdasd ads 3 asdasdnkj adfihksjdnfm  aoijksdf,m  23ojilksdf", "Hidayet Turkoglu", "success");
    };

    self.clearDayEvents = function () {
        $('#dailyCalendar').find('.day-event').remove();
    };

    self.addDayEventOnUI = function (eventKey, startDateTime, endDateTime, title, organizer, type) {
        var startHour = utils.getTimeInFloat(startDateTime);
        var endHour = utils.getTimeInFloat(endDateTime);

        var hours = utils.formatTimeRange(startDateTime, endDateTime);
        var marginTop = (startHour - dayStartHour) * heightOfDayCalendarEachOur;
        var height = (endHour - startHour) * heightOfDayCalendarEachOur;

        var eventDiv = "<div class='pull-left day-event day-highlight dh-event-" + type + "' style='margin-top: " + marginTop + "px; height: " + height + "px'>" +
            " <span class='cal-hours'>" + hours + "</span>" +
            " <a data-event-key='" + eventKey + "' data-event-class='event-" + type + "' class='event-item'>" +
            organizer + "<br/>" +
            title + "</a>" +
            "</div>";

        $('#cal-day-panel').append(eventDiv);
    };

};