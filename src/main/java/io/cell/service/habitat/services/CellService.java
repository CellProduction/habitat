package io.cell.service.habitat.services;

import io.cell.service.habitat.model.Cell;

import java.util.concurrent.CompletableFuture;

public interface CellService extends BaseModelService<Cell> {

  CompletableFuture<Cell> getCellByCoordinates(Integer X, Integer Y);

}
