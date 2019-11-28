package com.freenow.domainobject;


import com.freenow.domainvalue.EngineType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Created 10/17/2019 12:58 PM
 *
 * @author Rohit Rawani
 */
@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "car_licensePlate", columnNames = {"licensePlate"})
)
public class CarDO {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotNull(message = "licensePlate can not be null!")
  private String licensePlate;

  @Column(nullable = true)
  private double rating;

  @Column(nullable = false)
  @NotNull(message = "seatCount can not be null!")
  private int seatCount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EngineType engineType;

  @NotNull(message = "manufacturer can not be null!")
  @Column(nullable = false)
  private String manufacturer;

  @Column(nullable = false)
  private Boolean deleted = false;

  @OneToOne(mappedBy="car")
  private DriverDO driver;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public int getSeatCount() {
    return seatCount;
  }

  public void setSeatCount(int seatCount) {
    this.seatCount = seatCount;
  }

  public EngineType getEngineType() {
    return engineType;
  }

  public void setEngineType(EngineType engineType) {
    this.engineType = engineType;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public CarDO() {
  }

  public CarDO(@NotNull(message = "licensePlate can not be null!") String licensePlate,
      EngineType engineType, String manufacturer, int seatCount, double rating) {
    this.licensePlate = licensePlate;
    this.rating = rating;
    this.seatCount = seatCount;
    this.engineType = engineType;
    this.manufacturer = manufacturer;
    this.deleted=false;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public DriverDO getDriver() {
    return driver;
  }

  public void setDriver(DriverDO driver) {
    this.driver = driver;
  }
}
