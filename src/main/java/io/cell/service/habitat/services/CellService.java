package io.cell.service.habitat.services;

import io.cell.service.habitat.model.Cell;

import java.util.concurrent.CompletableFuture;

public interface CellService extends BaseModelService<Cell> {

  CompletableFuture<Cell> getCellByCoordinate(Integer X, Integer Y);

}
