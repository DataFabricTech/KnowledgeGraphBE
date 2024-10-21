package com.tmax.datafabric.domain.outbox;

import java.util.List;

public interface OutboxEventRepository {

    OutboxEvent save(OutboxEvent outboxEvent);

    List<OutboxEvent> findByCounts(int count);

    void delete(OutboxEvent outboxEvent);

}
