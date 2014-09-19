package tr.com.aliok.meetingroomkiosk.android.util;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import tr.com.aliok.meetingroomkiosk.android.Constants;

public class SharedPrefsUtils {

    public static final String PROPERTY_GCM_REG_ID = "gcm_registration_id";
    public static final String PROPERTY_SERVER_TOKEN = "server_token";
    public static final String PROPERTY_ROOM_ID = "room_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_SERVER_END_POINT = "serverBaseUrl";

    private static final String TAG = "MeetingRoomKiosk";

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @param application application
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getGcmRegistrationId(Application application) {
        final SharedPreferences prefs = getSharedPreferences(application.getApplicationContext());
        String registrationId = prefs.getString(PROPERTY_GCM_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = AppUtils.getAppVersion(application.getApplicationContext());
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param application application
     * @param regId       registration ID
     */
    public static void storeRegistrationId(Application application, String regId) {
        final SharedPreferences prefs = getSharedPreferences(application.getApplicationContext());
        int appVersion = AppUtils.getAppVersion(application.getApplicationContext());
        Log.i(TAG, "Saving regId on app version " + appVersion + " " + regId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_GCM_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public static String getServerToken(Application application) {
        //TODO GO-LIVE implement before go-live
        if (1 == 1)
            return "asdasdadasdasdasdasdasd";

        final SharedPreferences prefs = getSharedPreferences(application.getApplicationContext());
        final String serverToken = prefs.getString(PROPERTY_SERVER_TOKEN, "");
        if (serverToken.isEmpty()) {
            Log.i(TAG, "Server token not found.");
            return "";
        }
        return serverToken;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        // to persist the GCM registration id in shared preferences
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static String getRoomId(Application application) {
        //TODO GO-LIVE implement before go-live
        if (1 == 1)
            return "quartz";

        final SharedPreferences prefs = getSharedPreferences(application.getApplicationContext());
        final String roomId = prefs.getString(PROPERTY_ROOM_ID, "");
        if (roomId.isEmpty()) {
            Log.i(TAG, "Room id not found.");
            return "";
        }
        return roomId;
    }

    /**
     * Finds saved server end point. If not saved yet, returns default one, which is http://localhost:8080/
     * @param application app to get the saved property
     * @return server end point
     */
    public static String getServerEndpoint(Application application) {
        final SharedPreferences prefs = getSharedPreferences(application.getApplicationContext());
        return prefs.getString(PROPERTY_SERVER_END_POINT, Constants.DEFAULT_SERVER_END_POINT);
    }
}
