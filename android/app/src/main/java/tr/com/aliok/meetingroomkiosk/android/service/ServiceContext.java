package tr.com.aliok.meetingroomkiosk.android.service;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit.RestAdapter;
import tr.com.aliok.meetingroomkiosk.android.client.RegistrationServiceClient;
import tr.com.aliok.meetingroomkiosk.android.client.ScheduleServiceClient;
import tr.com.aliok.meetingroomkiosk.android.client.mock.RegistrationServiceMockClient;
import tr.com.aliok.meetingroomkiosk.android.client.mock.ScheduleServiceMockClient;
import tr.com.aliok.meetingroomkiosk.android.client.rest.RegistrationServiceRestClient;
import tr.com.aliok.meetingroomkiosk.android.client.rest.ScheduleServiceRestClient;

/**
 * Holds the service references.
 */
public class ServiceContext {

    private final ScheduleService scheduleService;
    private final RegistrationService registrationService;

    private ServiceContext(ScheduleServiceClient scheduleServiceClient, RegistrationServiceClient registrationServiceClient) {
        this.scheduleService = new ScheduleServiceImpl(scheduleServiceClient);
        this.registrationService = new RegistrationServiceImpl(registrationServiceClient);
    }

    /**
     * Builds service context which uses rest service clients using the end point provided.
     *
     * @param endPoint Something like https://localhost:8080/
     */
    public static ServiceContext build(String endPoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .build();

        final ScheduleServiceRestClient scheduleServiceRestClient = restAdapter.create(ScheduleServiceRestClient.class);
        final RegistrationServiceRestClient registrationServiceRestClient = restAdapter.create(RegistrationServiceRestClient.class);

        return new ServiceContext(scheduleServiceRestClient, registrationServiceRestClient);
    }

    /**
     * Builds service context which uses mock data.
     */
    public static ServiceContext buildMock(AssetManager assetManager) {
        final Gson gson = buildGson();

        final ScheduleServiceMockClient scheduleServiceMockClient = new ScheduleServiceMockClient(assetManager, gson);
        final RegistrationServiceMockClient registrationServiceMockClient = new RegistrationServiceMockClient(assetManager, gson);

        return new ServiceContext(scheduleServiceMockClient, registrationServiceMockClient);
    }

    private static Gson buildGson() {
        // since REST end point returns datetime values as milliseconds and Gson by default expects something else,
        // need to do the conversion by ourselves
        // see http://stackoverflow.com/questions/4322541/how-to-convert-a-date-between-jackson-and-gson
        return new GsonBuilder()
//                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
//
//                    @Override
//                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//                            throws JsonParseException {
//
//                        final long asLong = json.getAsJsonPrimitive().getAsLong();
//                        final Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//                        utc.setTimeInMillis(asLong);
//                        return utc.getTime();
//                    }
//                })
                .create();
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

}
