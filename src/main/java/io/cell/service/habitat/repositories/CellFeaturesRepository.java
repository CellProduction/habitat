package io.cell.service.habitat.repositories;

import io.cell.service.habitat.model.CellFeatures;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring-data-mongo CRUD-repository реализация для {@link CellFeatures}
 */
@Repository
public interface CellFeaturesRepository extends MongoRepository<CellFeatures, UUID> {
}
