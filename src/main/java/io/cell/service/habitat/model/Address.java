package io.cell.service.habitat.model;

import java.util.Objects;
import java.util.UUID;

public class Address {
  private UUID id;
  private Integer regionIndex;
  private Integer x;
  private Integer Y;

  public UUID getId() {
    return id;
  }

  public Address setId(UUID id) {
    this.id = id;
    return this;
  }

  public Integer getRegionIndex() {
    return regionIndex;
  }

  public Address setRegionIndex(Integer regionIndex) {
    this.regionIndex = regionIndex;
    return this;
  }

  public Integer getX() {
    return x;
  }

  public Address setX(Integer x) {
    this.x = x;
    return this;
  }

  public Integer getY() {
    return Y;
  }

  public Address setY(Integer y) {
    Y = y;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return id.equals(address.id) &&
        Objects.equals(regionIndex, address.regionIndex) &&
        Objects.equals(x, address.x) &&
        Objects.equals(Y, address.Y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, regionIndex, x, Y);
  }

  @Override
  public String toString() {
    return "Address{" +
        regionIndex +
        "." + x +
        "." + Y +
        "}";
  }
}
