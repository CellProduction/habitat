package io.cell.service.habitat.dto.client;

import io.cell.service.habitat.model.Address;

import java.util.Optional;
import java.util.UUID;

/**
 * DTO объект объеденяющий общие свойства клетки и ее переменные особенности на текущий момент
 */
public class CurrentCellState implements Comparable<CurrentCellState>{
  private UUID cellId;
  private Address address;
  private Integer movementRate;
  private Boolean movable;
  private Integer flightRate;
  private Boolean flyable;
  private String presentationImage;
  private String backgroundImage;

  public UUID getCellId() {
    return cellId;
  }

  public CurrentCellState setCellId(UUID cellId) {
    this.cellId = cellId;
    return this;
  }

  public Address getAddress() {
    return address;
  }

  public CurrentCellState setAddress(Address address) {
    this.address = address;
    return this;
  }

  public Integer getMovementRate() {
    return movementRate;
  }

  public CurrentCellState setMovementRate(Integer movementRate) {
    this.movementRate = movementRate;
    return this;
  }

  public Boolean isMovable() {
    return movable;
  }

  public CurrentCellState setMovable(Boolean movable) {
    this.movable = movable;
    return this;
  }

  public Integer getFlightRate() {
    return flightRate;
  }

  public CurrentCellState setFlightRate(Integer flightRate) {
    this.flightRate = flightRate;
    return this;
  }

  public Boolean isFlyable() {
    return flyable;
  }

  public CurrentCellState setFlyable(Boolean flyable) {
    this.flyable = flyable;
    return this;
  }

  public String getPresentationImage() {
    return presentationImage;
  }

  public CurrentCellState setPresentationImage(String presentationImage) {
    this.presentationImage = presentationImage;
    return this;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public CurrentCellState setBackgroundImage(String backgroundImage) {
    this.backgroundImage = backgroundImage;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CurrentCellState that = (CurrentCellState) o;

    if (!cellId.equals(that.cellId)) return false;
    if (!address.equals(that.address)) return false;
    if (movementRate != null ? !movementRate.equals(that.movementRate) : that.movementRate != null) return false;
    if (movable != null ? !movable.equals(that.movable) : that.movable != null) return false;
    if (flightRate != null ? !flightRate.equals(that.flightRate) : that.flightRate != null) return false;
    if (flyable != null ? !flyable.equals(that.flyable) : that.flyable != null) return false;
    if (presentationImage != null ? !presentationImage.equals(that.presentationImage) : that.presentationImage != null)
      return false;
    return backgroundImage != null ? backgroundImage.equals(that.backgroundImage) : that.backgroundImage == null;

  }

  @Override
  public int hashCode() {
    int result = cellId.hashCode();
    result = 31 * result + address.hashCode();
    result = 31 * result + (movementRate != null ? movementRate.hashCode() : 0);
    result = 31 * result + (movable != null ? movable.hashCode() : 0);
    result = 31 * result + (flightRate != null ? flightRate.hashCode() : 0);
    result = 31 * result + (flyable != null ? flyable.hashCode() : 0);
    result = 31 * result + (presentationImage != null ? presentationImage.hashCode() : 0);
    result = 31 * result + (backgroundImage != null ? backgroundImage.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CurrentCellState{" +
        "cellId=" + cellId +
        ", address=" + address +
        ", movementRate=" + movementRate +
        ", movable=" + movable +
        ", flightRate=" + flightRate +
        ", flyable=" + flyable +
        ", presentationImage='" + presentationImage + '\'' +
        ", backgroundImage='" + backgroundImage + '\'' +
        '}';
  }

  @Override
  public int compareTo(CurrentCellState cellState) {
    if (cellState.getAddress() == null) {
      return 1;
    }
    int compareToX = Optional.ofNullable(cellState.getAddress().getX()).orElse(0);
    int compareToY = Optional.ofNullable(cellState.getAddress().getY()).orElse(0);
    int result = this.getAddress().getX() - compareToX;
    if (result == 0) {
      result = this.getAddress().getY() - compareToY;
    }
    return result;
  }

}
