package io.cell.service.habitat.services;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cell.service.habitat.dto.client.CellState;
import io.cell.service.habitat.model.Address;
import org.junit.jupiter.api.Test;

public class CellStatesShouldBeSortedByCoordinatesTest {

    @Test
    public void statesShouldBeSorted() {
        CellState cellState1 = new CellState().setCellId(UUID.randomUUID()).setAddress(getTestAddress(1, 1));
        CellState cellState2 = new CellState().setCellId(UUID.randomUUID()).setAddress(getTestAddress(2, 1));
        CellState cellState3 = new CellState().setCellId(UUID.randomUUID()).setAddress(getTestAddress(1, 3));

        Set<CellState> cellStates = new TreeSet<>();
        cellStates.add(cellState1);
        cellStates.add(cellState2);
        cellStates.add(cellState3);

        CellState[] cellStatesArray = cellStates.toArray(new CellState[3]);
        assertEquals(cellStatesArray[0], cellState1);
        assertEquals(cellStatesArray[1], cellState3);
        assertEquals(cellStatesArray[2], cellState2);
    }

    private Address getTestAddress(Integer x, Integer y) {
        return new Address()
                .setId(UUID.randomUUID())
                .setRegionIndex(1)
                .setX(x)
                .setY(y);
    }

}
