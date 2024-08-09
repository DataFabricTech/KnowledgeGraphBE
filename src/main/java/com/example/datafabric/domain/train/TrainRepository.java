package com.example.datafabric.domain.train;

import java.util.Optional;

public interface TrainRepository {

    Train save(Train train);

    Optional<Train> findById(Long id);
}
