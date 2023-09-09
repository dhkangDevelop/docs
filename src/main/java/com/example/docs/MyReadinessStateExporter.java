package com.example.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyReadinessStateExporter {
    static final Logger LOGGER = LoggerFactory.getLogger(MyReadinessStateExporter.class);
    @EventListener
    public void onStateChange(AvailabilityChangeEvent<ReadinessState> event) {
        switch (event.getState()) {
            case ACCEPTING_TRAFFIC:
                LOGGER.info("Status={}", event.getState());
                // create file /tmp/healthy
                break;
            case REFUSING_TRAFFIC:
                LOGGER.info("Status={}", event.getState());
                // remove file /tmp/healthy
                break;
        }
    }
}
