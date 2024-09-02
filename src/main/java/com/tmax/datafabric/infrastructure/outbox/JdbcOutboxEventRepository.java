package com.tmax.datafabric.infrastructure.outbox;

import com.tmax.datafabric.domain.outbox.OutboxEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcOutboxEventRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OutboxEvent> outboxEventRowMapper = (rs, rowMapper) ->
        new OutboxEvent(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
    
    @Transactional
    public List<OutboxEvent> findByCounts(int count) {
        String sql = "select * from outbox_event limit ?";
        return jdbcTemplate.query(sql, outboxEventRowMapper, count);
    }
}
