package com.tmax.datafabric.domain.train;

import java.util.Optional;

public interface TrainRepository {

    Train save(Train train);

    Optional<Train> findById(Long id);
}
