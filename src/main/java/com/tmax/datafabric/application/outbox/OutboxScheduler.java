package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.domain.event.EventPublisher;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OuxboxEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OuxboxEventRepository outboxEventRepository;
    private final EventPublisher eventPublisher;
    private final TransactionTemplate transactionTemplate;

    /***
     * outbox transaction은 반드시 eventually consistency 보장 해야 함
     * DB deadlock or network connection timeout과 같은 exception은 허용 가능 (scheduler에 의해 eventually consistency 보장)
     * "event handler logic 자체에서 error가 있으면 안됨"
     */

    @Scheduled(initialDelay = 5000L, fixedRate = 10000L)
    public void outboxSchedule() {
        List<OutboxEvent> outboxEventList = outboxEventRepository.findByCounts(10);

        if (outboxEventList.isEmpty()) {
            return;
        }

        for (OutboxEvent outboxEvent : outboxEventList) {
            try {
                transactionTemplate.executeWithoutResult(action -> {
                    eventPublisher.publish(outboxEvent);
                    outboxEventRepository.delete(outboxEvent);
                });
            } catch (Exception e) {
                log.error("Fail to handle outbox event (severe error)", e);
                break;
            }
        }
    }


}
