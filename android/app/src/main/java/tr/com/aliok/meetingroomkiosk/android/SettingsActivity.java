package tr.com.aliok.meetingroomkiosk.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import tr.com.aliok.meetingroomkiosk.android.model.Display;
import tr.com.aliok.meetingroomkiosk.android.model.Sensor;
import tr.com.aliok.meetingroomkiosk.android.service.ServiceContext;
import tr.com.aliok.meetingroomkiosk.android.util.CommonUtils;
import tr.com.aliok.meetingroomkiosk.android.util.SharedPrefsUtils;

import static tr.com.aliok.meetingroomkiosk.android.Constants.TAG;

public class SettingsActivity extends Activity {

    // ---- Services ----------------------- //
    private ServiceContext serviceContext;
    private GoogleCloudMessaging gcm;

    // ---- GCM related components --------- //
    private TextView mGcmRegistrationIdTextView;

    // ---- Server related components ------ //
    private LinearLayout mServerInfoContainer;
    private TextView mServerTokenTextView;
    private TextView mRoomIdTextView;

    // ---- Sensor related components ------ //
    private TextView mSensorIdTextView;
    private TextView mSensorRoomIdTextView;

    // TODO s:
    // 1. do not store username and password


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        mGcmRegistrationIdTextView = (TextView) findViewById(R.id.gcmRegistrationIdTextView);

        mServerInfoContainer = (LinearLayout) findViewById(R.id.editServerInfoContainer);
        mServerTokenTextView = (TextView) findViewById(R.id.serverTokenTextView);
        mRoomIdTextView = (TextView) findViewById(R.id.roomIdTextView);

        mSensorIdTextView = (TextView) findViewById(R.id.sensorIdTextView);
        mSensorRoomIdTextView = (TextView) findViewById(R.id.sensorRoomIdTextView);


        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (CommonUtils.checkPlayServices(this)) {
            gcm = GoogleCloudMessaging.getInstance(this);
            String gcmRegistrationId = SharedPrefsUtils.getGcmRegistrationId(getApplication());

            if (gcmRegistrationId.isEmpty()) {
                registerToGcmInBackground();
            } else {
                Log.i(TAG, "Registration id found : " + gcmRegistrationId);
                mGcmRegistrationIdTextView.setText(gcmRegistrationId);
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

        if (Constants.MOCK_DATA)
            serviceContext = ServiceContext.buildMock(getAssets());
        else
            serviceContext = ServiceContext.build(SharedPrefsUtils.getServerEndpoint(getApplication()));
    }

    //TODO check internet connection on resume

    @Override
    protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        CommonUtils.checkPlayServices(this);

        displayServerInformation();

        doRefreshSensorInformation();
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerToGcmInBackground() {
        final Context applicationContext = getApplicationContext();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg;
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(applicationContext);
                    }
                    final String registrationId = gcm.register(Constants.GCM_API_PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + registrationId;

                    // Persist the regID - no need to register again.
                    SharedPrefsUtils.storeRegistrationId(getApplication(), registrationId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show();
                mGcmRegistrationIdTextView.setText(SharedPrefsUtils.getGcmRegistrationId(getApplication()));
            }
        }.execute(null, null, null);
    }

    public void reRegisterToGcm(View view) {
        registerToGcmInBackground();
    }

    public void registerToServer(View view) {
        // register to server
        // if success, update server token and hide server info container
        // else, give a message and let the server info container visible

        // if(success)
        mServerInfoContainer.setVisibility(View.GONE);
    }

    public void toggleServerInformationContainer(View view) {
        if (mServerInfoContainer.getVisibility() == View.VISIBLE) {
            mServerInfoContainer.setVisibility(View.GONE);
        } else {
            mServerInfoContainer.setVisibility(View.VISIBLE);
        }

    }

    public void refreshSensorInformation(View view) {
        doRefreshSensorInformation();
    }

    private void displayServerInformation() {
        final String serverToken = SharedPrefsUtils.getServerToken(getApplication());
        if (serverToken.length() > 5) {
            mServerTokenTextView.setText(serverToken.substring(0, 5) + "....");
        }

        mRoomIdTextView.setText(SharedPrefsUtils.getRoomId(getApplication()));
    }

    private void doRefreshSensorInformation() {
        new AsyncTask<Void, Void, Display>() {

            ProgressDialog progressDialog;

            @Override
            protected Display doInBackground(Void... voids) {
                return serviceContext.getRegistrationService().getDisplayInformation(SharedPrefsUtils.getServerToken(getApplication()));
            }

            @Override
            protected void onPreExecute() {
                // show loading indicator
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(SettingsActivity.this);
                    progressDialog.setMessage(getResources().getString(R.string.syncing));
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                }
            }

            @Override
            protected void onPostExecute(Display display) {
                if (display == null || display.getSensor() == null) {
                    Toast.makeText(SettingsActivity.this, getResources().getText(R.string.unable_to_fetch_sensor_info), Toast.LENGTH_LONG).show();
                } else {
                    final Sensor sensor = display.getSensor();

                    mSensorIdTextView.setText(sensor.getSensorKey());
                    mSensorRoomIdTextView.setText(sensor.getRoom().getKey());
                }

                // hide loading indicator
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }.execute();

    }
}
