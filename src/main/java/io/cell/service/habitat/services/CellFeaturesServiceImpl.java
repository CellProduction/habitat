package io.cell.service.habitat.services;

import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.CellFeatures;
import io.cell.service.habitat.repositories.CellFeaturesRepository;
import io.cell.service.habitat.services.exceptions.ObjectAlreadyExistsException;
import io.cell.service.habitat.services.exceptions.ObjectNotFoundException;
import io.cell.service.habitat.utils.EntityConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CellFeaturesServiceImpl implements CellFeaturesService {

  private static final Logger LOG = LoggerFactory.getLogger(CellFeaturesServiceImpl.class);

  private CellFeaturesRepository featuresRepository;
  private EntityConditionMapper entityConditionMapper;

  @Autowired
  public CellFeaturesServiceImpl(CellFeaturesRepository featuresRepository, EntityConditionMapper entityConditionMapper) {
    this.featuresRepository = featuresRepository;
    this.entityConditionMapper = entityConditionMapper;
  }

  @Override
  public CompletableFuture<CellFeatures> getCellFeaturesByCoordinates(Integer X, Integer Y) {
    return CompletableFuture.supplyAsync(() -> {
      Optional<CellFeatures> foundCellFeatures = featuresRepository.findOne(getCellFeaturesExampleByCoordinates(X, Y));
      if (!foundCellFeatures.isPresent()) {
        LOG.warn("CellFeatures with coordinates:[{}:{}] not found", X, Y);
      }
      return foundCellFeatures.orElse(null);
    });
  }

  @Override
  public CompletableFuture<List<CellFeatures>> getArea(Integer x0, Integer y0, Integer xN, Integer yN) {
    return CompletableFuture.completedFuture(
        featuresRepository.findAllByAddress_XBetweenAndAddress_YBetween(x0 - 1, xN + 1, y0 - 1, yN + 1));
  }

  @Override
  public CompletableFuture<Boolean> create(CellFeatures cellFeatures) {
    return CompletableFuture.supplyAsync(() -> {
      boolean cellFeaturesExists = featuresRepository.exists(getCellFeaturesExampleByCoordinates(cellFeatures.getAddress().getX(), cellFeatures.getAddress().getY()));
      if (cellFeaturesExists) {
        String errorMessage = String.format(
            "CellFeatures with coordinates:[%d:%d] already exists",
            cellFeatures.getAddress().getX(),
            cellFeatures.getAddress().getY());
        throw new ObjectAlreadyExistsException(errorMessage);
      }
      CellFeatures createdCellFeatures = featuresRepository.insert(cellFeatures);
      LOG.debug("CellFeatures with coordinates:[{}:{}] is created", createdCellFeatures.getAddress().getX(), createdCellFeatures.getAddress().getY());
      return true;
    }).exceptionally(throwable -> {
      LOG.error(throwable.getMessage());
      return false;
    });
  }

  @Override
  public CompletableFuture<Boolean> update(CellFeatures cellFeatures) {
    return CompletableFuture.supplyAsync(() -> {
      boolean cellFeaturesExists = featuresRepository.exists(getCellFeaturesExampleByCoordinates(cellFeatures.getAddress().getX(), cellFeatures.getAddress().getY()));
      if (!cellFeaturesExists) {
        String errorMessage = String.format(
            "CellFeatures with coordinates:[%d:%d] doesn't exist. Update failed.",
            cellFeatures.getAddress().getX(),
            cellFeatures.getAddress().getY());
        throw new ObjectNotFoundException(errorMessage);
      }
      try {
        CellFeatures targetCellFeatures = read(cellFeatures.getId()).get();
        entityConditionMapper.update(targetCellFeatures, cellFeatures);
        CellFeatures updatedCellFeatures = featuresRepository.save(targetCellFeatures);
        LOG.debug("CellFeatures with coordinates:[{}:{}] is updated", updatedCellFeatures.getAddress().getX(), updatedCellFeatures.getAddress().getY());
      } catch (Exception e) {
        LOG.error("CellFeatures with coordinates:[{}:{}] is not updated", cellFeatures.getAddress().getX(), cellFeatures.getAddress().getY(), e);
        return false;
      }
      return true;
    }).exceptionally(throwable -> {
      LOG.error(throwable.getMessage());
      return false;
    });
  }

  @Override
  public CompletableFuture<Boolean> delete(UUID id) {
    return CompletableFuture.supplyAsync(() -> {
      if (!featuresRepository.existsById(id)) {
        throw new ObjectNotFoundException(String.format("Набор ствойств клетки с ID:%s не существует. Удалить не удалось.", id.toString()));
      }
      featuresRepository.deleteById(id);
      LOG.debug("CellFeatures Id:{} is removed", id.toString());
      return true;
    });
  }

  @Override
  public CompletableFuture<Boolean> delete(CellFeatures cellFeatures) {
    return CompletableFuture.supplyAsync(() -> {
      boolean cellFeaturesExists = featuresRepository.exists(getCellFeaturesExampleByCoordinates(cellFeatures.getAddress().getX(), cellFeatures.getAddress().getY()));
      if (!cellFeaturesExists) {
        String errorMessage = String.format(
            "CellFeatures with coordinates:[%d:%d] doesn't exist. Deletion failed.",
            cellFeatures.getAddress().getX(),
            cellFeatures.getAddress().getY());
        throw new ObjectAlreadyExistsException(errorMessage);
      }
      featuresRepository.delete(cellFeatures);
      LOG.debug("CellFeatures with coordinates:[{}:{}] is deleted", cellFeatures.getAddress().getX(), cellFeatures.getAddress().getY());
      return true;
    }).exceptionally(throwable -> {
      LOG.error(throwable.getMessage());
      return false;
    });
  }

  @Override
  public CompletableFuture<CellFeatures> read(UUID id) {
    return CompletableFuture.supplyAsync(() -> {
      if (!featuresRepository.existsById(id)) {
        String errorMessage = String.format(
            "CellFeatures with ID:%s doesn't exist. Could not read.",
            id.toString());
        throw new ObjectNotFoundException(errorMessage);
      }
      CellFeatures cellFeatures = featuresRepository.findById(id).orElse(null);
      LOG.debug("CellFeatures ID:{} is found", id.toString());
      return cellFeatures;
    }).exceptionally(throwable -> {
      LOG.error(throwable.getMessage());
      return null;
    });
  }

  private Example<CellFeatures> getCellFeaturesExampleByCoordinates(Integer X, Integer Y) {
    return Example.of(
        new CellFeatures()
            .setAddress(
                new Address()
                    .setX(X)
                    .setY(Y)));
  }
}
