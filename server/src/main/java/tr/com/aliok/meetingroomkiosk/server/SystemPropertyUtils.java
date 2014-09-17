package tr.com.aliok.meetingroomkiosk.server;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class SystemPropertyUtils {

    public static String getGoogleCloudMessagingAPIKey(){
        return System.getProperty("GCM_API_KEY");
    }

}
