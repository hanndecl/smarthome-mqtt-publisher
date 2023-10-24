package be.declercq.smarthome.lgwebos.handler;

import be.declercq.smarthome.lgwebos.LGSessionManager;
import be.declercq.smarthome.lgwebos.LGWebosDevice;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractSubscribeTextMessageHandler implements TextMessageHandler {

    private final LGSessionManager sessionManager;

    @Override
    @SneakyThrows
    public void sendMessage(LGWebosDevice device, WebSocketSession session) {
        if (sessionManager.hasSession()) {
            try (InputStream is = this.getClass().getResourceAsStream("/templates/02-subscribe-request.json")) {
                String msg = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                msg = StringUtils.replace(msg, "${uuid}", UUID.randomUUID().toString());
                msg = StringUtils.replace(msg, "${subscribe-uri}", getSubscribeUri());
                log.info("Sending message {}", msg);
                session.sendMessage(new TextMessage(msg));
            }
        }
    }

    @Override
    public boolean canHandle(Map<String, Object> message) {
        Map<String, Object> payload = (Map<String, Object>) message.get("payload");
        return payload.get(getPayloadName()) != null;
    }

    @Override
    public void handle(Map<String, Object> message) {
        Map<String, Object> payload = (Map<String, Object>) message.get("payload");
        handlePayload((Map<String, Object>) payload.get(getPayloadName()));
    }

    protected abstract String getPayloadName();

    protected abstract String getSubscribeUri();

    protected abstract void handlePayload(Map<String,Object> message);
}
