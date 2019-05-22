package io.cell.service.habitat.utils;

import io.cell.service.habitat.dto.client.CellState;
import io.cell.service.habitat.model.Cell;
import io.cell.service.habitat.model.CellFeatures;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CellStateBuilder {

  private Cell cell;
  private CellFeatures features;

  public Cell getCell() {
    return cell;
  }

  public CellStateBuilder setCell(Cell cell) {
    this.cell = cell;
    return this;
  }

  public CellFeatures getFeatures() {
    return features;
  }

  public CellStateBuilder setFeatures(CellFeatures features) {
    this.features = features;
    return this;
  }

  public CellState build() {
    CellState cellState = new CellState();
    fillCellFields(cellState);
    fillFeaturesFields(cellState);
    cell = null;
    features = null;
    return cellState;
  }

  private CellStateBuilder fillCellFields(CellState cellState) {
    if (!Optional.ofNullable(cell).isPresent()) {
      return this;
    }
    cellState.setCellId(cell.getId())
        .setAddress(cell.getAddress());
    return this;
  }

  private CellStateBuilder fillFeaturesFields(CellState cellState) {
    if (!Optional.ofNullable(features).isPresent()) {
      return this;
    }
    cellState.setMovementRate(features.getMovementRate())
        .setMovable(features.isMovable())
        .setFlightRate(features.getFlightRate())
        .setFlyable(features.isFlyable())
        .setPresentationImage(features.getPresentationImage())
        .setBackgroundImage(features.getBackgroundImage());
    return this;
  }
}
