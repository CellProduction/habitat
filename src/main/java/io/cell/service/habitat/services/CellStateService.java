package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CellState;
import io.cell.service.habitat.model.Address;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Сервис для работы с текущим состоянием клетки {@link CellState}
 */
public interface CellStateService {

  CompletableFuture<CellState> getCellStateByAddress(Address address);

  CompletableFuture<CellState> getCellStateByCoordinates(Integer x, Integer y);

  /**
   * Возвращает клетку и окружающие ее клетки, которые составляют область заданного размера.
   * @param x
   * @param y
   * @param areaSize
   * @return
   */
  CompletableFuture<Set<CellState>> getArea(Integer x, Integer y, Integer areaSize);
}
