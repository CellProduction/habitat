package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CellState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

  private GridFsTemplate gridFsTemplate;

  @Autowired
  public FileServiceImpl(GridFsTemplate gridFsTemplate) {
    this.gridFsTemplate = gridFsTemplate;
  }

  @Override
  public CompletableFuture<Resource> getResource(String filename) {
    return CompletableFuture.completedFuture(gridFsTemplate.getResource(filename));
  }

  @Override
  public CompletableFuture<Set<Resource>> getAreaBackgroundImages(Set<CellState> cellStates) {
    return CompletableFuture.supplyAsync(() ->
        cellStates.stream()
            .map(cellState -> gridFsTemplate.getResource(cellState.getBackgroundImage()))
            .collect(Collectors.toSet()));
  }
}
