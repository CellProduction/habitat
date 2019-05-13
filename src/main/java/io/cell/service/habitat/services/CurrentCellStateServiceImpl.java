package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CurrentCellState;
import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.Cell;
import io.cell.service.habitat.model.CellFeatures;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CurrentCellStateServiceImpl implements CurrentCellStateService {

  private CellService cellService;
  private CellFeaturesService featuresService;

  @Autowired
  public CurrentCellStateServiceImpl(CellService cellService, CellFeaturesService featuresService) {
    this.cellService = cellService;
    this.featuresService = featuresService;
  }

  @Override
  public CompletableFuture<CurrentCellState> getCellStateByAddress(Address address) {
    return getCellStateByCoordinates(address.getX(), address.getY());
  }

  @Override
  public CompletableFuture<CurrentCellState> getCellStateByCoordinates(Integer x, Integer y) {
    CompletableFuture<Cell> cellFuture = cellService.getCellByCoordinates(x, y);
    CompletableFuture<CellFeatures> featuresFuture = featuresService.getCellFeaturesByCoordinates(x, y);
    return cellFuture.thenCombine(featuresFuture, (cell, cellFeatures) ->
        fillFeaturesFields(fillCellFields(null, cell), cellFeatures));
  }

  private CurrentCellState fillCellFields(CurrentCellState cellState, Cell cell) {
    return Optional.ofNullable(cellState).orElse(new CurrentCellState())
        .setCellId(cell.getId())
        .setAddress(cell.getAddress());
  }

  private CurrentCellState fillFeaturesFields(CurrentCellState cellState, CellFeatures features) {
    return Optional.ofNullable(cellState).orElse(new CurrentCellState())
        .setMovementRate(features.getMovementRate())
        .setMovable(features.isMovable())
        .setFlightRate(features.getFlightRate())
        .setFlyable(features.isFlyable())
        .setPresentationImage(features.getPresentationImage())
        .setBackgroundImage(features.getBackgroundImage());
  }
}
