package io.cell.service.habitat.services;

import io.cell.service.habitat.model.Cell;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CellService extends BaseModelService<Cell> {

  CompletableFuture<Cell> getCellByCoordinates(Integer x, Integer y);

  CompletableFuture<List<Cell>> getArea(Integer x0, Integer y0, Integer xN, Integer yN);

}
