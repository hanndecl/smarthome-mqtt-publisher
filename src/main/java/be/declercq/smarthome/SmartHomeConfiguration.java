package be.declercq.smarthome;

import be.declercq.smarthome.lgwebos.LGSessionManager;
import be.declercq.smarthome.lgwebos.LGWebOsClient;
import be.declercq.smarthome.lgwebos.LGWebosDevice;
import be.declercq.smarthome.lgwebos.LGWebosWebSocketHandler;
import be.declercq.smarthome.lgwebos.handler.TextMessageHandler;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "smart-home")
@Getter
@Setter
public class SmartHomeConfiguration {

    private List<LGWebosDevice> lgWebosDevices;

    private List<LGWebOsClient> lgWebOsClients = new ArrayList<>();

    @Autowired
    private List<TextMessageHandler> handlers;

    @Autowired
    private LGWebosWebSocketHandler webSocketHandler;

    @Autowired
    private LGSessionManager sessionManager;

    @PostConstruct
    public void postConstruct() {
        lgWebosDevices.forEach(device -> {
                    lgWebOsClients.add(new LGWebOsClient(device, webSocketHandler, handlers, sessionManager));
                }
        );
    }

}
