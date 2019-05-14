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
  private CellState cellState;

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
    fillCellFields();
    fillFeaturesFields();
    cell = null;
    features = null;
    return cellState;
  }

  private CellStateBuilder fillCellFields() {
    if (!Optional.ofNullable(cell).isPresent()) {
      return this;
    }
    cellState = Optional.ofNullable(cellState).orElse(new CellState())
        .setCellId(cell.getId())
        .setAddress(cell.getAddress());
    return this;
  }

  private CellStateBuilder fillFeaturesFields() {
    if (!Optional.ofNullable(features).isPresent()) {
      return this;
    }
    cellState = Optional.ofNullable(cellState).orElse(new CellState())
        .setMovementRate(features.getMovementRate())
        .setMovable(features.isMovable())
        .setFlightRate(features.getFlightRate())
        .setFlyable(features.isFlyable())
        .setPresentationImage(features.getPresentationImage())
        .setBackgroundImage(features.getBackgroundImage());
    return this;
  }
}
