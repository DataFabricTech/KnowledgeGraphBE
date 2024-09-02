package com.tmax.datafabric.infrastructure.train;

import com.tmax.datafabric.domain.train.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTrainRepository extends JpaRepository<Train, Long> {

}
