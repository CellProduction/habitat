package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CellState;
import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.Cell;
import io.cell.service.habitat.model.CellFeatures;
import io.cell.service.habitat.utils.CellStateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CellStateServiceImpl implements CellStateService {

  private CellService cellService;
  private CellFeaturesService featuresService;
  private CellStateBuilder stateBuilder;
  private GridFsTemplate gridFsTemplate;

  @Autowired
  public CellStateServiceImpl(CellService cellService, CellFeaturesService featuresService, CellStateBuilder stateBuilder, GridFsTemplate gridFsTemplate) {
    this.cellService = cellService;
    this.featuresService = featuresService;
    this.stateBuilder = stateBuilder;
    this.gridFsTemplate = gridFsTemplate;
  }

  @Override
  public CompletableFuture<CellState> getCellStateByAddress(Address address) {
    return getCellStateByCoordinates(address.getX(), address.getY());
  }

  @Override
  public CompletableFuture<CellState> getCellStateByCoordinates(Integer x, Integer y) {
    CompletableFuture<Cell> cellFuture = cellService.getCellByCoordinates(x, y);
    CompletableFuture<CellFeatures> featuresFuture = featuresService.getCellFeaturesByCoordinates(x, y);
    return cellFuture.thenCombine(featuresFuture, (cell, cellFeatures) ->
        stateBuilder.setCell(cell).setFeatures(cellFeatures).build());
  }

  @Override
  public CompletableFuture<Set<CellState>> getArea(Integer x, Integer y, Integer areaSize) {
    Integer rangeSize = areaSize / 2;
    return cellService.getArea(x - rangeSize, y - rangeSize, x + rangeSize, y + rangeSize)
        .thenCombine(
            featuresService.getArea(x - rangeSize, y - rangeSize, x + rangeSize, y + rangeSize),
            (cells, cellFeatures) -> {
              Map<Address, Cell> cellMap = cells.stream()
                  .collect(Collectors.toMap(Cell::getAddress, cell -> cell));
              Map<Address, CellFeatures> featuresMap = cellFeatures.stream()
                  .collect(Collectors.toMap(CellFeatures::getAddress, features -> features));
              return cellMap.keySet().stream().map(address -> {
                Cell cell = cellMap.get(address);
                CellFeatures ownedFeatures = featuresMap.get(address);
                return stateBuilder.setCell(cell).setFeatures(ownedFeatures).build();
              }).collect(Collectors.toSet());
            });
  }
}
