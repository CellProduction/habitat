package io.cell.service.habitat.services;

import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.Cell;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface HabitateService {
  CompletableFuture<Cell[][]> getCanvas(UUID cellId, Integer xExtension, Integer yExtension);
  CompletableFuture<Cell[][]> getCanvas(Address address, Integer xExtension, Integer yExtension);
}
