package com.example.datafabric.infrastructure.train;

import com.example.datafabric.domain.train.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTrainRepository extends JpaRepository<Train, Long> {

}
