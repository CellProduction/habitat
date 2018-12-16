package io.cell.service.habitat.services.mongo;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BaseModelService<T>{

  CompletableFuture<Boolean> create(T obj);

  CompletableFuture<Boolean> update(T obj);

  CompletableFuture<Boolean> delete(UUID id);

  CompletableFuture<Boolean> delete(T obj);

  CompletableFuture<T> read(UUID id);
}
