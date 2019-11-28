package com.freenow.domainobject;

import com.freenow.domainvalue.OnlineStatus;

/**
 * Created 10/21/2019 4:52 PM
 *
 * @author Rohit Rawani
 */
public class DriverSearchCriteria {

  private String username;
  private OnlineStatus status;
  private String licensePlate;
  private double rating;

  public DriverSearchCriteria() {
  }

  public String getUsername() {
    return username;
  }

  public OnlineStatus getStatus() {
    return status;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public double getRating() {
    return rating;
  }

  public DriverSearchCriteria(DriverSearchCriteriaBuilder builder) {
    this.username = builder.username;
    this.status = builder.status;
    this.licensePlate = builder.licensePlate;
    this.rating = builder.rating;
  }

  public static class DriverSearchCriteriaBuilder {

    private String username;
    private OnlineStatus status;
    private String licensePlate;
    private double rating;

    public DriverSearchCriteriaBuilder(String username, OnlineStatus status) {
      this.username = username;
      this.status = status;
    }

    public DriverSearchCriteriaBuilder setLicensePlate(String licensePlate) {
      this.licensePlate = licensePlate;
      return this;
    }

    public DriverSearchCriteriaBuilder setRating(double rating) {
      this.rating = rating;
      return this;
    }

    public DriverSearchCriteria build() {
      return new DriverSearchCriteria(this);
    }
  }
}
