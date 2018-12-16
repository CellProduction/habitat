package io.cell.service.habitat.model;

import java.util.Objects;
import java.util.UUID;

public class Location {
  private UUID id;
  private String name;
  private String description;
  private Region region;

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

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return id.equals(location.id) &&
        Objects.equals(name, location.name) &&
        Objects.equals(description, location.description) &&
        Objects.equals(region, location.region);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, region);
  }
}
