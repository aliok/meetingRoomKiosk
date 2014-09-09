package tr.com.aliok.meetingroomkiosk.android.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import tr.com.aliok.meetingroomkiosk.android.Constants;

public class AppUtils {

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Utility to override the default application font.
     * <p/>
     * Using the solution in http://stackoverflow.com/questions/2711858/is-it-possible-to-set-font-for-entire-application
     */
    public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read asset and return its content.
     *
     * @param assetManager manager that allows us to access the resource
     * @param filePath     without the prefix "assets"
     * @return Content of the asset
     */
    public static String readCharAsset(AssetManager assetManager, String filePath) {
        // see http://stackoverflow.com/questions/9674815/trouble-with-reading-file-from-assets-folder-in-android

        InputStreamReader inputStreamReader = null;
        try {
            final BufferedInputStream inputStream = new BufferedInputStream(assetManager.open("mock_rest/getSchedule.json"));
            inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8.name());
            return CharStreams.toString(inputStreamReader);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Unable to read asset :" + filePath, e);
            return null;
        } finally {
            if (inputStreamReader != null)
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    Log.e(Constants.TAG, "Cannot close stream reader for asset :" + filePath, e);
                }
        }
    }

}
