package io.cell.service.habitat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Дополнительные харрактеристики клетки, действующие в разрезе определенного промежутка времени
 */
@Document
public class CellFeatures {
  @Id
  private UUID id;
  private Address address;
  private ZonedDateTime startDateTime;
  private ZonedDateTime endDateTime;
  private int movementRate;
  private boolean movable;
  private int flightRate;
  private boolean flyable;
  private String presentationImage;
  private String backgroundImage;

  public UUID getId() {
    return id;
  }

  public Address getAddress() {
    return address;
  }

  public CellFeatures setAddress(Address address) {
    this.address = address;
    return this;
  }

  public CellFeatures setId(UUID id) {
    this.id = id;
    return this;
  }

  public ZonedDateTime getStartDateTime() {
    return startDateTime;
  }

  public CellFeatures setStartDateTime(ZonedDateTime startDateTime) {
    this.startDateTime = startDateTime;
    return this;
  }

  public ZonedDateTime getEndDateTime() {
    return endDateTime;
  }

  public CellFeatures setEndDateTime(ZonedDateTime endDateTime) {
    this.endDateTime = endDateTime;
    return this;
  }

  public int getMovementRate() {
    return movementRate;
  }

  public CellFeatures setMovementRate(int movementRate) {
    this.movementRate = movementRate;
    return this;
  }

  public boolean isMovable() {
    return movable;
  }

  public CellFeatures setMovable(boolean movable) {
    this.movable = movable;
    return this;
  }

  public int getFlightRate() {
    return flightRate;
  }

  public CellFeatures setFlightRate(int flightRate) {
    this.flightRate = flightRate;
    return this;
  }

  public boolean isFlyable() {
    return flyable;
  }

  public CellFeatures setFlyable(boolean flyable) {
    this.flyable = flyable;
    return this;
  }

  public String getPresentationImage() {
    return presentationImage;
  }

  public CellFeatures setPresentationImage(String presentationImage) {
    this.presentationImage = presentationImage;
    return this;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public CellFeatures setBackgroundImage(String backgroundImage) {
    this.backgroundImage = backgroundImage;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CellFeatures that = (CellFeatures) o;

    if (movementRate != that.movementRate) return false;
    if (movable != that.movable) return false;
    if (flightRate != that.flightRate) return false;
    if (flyable != that.flyable) return false;
    if (!id.equals(that.id)) return false;
    if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null) return false;
    return endDateTime != null ? endDateTime.equals(that.endDateTime) : that.endDateTime == null;

  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
    result = 31 * result + (endDateTime != null ? endDateTime.hashCode() : 0);
    result = 31 * result + movementRate;
    result = 31 * result + (movable ? 1 : 0);
    result = 31 * result + flightRate;
    result = 31 * result + (flyable ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CellFeatures{" +
        "id=" + id +
        ", startDateTime=" + startDateTime +
        ", endDateTime=" + endDateTime +
        ", movementRate=" + movementRate +
        ", movable=" + movable +
        ", flightRate=" + flightRate +
        ", flyable=" + flyable +
        '}';
  }
}