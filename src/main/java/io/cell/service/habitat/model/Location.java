package io.cell.service.habitat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Document
public class Location {
  @Id
  private UUID id;
  @TextIndexed
  private String name;
  private String description;
  private UUID cellId;

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

  public UUID getCellId() {
    return cellId;
  }

  public void setCellId(UUID cellId) {
    this.cellId = cellId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return id.equals(location.id) &&
        Objects.equals(name, location.name) &&
        Objects.equals(description, location.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description);
  }
}
