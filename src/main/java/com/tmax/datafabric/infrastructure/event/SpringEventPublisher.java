package com.tmax.datafabric.infrastructure.event;

import com.tmax.datafabric.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationContext applicationContext;

    @Override
    public void publish(Object event) {
        applicationContext.publishEvent(event);
    }
}
