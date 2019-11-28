package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created 10/19/2019 2:06 PM
 *
 * @author Rohit Rawani
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Some constraints are violated ...")
public class CarAlreadyInUseException extends Exception {

  public CarAlreadyInUseException() {
    super("Car already in use..Please select another");

  }
}
