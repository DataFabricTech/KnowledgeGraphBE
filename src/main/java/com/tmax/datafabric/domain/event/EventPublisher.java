package com.tmax.datafabric.domain.event;

public interface EventPublisher {

    void publish(Object event);
}
