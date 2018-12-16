package io.cell.service.habitat.model;

import java.util.Objects;
import java.util.UUID;

public class Region {
  private UUID id;
  private String name;
  private String description;
  private Integer movementRate;
  private UUID defaultCellId;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getMovementRate() {
    return movementRate;
  }

  public void setMovementRate(Integer movementRate) {
    this.movementRate = movementRate;
  }

  public UUID getDefaultCellId() {
    return defaultCellId;
  }

  public void setDefaultCellId(UUID defaultCellId) {
    this.defaultCellId = defaultCellId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Region region = (Region) o;
    return id.equals(region.id) &&
        Objects.equals(name, region.name) &&
        Objects.equals(description, region.description) &&
        Objects.equals(movementRate, region.movementRate) &&
        defaultCellId.equals(region.defaultCellId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, movementRate, defaultCellId);
  }
}
