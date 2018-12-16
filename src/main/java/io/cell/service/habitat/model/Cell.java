package io.cell.service.habitat.model;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class Cell {
  private UUID id;
  private Address address;
  private Integer movementRate;
  private Collection<Location> locations;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Integer getMovementRate() {
    return movementRate;
  }

  public void setMovementRate(Integer movementRate) {
    this.movementRate = movementRate;
  }

  public Collection<Location> getLocations() {
    return locations;
  }

  public void setLocations(Collection<Location> locations) {
    this.locations = locations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return id.equals(cell.id) &&
        Objects.equals(address, cell.address) &&
        Objects.equals(movementRate, cell.movementRate) &&
        Objects.equals(locations, cell.locations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, movementRate, locations);
  }
}
