package com.example.datafabric.domain.outbox;

import java.util.List;

public interface OuxboxEventRepository {

    OutboxEvent save(OutboxEvent outboxEvent);

    List<OutboxEvent> findByCounts(int count);

    void delete(OutboxEvent outboxEvent);

}
