package io.cell.service.habitat.init.map.v1;

import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.Cell;
import io.cell.service.habitat.model.CellFeatures;
import io.cell.service.habitat.model.Region;
import io.cell.service.habitat.repositories.RegionRepository;
import io.cell.service.habitat.services.CellFeaturesService;
import io.cell.service.habitat.services.CellService;
import io.cell.service.habitat.services.CellServiceImpl;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Service
@PropertySource(value = "classpath:application.yaml", ignoreResourceNotFound = true)
public class InitDatabaseService_V1 {
  private static final Logger LOG = LoggerFactory.getLogger(CellServiceImpl.class);

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

  private static final int DEFAULT_MOVEMENT_RATE = 100;
  private static final String FILE_FORMAT = "jpg";

  @Value("${init.db.enable}")
  private boolean initEnable;
  @Value("${init.db.images.filepath}")
  private String filepath;
  private boolean loadBackGroundImages;

  private Map<Pair, Integer> regionCanvas = new HashMap<>();

  private GridFsTemplate gridFsTemplate;
  private CellService cellService;
  private CellFeaturesService featuresService;
  private RegionRepository regionRepository;

  @Autowired
  public InitDatabaseService_V1(GridFsTemplate gridFsTemplate, CellService cellService, CellFeaturesService featuresService, RegionRepository regionRepository) {
    this.gridFsTemplate = gridFsTemplate;
    this.cellService = cellService;
    this.featuresService = featuresService;
    this.regionRepository = regionRepository;
    this.loadBackGroundImages = Optional.ofNullable(filepath).isPresent();
  }

  @PostConstruct
  public void init() {
    if (!initEnable) {
      return;
    }
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
        createCell(address);
        storeCellBackgroundImage(i, j);
        createCellFeature(address);
      }
    }
  }

  private void createCell(Address address) {
    Cell cell = new Cell()
        .setId(UUID.randomUUID())
        .setAddress(address);
    cellService.create(cell);
  }

  private void storeCellBackgroundImage(Integer x, Integer y) {
    if (!loadBackGroundImages) {
      return;
    }
    String filename = filename(x, y);
    try (InputStream imageInputStream = Files.newInputStream(new File(fullFilename(x, y)).toPath(), StandardOpenOption.READ)) {
      BasicQuery existsQuery = new BasicQuery("{ filename : \'" + filename + "\' }");
      boolean fileExists = gridFsTemplate.findOne(existsQuery) != null;
      if (fileExists) {
        LOG.warn("File {} already exists.", filename);
        return;
      }
      gridFsTemplate.store(imageInputStream, filename, IMAGE_JPEG_VALUE);
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  private void createCellFeature(Address address) {
    CellFeatures features = new CellFeatures()
        .setId(UUID.randomUUID())
        .setAddress(address)
        .setMovable(true)
        .setFlyable(true)
        .setMovementRate(DEFAULT_MOVEMENT_RATE)
        .setFlightRate(DEFAULT_MOVEMENT_RATE)
        .setBackgroundImage(filename(address.getX(), address.getY()));
    featuresService.create(features);
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

  private String filename(Integer x, Integer y) {
    return "v1_" + x + "_" + y
        + "." + FILE_FORMAT;
  }

  private String fullFilename(Integer x, Integer y) {
    return filepath
        + File.separatorChar
        + filename(x, y);
  }
}
