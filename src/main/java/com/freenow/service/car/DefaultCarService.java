package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created 10/17/2019 4:23 PM
 *
 * @author Rohit Rawani
 */
@Service
public class DefaultCarService implements CarService {

  private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);
  private final CarRepository carRepository;

  public DefaultCarService(final CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @Override public CarDO find(Long id) throws EntityNotFoundException {
    return findCarChecked(id);
  }

  @Override public CarDO create(CarDO carDO) throws ConstraintsViolationException {
    CarDO car;
    try {
      car = carRepository.save(carDO);
    } catch (DataIntegrityViolationException e) {
      LOG.warn("ConstraintsViolationException while creating a driver: {}", carDO, e);
      throw new ConstraintsViolationException(e.getMessage());
    }
    return car;
  }

  @Override public void delete(Long carId) throws EntityNotFoundException {
    CarDO carDO = findCarChecked(carId);
    carDO.setDeleted(true);
  }

  private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
    return carRepository.findById(carId).orElseThrow(
        () -> new EntityNotFoundException("Could not find entity with id: " + carId));
  }

  /**
   * Find all cars
   */
  @Override public List<CarDO> findAllCars() {
    return (List<CarDO>) carRepository.findAll();
  }

  @Override
  @Transactional
  public void updateCarRating(Long carId, double rating) throws EntityNotFoundException {
    CarDO carDO = findCarChecked(carId);
    carDO.setRating(rating);
  }
}
