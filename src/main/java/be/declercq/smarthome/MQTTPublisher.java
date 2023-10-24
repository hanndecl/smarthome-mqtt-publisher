package be.declercq.smarthome;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
@Service
public class MQTTPublisher {

    private final AtomicInteger counter = new AtomicInteger();

    public int get() {
        return counter.get();
    }

    //@Scheduled(every = "10s")
    void increment() {
        counter.incrementAndGet();
    }
}
