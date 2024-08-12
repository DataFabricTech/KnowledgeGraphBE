package com.example.datafabric.infrastructure.train;

import com.example.datafabric.domain.train.Train;
import com.example.datafabric.domain.train.TrainRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainRepositoryAdapter implements TrainRepository {

    private final JpaTrainRepository jpaTrainRepository;

    @Override
    public Train save(Train train) {
        return jpaTrainRepository.save(train);
    }

    @Override
    public Optional<Train> findById(Long id) {
        return jpaTrainRepository.findById(id);
    }
}
