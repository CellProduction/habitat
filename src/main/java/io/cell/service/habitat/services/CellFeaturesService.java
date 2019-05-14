package io.cell.service.habitat.services;

import io.cell.service.habitat.model.CellFeatures;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CellFeaturesService extends BaseModelService<CellFeatures> {

  CompletableFuture<CellFeatures> getCellFeaturesByCoordinates(Integer x, Integer y);

  CompletableFuture<List<CellFeatures>> getArea(Integer x0, Integer y0, Integer xN, Integer yN);

}
