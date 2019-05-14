package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CellState;
import org.springframework.core.io.Resource;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface FileService {

  CompletableFuture<Resource> getResource(String filename);

  CompletableFuture<Set<Resource>> getAreaBackgroundImages(Set<CellState> cellStates);

  }
