package tr.com.aliok.meetingroomkiosk.server.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import tr.com.aliok.meetingroomkiosk.server.AppConfig;
import tr.com.aliok.meetingroomkiosk.server.SystemPropertyUtils;

@ComponentScan
@EnableAutoConfiguration
@Import(AppConfig.class)
public class Application {

    public static void main(String[] args) {
        final String googleCloudMessagingAPIKey = SystemPropertyUtils.getGoogleCloudMessagingAPIKey();
        if (googleCloudMessagingAPIKey == null)
            throw new RuntimeException("Google cloud messaging api key is not provided. Please set it with GCM_API_KEY environment variable.");
        SpringApplication.run(Application.class, args);
    }
}