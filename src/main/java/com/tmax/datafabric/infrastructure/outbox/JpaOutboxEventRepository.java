package com.tmax.datafabric.infrastructure.outbox;

import com.tmax.datafabric.domain.outbox.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

}
