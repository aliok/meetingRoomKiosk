package tr.com.aliok.meetingroomkiosk.android.restclient.model;

import tr.com.aliok.meetingroomkiosk.model.api.RoomModel;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
public class Room implements RoomModel {
    private String key;
    private String name;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

}
