package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.EngineType;

/**
 * Created 10/17/2019 3:42 PM
 *
 * @author Rohit Rawani
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

  public CarDTO() {
  }

  private Long id;
  private String licensePlate;
  private String manufacturer;
  private EngineType engineType;
  private int seatCount;
  private double rating;

  @JsonProperty public Long getId() {
    return id;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public EngineType getEngineType() {
    return engineType;
  }

  public int getSeatCount() {
    return seatCount;
  }

  public double getRating() {
    return rating;
  }

  public CarDTO(CarDTOBuilder builder) {
    this.id = builder.id;
    this.licensePlate = builder.licensePlate;
    this.manufacturer = builder.manufacturer;
    this.engineType = builder.engineType;
    this.seatCount = builder.seatCount;
    this.rating = builder.rating;
  }

  public static class CarDTOBuilder {

    private Long id;
    private String licensePlate;
    private String manufacturer;
    private EngineType engineType;
    private int seatCount;
    private double rating;

    public CarDTOBuilder() {
    }

    public CarDTOBuilder(Long id, String licensePlate, String manufacturer) {
      this.id = id;
      this.licensePlate = licensePlate;
      this.manufacturer = manufacturer;
    }

    public CarDTOBuilder setEngineType(EngineType engineType) {
      this.engineType = engineType;
      return this;
    }

    public CarDTOBuilder setSeatCount(int seatCount) {
      this.seatCount = seatCount;
      return this;
    }

    public CarDTOBuilder setRating(double rating) {
      this.rating = rating;
      return this;
    }

    public CarDTO build() {
      return new CarDTO(this);
    }
  }
}
