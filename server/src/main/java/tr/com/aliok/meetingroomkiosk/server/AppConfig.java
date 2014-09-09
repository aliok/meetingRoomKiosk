package tr.com.aliok.meetingroomkiosk.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.aliok.meetingroomkiosk.server.manager.*;

/**
 * @author Ali Ok (ali.ok@apache.org)
 */
@Configuration
public class AppConfig {

    @Bean
    public DisplayManager displayManager() {
        return new DisplayManagerImpl();
    }

    @Bean
    public RoomManager roomManager() {
        return new RoomManagerImpl();
    }

    @Bean
    public SensorManager sensorManager() {
        return new SensorManagerImpl();
    }

}
