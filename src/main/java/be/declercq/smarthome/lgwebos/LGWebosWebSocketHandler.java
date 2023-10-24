package be.declercq.smarthome.lgwebos;

import be.declercq.smarthome.lgwebos.handler.TextMessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class LGWebosWebSocketHandler extends AbstractWebSocketHandler {

    private final List<TextMessageHandler> handlerList;
    private final LGSessionManager sessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connection established {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("connection closed {} Reason: {}", session.getId(), status.getReason());
        sessionManager.sessionCreated(false);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception {
        log.info("handleTextMessage {}", msg.getPayload());
        Map<String, Object> message = new ObjectMapper().readValue(msg.getPayload(), HashMap.class);
        for (TextMessageHandler handler : handlerList) {
            if (handler.canHandle(message)) {
                handler.handle(message);
                return;
            }
        }
        throw new IllegalArgumentException("Unable to handle message with payload: " + msg.getPayload());
    }
}
