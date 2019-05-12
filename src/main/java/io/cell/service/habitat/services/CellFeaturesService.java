package io.cell.service.habitat.services;

import io.cell.service.habitat.model.CellFeatures;

import java.util.concurrent.CompletableFuture;

public interface CellFeaturesService extends BaseModelService<CellFeatures> {

  CompletableFuture<CellFeatures> getCellFeaturesByCoordinates(Integer X, Integer Y);

}
