package io.cell.service.habitat.repositories;

import io.cell.service.habitat.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring-data-mongo CRUD-repository реализация для {@link Address}
 */
@Repository
public interface AddressRepository extends MongoRepository<Address, UUID> {
}
