package be.declercq.smarthome.lgwebos.handler;

import be.declercq.smarthome.lgwebos.LGSessionManager;
import be.declercq.smarthome.lgwebos.LGWebosDevice;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class RegistedTextMessageHandler implements TextMessageHandler {

    private final LGSessionManager sessionManager;
    private CountDownLatch countDownLatch;
    private boolean sessionState = false;

    @Override
    @SneakyThrows
    public void sendMessage(LGWebosDevice device, WebSocketSession session) {
        sessionManager.establishSession();
        countDownLatch = new CountDownLatch(1);
        try (InputStream is = this.getClass().getResourceAsStream("/templates/01-register-request.json")) {
            String msg = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            msg = StringUtils.replace(msg, "${clientKey}", device.getClientKey());
            log.info("Sending message {}", msg);
            session.sendMessage(new TextMessage(msg));
            countDownLatch.await();
            sessionManager.sessionCreated(sessionState);
        }
    }

    @Override
    public boolean canHandle(Map<String, Object> message) {
        String type = message.get("type").toString();
        return "registered".equals(type);
    }

    @Override
    public void handle(Map<String, Object> message) {
        sessionState = true;
        countDownLatch.countDown();
    }
}
