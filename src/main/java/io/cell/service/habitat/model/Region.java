package io.cell.service.habitat.model;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Region {
    @Id
    private UUID id;
    @Indexed
    private Integer index; // порядковый номер региона
    private String name;
    private String description;
    private Integer movementRate;
    private UUID defaultCellId;

    public UUID getId() {
        return id;
    }

    public Region setId(UUID id) {
        this.id = id;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public Region setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public String getName() {
        return name;
    }

    public Region setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Region setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getMovementRate() {
        return movementRate;
    }

    public Region setMovementRate(Integer movementRate) {
        this.movementRate = movementRate;
        return this;
    }

    public UUID getDefaultCellId() {
        return defaultCellId;
    }

    public Region setDefaultCellId(UUID defaultCellId) {
        this.defaultCellId = defaultCellId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
        Region region = (Region) o;
        return id.equals(region.id) &&
                index.equals(region.index) &&
                Objects.equals(name, region.name) &&
                Objects.equals(description, region.description) &&
                Objects.equals(movementRate, region.movementRate) &&
                defaultCellId.equals(region.defaultCellId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index, name, description, movementRate, defaultCellId);
    }
}
