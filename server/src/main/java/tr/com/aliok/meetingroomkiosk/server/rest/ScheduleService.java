package tr.com.aliok.meetingroomkiosk.server.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.aliok.meetingroomkiosk.server.model.ScheduleInformation;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
@RestController
public class ScheduleService {

    @RequestMapping("/getSchedule")
    public ScheduleInformation getScheduleInformation(@RequestParam(value = "token", required = true) String token) {
        return new SampleData().scheduleInformation;
    }

}
