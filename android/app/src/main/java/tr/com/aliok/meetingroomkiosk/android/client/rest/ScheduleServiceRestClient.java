package tr.com.aliok.meetingroomkiosk.android.client.rest;


import retrofit.http.GET;
import retrofit.http.Path;
import tr.com.aliok.meetingroomkiosk.android.client.ScheduleServiceClient;
import tr.com.aliok.meetingroomkiosk.android.model.ScheduleInformation;

/**
 * Connects to real rest end point.
 *
 * @see tr.com.aliok.meetingroomkiosk.android.client.ScheduleServiceClient
 */
public interface ScheduleServiceRestClient extends ScheduleServiceClient {

    @Override
    @GET("/getSchedule?token={user}")
    ScheduleInformation getSchedule(@Path("token") String token);

}
