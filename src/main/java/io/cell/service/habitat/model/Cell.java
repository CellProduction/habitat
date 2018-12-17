package io.cell.service.habitat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Document
public class Cell {
  @Id
  private UUID id;
  private Address address;
  private Integer movementRate;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return id.equals(cell.id) &&
        Objects.equals(address, cell.address) &&
        Objects.equals(movementRate, cell.movementRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, movementRate);
  }
}
