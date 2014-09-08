package tr.com.aliok.meetingroomkiosk.server.model;

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

    public static class Builder {
        private String key;
        private String name;

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Room create() {
            final Room room = new Room();
            room.key = this.key;
            room.name = this.name;
            return room;
        }
    }

}
