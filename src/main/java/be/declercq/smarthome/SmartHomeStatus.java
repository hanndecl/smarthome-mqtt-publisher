package be.declercq.smarthome;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SmartHomeStatus {


    private final MQTTPublisher mqttPublisher;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String status() {
        return "execution count: " + mqttPublisher.get();
    }
}