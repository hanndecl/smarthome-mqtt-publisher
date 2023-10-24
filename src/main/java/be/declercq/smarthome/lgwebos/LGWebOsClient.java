package be.declercq.smarthome.lgwebos;

import be.declercq.smarthome.lgwebos.handler.TextMessageHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.util.List;

@Slf4j
public class LGWebOsClient {

    private final LGWebosDevice device;

    private final LGWebosWebSocketHandler webSocketHandler;

    private final List<TextMessageHandler> handlerList;
    private final LGSessionManager sessionManager;
    private WebSocketSession session;

    public LGWebOsClient(LGWebosDevice device, LGWebosWebSocketHandler webSocketHandler, List<TextMessageHandler> handlerList, LGSessionManager sessionManager) {
        this.device = device;
        this.webSocketHandler = webSocketHandler;
        this.handlerList = handlerList;
        this.sessionManager = sessionManager;
        establishConnection();
    }

    @SneakyThrows
    public void establishConnection() {
        if (!sessionManager.hasSession()) {
            URI uri = new URI("ws://" + device.getHostName() + ":3000");
            this.session = new StandardWebSocketClient().doHandshake(webSocketHandler, new WebSocketHttpHeaders(), uri).get();
            this.handlerList.stream().forEach(h -> h.sendMessage(device, session));
        }
    }


    public String getId() {
        return this.session.getId();
    }


}
