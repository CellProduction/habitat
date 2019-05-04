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
  public CompletableFuture<Cell> getCellByCoordinate(Integer X, Integer Y) {
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
      if (cellRepository.existsById(cell.getId())) {
        throw new ObjectAlreadyExistsException(String.format("Клетка с Id:%s уже создана", cell.getId().toString()));
      }
      Cell createdCell = cellRepository.insert(cell);
      LOG.debug("Cell Id:{} is created", createdCell.getId().toString());
      return true;
    });
  }

  @Override
  public CompletableFuture<Boolean> update(Cell cell) {
    return CompletableFuture.supplyAsync(() -> {
      if (!cellRepository.existsById(cell.getId())) {
        throw new ObjectNotFoundException(String.format("Клетка с ID:%s не существует. Обновить не удалось.", cell.getId().toString()));
      }
      try {
        Cell targetCell = read(cell.getId()).get();
        entityConditionMapper.update(targetCell, cell);
        Cell updatedCell = cellRepository.save(targetCell);
        LOG.debug("Обновленa клетка с Id:{}", updatedCell.getId().toString());
      } catch (Exception e) {
        LOG.error("Cell Id:{} is not updated", cell.getId().toString(), e);
        return false;
      }
      return true;
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
    return delete(cell.getId());
  }

  @Override
  public CompletableFuture<Cell> read(UUID id) {
    return CompletableFuture.supplyAsync(() -> {
      if (!cellRepository.existsById(id)) {
        throw new ObjectNotFoundException(String.format("Клетка с ID:%s не существует. Прочитать не удалось.", id.toString()));
      }
      Cell cell = cellRepository.findById(id).orElse(null);
      LOG.debug("Cell ID:{} is found", id.toString());
      return cell;
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
