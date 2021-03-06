package io.cell.service.habitat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Document
public class Cell {
  @Id
  private UUID id;
  private Address address;

  public UUID getId() {
    return id;
  }

  public Cell setId(UUID id) {
    this.id = id;
    return this;
  }

  public Address getAddress() {
    return address;
  }

  public Cell setAddress(Address address) {
    this.address = address;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return id.equals(cell.id) &&
        Objects.equals(address, cell.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address);
  }

  @Override
  public String toString() {
    return "Cell{" +
        "address=" + address +
        '}';
  }
}
