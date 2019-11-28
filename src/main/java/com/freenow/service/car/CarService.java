package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

import java.util.List;

/**
 * Created 10/17/2019 4:21 PM
 *
 * @author Rohit Rawani
 */
public interface CarService {

  CarDO find(Long id) throws EntityNotFoundException;

  CarDO create(CarDO carDO) throws ConstraintsViolationException;

  void delete(Long carId) throws EntityNotFoundException;

  List<CarDO> findAllCars();

  void updateCarRating(Long carId, double rating) throws EntityNotFoundException;
}
