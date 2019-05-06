package io.cell.service.habitat.init.map.v1;

import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.Cell;
import io.cell.service.habitat.model.Region;
import io.cell.service.habitat.repositories.RegionRepository;
import io.cell.service.habitat.services.CellService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class InitDatabaseService_V1 {

  //Регионы
  private static final int REGION_1 = 1;
  private static final int REGION_2 = 2;
  private static final int REGION_3 = 3;
  private static final int REGION_4 = 4;
  private static final int REGION_5 = 5;
  private static final int REGION_6 = 6;
  private static final int REGION_7 = 7;
  private static final int REGION_8 = 8;
  private static final int REGION_9 = 9;
  private static final int REGION_10 = 10;
  private static final int REGION_11 = 11;
  private static final int REGION_12 = 12;
  private static final int DEFAULT_REGION = 666;

  private static final int INITIAL_MAX_X = 40;
  private static final int INITIAL_MAX_Y = 30;

  private static final int DEFAULT_MOVE_RATE = 100;

  private Map<Pair, Integer> regionCanvas = new HashMap<>();

  private CellService cellService;
  private RegionRepository regionRepository;

  @Autowired
  public InitDatabaseService_V1(CellService cellService, RegionRepository regionRepository) {
    this.cellService = cellService;
    this.regionRepository = regionRepository;
  }

  @PostConstruct
  public void init() {
    fillRegions(); // создать регионы
    createCells(); // создать клетки
  }

  /**
   * Создать первую область размерностью INITIAL_MAX_X * INITIAL_MAX_Y
   */
  private void createCells() {
    for (int i = 1; i <= INITIAL_MAX_X; i++) {
      for (int j = 1; j <= INITIAL_MAX_Y; j++) {
        Address address = new Address()
            .setId(UUID.randomUUID())
            .setRegionIndex(getRegion(i, j))
            .setX(i)
            .setY(j);
        Cell cell = new Cell()
            .setId(UUID.randomUUID())
            .setAddress(address)
            .setMovementRate(DEFAULT_MOVE_RATE);
        cellService.create(cell);
      }
    }
  }

  private Integer getRegion(Integer x, Integer y) {
    return Optional.ofNullable(regionCanvas.get(new Pair(x, y)))
        .orElse(DEFAULT_REGION);
  }

  /**
   * Создать регионы
   */
  private void fillRegions() {
    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_1));
    IntStream.rangeClosed(1, 10).forEach(x ->
        IntStream.rangeClosed(1, 10).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_1)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_2));
    IntStream.rangeClosed(11, 20).forEach(x ->
        IntStream.rangeClosed(1, 10).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_2)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_3));
    IntStream.rangeClosed(21, 30).forEach(x ->
        IntStream.rangeClosed(1, 10).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_3)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_4));
    IntStream.rangeClosed(31, 40).forEach(x ->
        IntStream.rangeClosed(1, 10).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_4)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_5));
    IntStream.rangeClosed(1, 10).forEach(x ->
        IntStream.rangeClosed(11, 20).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_5)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_6));
    IntStream.rangeClosed(11, 20).forEach(x ->
        IntStream.rangeClosed(11, 20).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_6)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_7));
    IntStream.rangeClosed(21, 30).forEach(x ->
        IntStream.rangeClosed(11, 20).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_7)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_8));
    IntStream.rangeClosed(31, 40).forEach(x ->
        IntStream.rangeClosed(11, 20).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_8)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_9));
    IntStream.rangeClosed(1, 10).forEach(x ->
        IntStream.rangeClosed(21, 30).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_9)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_10));
    IntStream.rangeClosed(11, 20).forEach(x ->
        IntStream.rangeClosed(21, 30).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_10)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_11));
    IntStream.rangeClosed(21, 30).forEach(x ->
        IntStream.rangeClosed(21, 30).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_11)));

    regionRepository.save(new Region()
        .setId(UUID.randomUUID())
        .setIndex(REGION_12));
    IntStream.rangeClosed(31, 40).forEach(x ->
        IntStream.rangeClosed(21, 30).forEach(y ->
            this.regionCanvas.put(new Pair(x, y), REGION_12)));
  }
}