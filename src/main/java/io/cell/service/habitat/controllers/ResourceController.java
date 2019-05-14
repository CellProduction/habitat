package io.cell.service.habitat.controllers;

import io.cell.service.habitat.services.CellStateService;
import io.cell.service.habitat.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;


@Controller
public class ResourceController {

  private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

  private FileService fileService;
  private CellStateService cellStateService;

  @Autowired
  public ResourceController(FileService fileService, CellStateService cellStateService) {
    this.fileService = fileService;
    this.cellStateService = cellStateService;
  }

  @GetMapping(path = "/image/{filename}", produces = IMAGE_JPEG_VALUE)
  public CompletableFuture<ResponseEntity<Resource>> getImage(@PathVariable("filename") @NotBlank String filename) {
    return fileService.getResource(filename)
        .thenApply(ResponseEntity::ok)
        .exceptionally(throwable -> {
          LOG.error(throwable.getMessage(), throwable);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        });
  }
}
