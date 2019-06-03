package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CellState;
import io.cell.service.habitat.model.Address;
import io.cell.service.habitat.model.Cell;
import io.cell.service.habitat.model.CellFeatures;
import io.cell.service.habitat.utils.CellStateBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CellStateServiceImplTest {

  private static final UUID TEST_ADDRESS_ID = UUID.randomUUID();
  private static final UUID TEST_CELL_ID = UUID.randomUUID();
  private static final String FAKE_IMAGE_FILE_NAME = "image.jpg";

  @MockBean
  private CellService cellService;
  @MockBean
  private CellFeaturesService featuresService;
  @Mock
  private GridFsTemplate gridFsTemplate;

  private CellStateServiceImpl stateService;

  @Before
  public void init() {
    Cell testCell = getTestCell();
    CellFeatures features = getTestCellFeatures();
    when(cellService.getCellByCoordinates(1, 1)).thenReturn(CompletableFuture.completedFuture(testCell));
    when(featuresService.getCellFeaturesByCoordinates(1, 1)).thenReturn(CompletableFuture.completedFuture(features));

    stateService = new CellStateServiceImpl(cellService, featuresService, new CellStateBuilder(), gridFsTemplate);
  }

  @Test
  public void getCellStateByCoordinates() {
    Cell testCell = getTestCell();
    CellFeatures testFeatures = getTestCellFeatures();
    CellState cellState = stateService.getCellStateByCoordinates(1, 1).join();

    assertNotNull(cellState);
    assertEquals(cellState.getCellId(), testCell.getId());
    assertEquals(cellState.getAddress(), testCell.getAddress());
    assertEquals(cellState.getMovementRate(), testFeatures.getMovementRate());
    assertEquals(cellState.isMovable(), testFeatures.isMovable());
    assertEquals(cellState.getFlightRate(), testFeatures.getFlightRate());
    assertEquals(cellState.isFlyable(), testFeatures.isFlyable());
    assertEquals(cellState.getBackgroundImage(), testFeatures.getBackgroundImage());
    assertEquals(cellState.getPresentationImage(), testFeatures.getPresentationImage());
  }

  private Address getTestAddress() {
    return new Address()
        .setId(TEST_ADDRESS_ID)
        .setRegionIndex(1)
        .setX(1)
        .setY(1);
  }

  private Cell getTestCell() {
    return new Cell()
        .setId(TEST_CELL_ID)
        .setAddress(getTestAddress());
  }

  private CellFeatures getTestCellFeatures() {
    return new CellFeatures()
        .setId(UUID.randomUUID())
        .setAddress(getTestAddress())
        .setMovementRate(1f)
        .setMovable(true)
        .setFlightRate(1f)
        .setFlyable(true)
        .setBackgroundImage(FAKE_IMAGE_FILE_NAME)
        .setPresentationImage(FAKE_IMAGE_FILE_NAME);
  }

}
