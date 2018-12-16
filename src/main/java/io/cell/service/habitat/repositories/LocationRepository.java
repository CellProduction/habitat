package io.cell.service.habitat.repositories;

import io.cell.service.habitat.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring-data-mongo CRUD-repository реализация для {@link Location}
 */
@Repository
public interface LocationRepository extends MongoRepository<Location, UUID> {
}
