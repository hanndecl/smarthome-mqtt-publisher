package be.declercq.smarthome.lgwebos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class LGSessionManager {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private boolean hasSession;

    public void establishSession() {
        readWriteLock.writeLock().lock();
        log.info("Take session Lock");
    }

    public boolean hasSession() {
        try {
            readWriteLock.readLock().lock();
            return hasSession;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void sessionCreated(boolean hasSession) {
        this.hasSession = hasSession;
        readWriteLock.writeLock().unlock();
        log.info("Release session Lock");
    }
}
