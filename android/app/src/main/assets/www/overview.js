var MeetingRoomKiosk = function () {
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


    self.initializeDayView = function () {
        $('#dailyCalendarContainer').show();
        $('#weeklyCalendarContainer').hide();

        $('#header').css('height', heightOfHeader);
        $('#footer').css('height', heightOfFooter);

        $('#dailyCalendarHeader').css('height', heightOfDayCalendarHeader);
        $('#dailyCalendar').css('height', heightOfDayCalendar);

        $('#cal-day-box').find('.cal-day-hour-part').css('height', heightOfDayCalendarEachOurPart);

        // offset from #calendar div
        var offsetOf8Oclock = heightOfDayCalendarEachOur * (8 - dayStartHour);

        $('#dailyCalendar').animate({scrollTop: offsetOf8Oclock}, 500);


        self.clearDayEvents();
        self.addDayEvent(123, 8.5, 9.5, "Asdasd ads", "Ali Ok", "info");
        self.addDayEvent(456, 6, 7, "Asdasd ads 2", "Rene Gunther", "error");
        self.addDayEvent(789, 7, 8, "Asdasd ads 3 asdasdnkj adfihksjdnfm  aoijksdf,m  23ojilksdf", "Hidayet Turkoglu", "warning");
        self.addDayEvent(789, 12, 17, "Asdasd ads 3 asdasdnkj adfihksjdnfm  aoijksdf,m  23ojilksdf", "Hidayet Turkoglu", "success");
    };

    self.clearDayEvents = function () {
        $('#dailyCalendar').find('.day-event').remove();
    };

    self.addDayEvent = function (eventId, startHour, endHour, title, organizer, type) {
        var hours = "10:00 - 10:30";  //TODO
        var marginTop = (startHour - dayStartHour) * heightOfDayCalendarEachOur;
        var height = (endHour - startHour) * heightOfDayCalendarEachOur;

        var eventDiv = "<div class='pull-left day-event day-highlight dh-event-" + type + "' style='margin-top: " + marginTop + "px; height: " + height + "px'>" +
            " <span class='cal-hours'>" + hours + "</span>" +
            " <a data-event-id='" + eventId + "' data-event-class='event-" + type + "' class='event-item'>" +
            organizer + "<br/>" +
            title + "</a>" +
            "</div>";

        $('#cal-day-panel').append(eventDiv);
    }

};