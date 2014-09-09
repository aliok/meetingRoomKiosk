package tr.com.aliok.meetingroomkiosk.android.client.mock;

import android.content.res.AssetManager;

import com.google.gson.Gson;

import tr.com.aliok.meetingroomkiosk.android.client.RegistrationServiceClient;
import tr.com.aliok.meetingroomkiosk.android.model.Display;
import tr.com.aliok.meetingroomkiosk.android.util.AppUtils;

/**
 * Uses stored mock data.
 *
 * @see tr.com.aliok.meetingroomkiosk.android.client.RegistrationServiceClient
 */
public class RegistrationServiceMockClient implements RegistrationServiceClient {
    private final Gson gson;

    private final AssetManager assetManager;

    public RegistrationServiceMockClient(AssetManager assetManager, Gson gson) {
        this.gson = gson;
        this.assetManager = assetManager;
    }

    @Override
    public String registerDisplay(String displayKey, String password, String gcmRegistrationId, String roomKey) {
        return gson.fromJson(AppUtils.readCharAsset(assetManager, "mock_rest/registerDisplay.json"), String.class);
    }

    @Override
    public boolean isDisplayTokenValid(String token) {
        return gson.fromJson(AppUtils.readCharAsset(assetManager, "mock_rest/isDisplayTokenValid.json"), Boolean.class);

    }

    @Override
    public Display getDisplayInformation(String token) {
        return gson.fromJson(AppUtils.readCharAsset(assetManager, "mock_rest/getDisplayInformation.json"), Display.class);
    }
}
