package tr.com.aliok.meetingroomkiosk.server.service;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import tr.com.aliok.meetingroomkiosk.server.service.model.GoogleCloudMessage;
import tr.com.aliok.meetingroomkiosk.server.service.model.GoogleCloudMessageResponse;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public interface GoogleCloudMessagingRestClient {

    @POST("/gcm/send")
    public void sendMessage(@Body GoogleCloudMessage googleCloudMessage, Callback<GoogleCloudMessageResponse> callback);

}
