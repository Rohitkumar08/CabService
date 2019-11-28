package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freenow.domainvalue.OnlineStatus;

/**
 * Created 10/21/2019 5:23 PM
 *
 * @author Rohit Rawani
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverSearchDTO {

  private String username;
  private String licensePlate;
  private double rating;
  private OnlineStatus status;

  public DriverSearchDTO() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public OnlineStatus getStatus() {
    return status;
  }

  public void setStatus(OnlineStatus status) {
    this.status = status;
  }


}
