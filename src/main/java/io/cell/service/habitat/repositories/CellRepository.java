package io.cell.service.habitat.repositories;

import io.cell.service.habitat.model.Cell;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring-data-mongo CRUD-repository реализация для {@link Cell}
 */
@Repository
public interface CellRepository extends MongoRepository<Cell, UUID> {
}
