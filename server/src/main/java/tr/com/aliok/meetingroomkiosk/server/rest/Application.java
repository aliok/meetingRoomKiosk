package tr.com.aliok.meetingroomkiosk.server.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import tr.com.aliok.meetingroomkiosk.server.AppConfig;

@ComponentScan
@EnableAutoConfiguration
@Import(AppConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}