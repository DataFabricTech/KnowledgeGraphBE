package com.example.datafabric.infrastructure.outbox;

import com.example.datafabric.domain.outbox.OutboxEvent;
import com.example.datafabric.domain.outbox.OuxboxEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OuxboxEventRepositoryAdapter implements OuxboxEventRepository {

    private final JpaOutboxEventRepository jpaOutboxEventRepository;
    private final JdbcOutboxEventRepository jdbcOutboxEventRepository;

    @Override
    public OutboxEvent save(OutboxEvent outboxEvent) {
        return jpaOutboxEventRepository.save(outboxEvent);
    }

    @Override
    public List<OutboxEvent> findByCounts(int count) {
        return jdbcOutboxEventRepository.findByCounts(count);
    }

    @Override
    public void delete(OutboxEvent outboxEvent) {
        jpaOutboxEventRepository.delete(outboxEvent);
    }
}
