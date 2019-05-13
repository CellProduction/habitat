package io.cell.service.habitat.services;

import io.cell.service.habitat.dto.client.CurrentCellState;
import io.cell.service.habitat.model.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class CellStatesShouldBeSortedByCoordinatesTest {

  @Test
  public void statesShouldBeSorted() {
    CurrentCellState cellState1 = new CurrentCellState().setCellId(UUID.randomUUID()).setAddress(getTestAddress(1, 1));
    CurrentCellState cellState2 = new CurrentCellState().setCellId(UUID.randomUUID()).setAddress(getTestAddress(2, 1));
    CurrentCellState cellState3 = new CurrentCellState().setCellId(UUID.randomUUID()).setAddress(getTestAddress(1, 3));

    Set<CurrentCellState> cellStates = new TreeSet<>();
    cellStates.add(cellState1);
    cellStates.add(cellState2);
    cellStates.add(cellState3);

    CurrentCellState[] cellStatesArray = cellStates.toArray(new CurrentCellState[3]);
    assertEquals(cellStatesArray[0], cellState1);
    assertEquals(cellStatesArray[1], cellState3);
    assertEquals(cellStatesArray[2], cellState2);
  }

  private Address getTestAddress(Integer x,Integer y) {
    return new Address()
        .setId(UUID.randomUUID())
        .setRegionIndex(1)
        .setX(x)
        .setY(y);
  }

}
