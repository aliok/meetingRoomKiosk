package tr.com.aliok.meetingroomkiosk.android.client.mock;

import android.content.res.AssetManager;

import com.google.gson.Gson;

import tr.com.aliok.meetingroomkiosk.android.client.ScheduleServiceClient;
import tr.com.aliok.meetingroomkiosk.android.model.ScheduleInformation;
import tr.com.aliok.meetingroomkiosk.android.util.AppUtils;

/**
 * Uses stored mock data.
 *
 * @see tr.com.aliok.meetingroomkiosk.android.client.ScheduleServiceClient
 */
public class ScheduleServiceMockClient implements ScheduleServiceClient {
    private final Gson gson;
    private final AssetManager assetManager;

    public ScheduleServiceMockClient(AssetManager assetManager, Gson gson) {
        this.gson = gson;
        this.assetManager = assetManager;
    }

    @Override
    public ScheduleInformation getSchedule(String token) {
        return gson.fromJson(AppUtils.readCharAsset(assetManager, "mock_rest/getSchedule.json"), ScheduleInformation.class);
    }
}
