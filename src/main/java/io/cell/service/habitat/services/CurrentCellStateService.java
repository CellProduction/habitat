package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CurrentCellState;
import io.cell.service.habitat.model.Address;

import java.util.concurrent.CompletableFuture;

/**
 * Сервис для работы с текущим состоянием клетки {@link io.cell.service.habitat.dto.client.CurrentCellState}
 */
public interface CurrentCellStateService {

  CompletableFuture<CurrentCellState> getCellStateByAddress(Address address);

  CompletableFuture<CurrentCellState> getCellStateByCoordinates(Integer x, Integer y);

}
