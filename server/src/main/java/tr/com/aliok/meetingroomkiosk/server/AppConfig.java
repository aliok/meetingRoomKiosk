package tr.com.aliok.meetingroomkiosk.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

    // override bean definition from HttpMessageConvertersAutoConfiguration
    @Bean
    @ConditionalOnMissingBean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
            ObjectMapper objectMapper) {
        // use timestamps like following ones for all dates "2013-01-25T18:20:01-07:00" or "2014-09-10T22:00:00Z"
        // by default, Spring uses milliseconds such as 123456789 since epoch
        // see http://wallsofchange.wordpress.com/2013/02/02/spring-mvc-rest-services-force-jackson-to-serialize-dates-as-iso-8601-dates/
        // see http://stackoverflow.com/questions/9381665/how-can-we-configure-the-internal-jackson-mapper-when-using-resttemplate

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new ISO8601DateFormat());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setPrettyPrint(false);
        return converter;
    }

}
