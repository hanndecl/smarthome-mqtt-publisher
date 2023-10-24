package be.declercq.smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SmartHomeConfiguration.class)
public class SmartHomeLauncher {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeLauncher.class, args);
    }

}

