package io.cell.service.habitat.repositories;

import io.cell.service.habitat.model.Cell;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring-data-mongo CRUD-repository реализация для {@link Cell}
 */
@Repository
public interface CellRepository extends MongoRepository<Cell, UUID> {

  /**
   * 'Between' использует параметры как границы исключая сами значения.
   * Поэтому необходимо это учитывать и расширять границы, при необходимости включать граничные значения.
   * @param x0
   * @param y0
   * @param xN
   * @param yN
   * @return
   */
  List<Cell> findAllByAddress_XBetweenAndAddress_YBetween(Integer x0, Integer y0, Integer xN, Integer yN);
}
