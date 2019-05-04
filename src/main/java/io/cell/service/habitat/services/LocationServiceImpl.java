package io.cell.service.habitat.services;

import io.cell.service.habitat.model.Location;
import io.cell.service.habitat.repositories.LocationRepository;
import io.cell.service.habitat.services.exceptions.ObjectAlreadyExistsException;
import io.cell.service.habitat.services.exceptions.ObjectNotFoundException;
import io.cell.service.habitat.utils.EntityConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LocationServiceImpl implements LocationService {

  private static final Logger LOG = LoggerFactory.getLogger(LocationServiceImpl.class);

  private LocationRepository locationRepository;
  private EntityConditionMapper entityConditionMapper;

  @Autowired
  public LocationServiceImpl(LocationRepository locationRepository, EntityConditionMapper entityConditionMapper) {
    this.locationRepository = locationRepository;
    this.entityConditionMapper = entityConditionMapper;
  }

  @Override
  public CompletableFuture<Boolean> create(Location location) {
    return CompletableFuture.supplyAsync(() -> {
      if (locationRepository.existsById(location.getId())) {
        throw new ObjectAlreadyExistsException(String.format("Локация с Id:%s уже создан", location.getId().toString()));
      }
      Location createdLocation = locationRepository.insert(location);
      LOG.debug("Создана локация с Id:{}", createdLocation.getId().toString());
      return true;
    });
  }

  @Override
  public CompletableFuture<Boolean> update(Location location) {
    return CompletableFuture.supplyAsync(() -> {
      if (!locationRepository.existsById(location.getId())) {
        throw new ObjectNotFoundException(String.format("Локация с ID:%s не существует. Обновить не удалось.", location.getId().toString()));
      }
      try {
        Location targetLocation = read(location.getId()).get();
        entityConditionMapper.update(targetLocation, location);
        Location updatedLocation = locationRepository.save(targetLocation);
        LOG.debug("Обновленa локация с Id:{}", updatedLocation.getId().toString());
      } catch (Exception e) {
        LOG.error("Локация с Id:{} не обновлена", location.getId().toString(), e);
        return false;
      }
      return true;
    });
  }

  @Override
  public CompletableFuture<Boolean> delete(UUID id) {
    return CompletableFuture.supplyAsync(() -> {
      if (!locationRepository.existsById(id)) {
        throw new ObjectNotFoundException(String.format("Локация с ID:%s не существует. Удалить не удалось.", id.toString()));
      }
      locationRepository.deleteById(id);
      LOG.debug("Удалена локация с Id:{}", id.toString());
      return true;
    });
  }

  @Override
  public CompletableFuture<Boolean> delete(Location location) {
    return delete(location.getId());
  }

  @Override
  public CompletableFuture<Location> read(UUID id) {
    return CompletableFuture.supplyAsync(() -> {
      if (!locationRepository.existsById(id)) {
        throw new ObjectNotFoundException(String.format("Локация с ID:%s не существует. Прочитать не удалось.", id.toString()));
      }
      Location location = locationRepository.findById(id).get();
      LOG.debug("Найдена локация с ID:{}", location.getId().toString());
      return location;
    });
  }
}
