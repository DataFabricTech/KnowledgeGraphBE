package com.example.datafabric.infrastructure.outbox;

import com.example.datafabric.domain.outbox.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

}
