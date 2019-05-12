package io.cell.service.habitat.services;

import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.Cell;
import io.cell.service.habitat.repositories.CellRepository;
import io.cell.service.habitat.services.exceptions.ObjectAlreadyExistsException;
import io.cell.service.habitat.services.exceptions.ObjectNotFoundException;
import io.cell.service.habitat.utils.EntityConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CellServiceImpl implements CellService {

  private static final Logger LOG = LoggerFactory.getLogger(CellServiceImpl.class);

  private CellRepository cellRepository;
  private EntityConditionMapper entityConditionMapper;

  @Autowired
  public CellServiceImpl(CellRepository cellRepository, EntityConditionMapper entityConditionMapper) {
    this.cellRepository = cellRepository;
    this.entityConditionMapper = entityConditionMapper;
  }

  @Override
  public CompletableFuture<Cell> getCellByCoordinates(Integer X, Integer Y) {
    return CompletableFuture.supplyAsync(() -> {
      Optional<Cell> foundCell = cellRepository.findOne(getCellExampleByCoordinates(X, Y));
      if (!foundCell.isPresent()) {
        LOG.warn("Cell with coordinates:[{}:{}] not found", X, Y);
      }
      return foundCell.orElse(null);
    });
  }

  @Override
  public CompletableFuture<Boolean> create(Cell cell) {
    return CompletableFuture.supplyAsync(() -> {
      boolean cellExists = cellRepository.exists(getCellExampleByCoordinates(cell.getAddress().getX(), cell.getAddress().getY()));
      if (cellExists) {
        String errorMessage = String.format(
            "Cell with coordinates:[%d:%d] already exists",
            cell.getAddress().getX(),
            cell.getAddress().getY());
        throw new ObjectAlreadyExistsException(errorMessage);
      }
      Cell createdCell = cellRepository.insert(cell);
      LOG.debug("Cell with coordinates:[{}:{}] is created", createdCell.getAddress().getX(), createdCell.getAddress().getY());
      return true;
    }).exceptionally(throwable -> {
      LOG.error(throwable.getMessage());
      return false;
    });
  }

  @Override
  public CompletableFuture<Boolean> update(Cell cell) {
    return CompletableFuture.supplyAsync(() -> {
      boolean cellExists = cellRepository.exists(getCellExampleByCoordinates(cell.getAddress().getX(), cell.getAddress().getY()));
      if (!cellExists) {
        String errorMessage = String.format(
            "Cell with coordinates:[%d:%d] doesn't exist. Update failed.",
            cell.getAddress().getX(),
            cell.getAddress().getY());
        throw new ObjectNotFoundException(errorMessage);
      }
      try {
        Cell targetCell = read(cell.getId()).get();
        entityConditionMapper.update(targetCell, cell);
        Cell updatedCell = cellRepository.save(targetCell);
        LOG.debug("Cell with coordinates:[{}:{}] is updated", updatedCell.getAddress().getX(), updatedCell.getAddress().getY());
      } catch (Exception e) {
        LOG.error("Cell with coordinates:[{}:{}] is not updated", cell.getAddress().getX(), cell.getAddress().getY(), e);
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
      if (!cellRepository.existsById(id)) {
        throw new ObjectNotFoundException(String.format("Клетка с ID:%s не существует. Удалить не удалось.", id.toString()));
      }
      cellRepository.deleteById(id);
      LOG.debug("Cell Id:{} is removed", id.toString());
      return true;
    });
  }

  @Override
  public CompletableFuture<Boolean> delete(Cell cell) {
    return CompletableFuture.supplyAsync(() -> {
      boolean cellExists = cellRepository.exists(getCellExampleByCoordinates(cell.getAddress().getX(), cell.getAddress().getY()));
      if (!cellExists) {
        String errorMessage = String.format(
            "Cell with coordinates:[%d:%d] doesn't exist. Deletion failed.",
            cell.getAddress().getX(),
            cell.getAddress().getY());
        throw new ObjectAlreadyExistsException(errorMessage);
      }
      cellRepository.delete(cell);
      LOG.debug("Cell with coordinates:[{}:{}] is deleted", cell.getAddress().getX(), cell.getAddress().getY());
      return true;
    }).exceptionally(throwable -> {
      LOG.error(throwable.getMessage());
      return false;
    });
  }

  @Override
  public CompletableFuture<Cell> read(UUID id) {
    return CompletableFuture.supplyAsync(() -> {
      if (!cellRepository.existsById(id)) {
        String errorMessage = String.format(
            "Cell with ID:%s doesn't exist. Could not read.",
            id.toString());
        throw new ObjectNotFoundException(errorMessage);
      }
      Cell cell = cellRepository.findById(id).orElse(null);
      LOG.debug("Cell ID:{} is found", id.toString());
      return cell;
    }).exceptionally(throwable -> {
      LOG.error(throwable.getMessage());
      return null;
    });
  }

  private Example<Cell> getCellExampleByCoordinates(Integer X, Integer Y) {
    return Example.of(
        new Cell()
            .setAddress(
                new Address()
                    .setX(X)
                    .setY(Y)));
  }
}
