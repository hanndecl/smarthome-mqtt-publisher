package be.declercq.smarthome.lgwebos.handler;

import be.declercq.smarthome.lgwebos.LGSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class VolumeTextMessageHandler extends AbstractSubscribeTextMessageHandler {

    public VolumeTextMessageHandler(LGSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getPayloadName() {
        return "volumeStatus";
    }

    @Override
    protected String getSubscribeUri() {
        return "ssap://audio/getVolume";
    }

    @Override
    protected void handlePayload(Map<String, Object> payload) {
        log.info("Volume {}", payload.get("volume"));
    }
}
