package be.declercq.smarthome.lgwebos.handler;

import be.declercq.smarthome.lgwebos.LGWebosDevice;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public interface TextMessageHandler {

    void sendMessage(LGWebosDevice device, WebSocketSession session);

    boolean canHandle(Map<String, Object> message);

    void handle(Map<String, Object> message);
}
