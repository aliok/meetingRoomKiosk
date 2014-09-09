package tr.com.aliok.meetingroomkiosk.android.client.rest;


import retrofit.http.GET;
import retrofit.http.Path;
import tr.com.aliok.meetingroomkiosk.android.client.RegistrationServiceClient;
import tr.com.aliok.meetingroomkiosk.android.model.Display;

/**
 * Connects to real rest end point.
 *
 * @see tr.com.aliok.meetingroomkiosk.android.client.RegistrationServiceClient
 */
public interface RegistrationServiceRestClient extends RegistrationServiceClient {

    @Override
    @GET("/registerDisplay?displayKey={displayKey}&password={password}&gcmRegistrationId={gcmRegistrationId}&roomKey={roomKey}")
    String registerDisplay(@Path("displayKey") String displayKey,
                           @Path("password") String password,
                           @Path("gcmRegistrationId") String gcmRegistrationId,
                           @Path("roomKey") String roomKey);

    @Override
    @GET("/isDisplayTokenValid?token={token}")
    boolean isDisplayTokenValid(@Path("token") String token);

    @Override
    @GET("/getDisplayInformation?token={token}")
    Display getDisplayInformation(@Path("token") String token);

}
