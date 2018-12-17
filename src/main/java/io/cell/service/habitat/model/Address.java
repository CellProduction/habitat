package io.cell.service.habitat.model;

import java.util.Objects;
import java.util.UUID;

public class Address {
  private UUID id;
  private Region region;
  private Integer x;
  private Integer Y;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public Integer getY() {
    return Y;
  }

  public void setY(Integer y) {
    Y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return id.equals(address.id) &&
        Objects.equals(region, address.region) &&
        Objects.equals(x, address.x) &&
        Objects.equals(Y, address.Y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, region, x, Y);
  }

  @Override
  public String toString() {
    return "Address{" +
        region.getIndex() +
        "." + x +
        "." + Y +
        "}";
  }
}
