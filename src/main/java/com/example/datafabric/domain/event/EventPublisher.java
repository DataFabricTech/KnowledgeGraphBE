package com.example.datafabric.domain.event;

public interface EventPublisher {

    void publish(Object event);
}
