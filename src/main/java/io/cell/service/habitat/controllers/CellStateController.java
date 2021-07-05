package io.cell.service.habitat.controllers;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.validation.constraints.NotBlank;

import io.cell.service.habitat.dto.client.CellState;
import io.cell.service.habitat.services.CellStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CellStateController {

    private static final Logger LOG = LoggerFactory.getLogger(CellStateController.class);
    private CellStateService cellStateService;

    @Autowired
    public CellStateController(CellStateService cellStateService) {
        this.cellStateService = cellStateService;
    }

    @GetMapping(path = "/area")
    CompletableFuture<ResponseEntity<Set<CellState>>> getArea(
            @RequestParam("x") @NotBlank Integer x,
            @RequestParam("y") @NotBlank Integer y,
            @RequestParam("areaSize") @NotBlank Integer areaSize) {
        return cellStateService.getArea(x, y, areaSize)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    LOG.error(throwable.getMessage(), throwable);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }
}
