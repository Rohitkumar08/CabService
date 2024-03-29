package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created 10/17/2019 4:19 PM
 *
 * @author Rohit Rawani
 */
@RestController
@RequestMapping("v1/cars")
public class CarController {

  private final CarService carService;

  @Autowired
  public CarController(final CarService carService)
  {
    this.carService = carService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CarDTO createCar(
      @Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
    CarDO carDO = CarMapper.makeCarDO(carDTO);
    return CarMapper.makeCarDTO(carService.create(carDO));
  }

  @DeleteMapping("/{carId}")
  public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
  {
    carService.delete(carId);
  }

  @PutMapping("/{carId}")
  public void updateCarRating(
      @PathVariable long carId, @RequestParam double rating)
      throws EntityNotFoundException
  {
    carService.updateCarRating(carId, rating);
  }

  @GetMapping("/allCars")
  public List<CarDTO> getAllCars()
  {
    return CarMapper.makeCarDTOList(carService.findAllCars());
  }
}
