package io.cell.service.habitat.repositories;

import io.cell.service.habitat.model.Region;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring-data-mongo CRUD-repository реализация для {@link Region}
 */
@Repository
public interface RegionRepository extends MongoRepository<Region, UUID> {
}
