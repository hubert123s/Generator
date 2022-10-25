package com.example.generator;

import com.example.generator.model.GeneratorParameters;
import com.example.generator.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneratorRepository extends JpaRepository<GeneratorParameters, Long> {
    Optional<GeneratorParameters> findFirstByStatus(Status status);

    List<GeneratorParameters> findAllByStatus(Status status);
}
