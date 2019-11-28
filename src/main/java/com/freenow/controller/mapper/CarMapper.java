package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created 10/17/2019 3:51 PM
 *
 * @author Rohit Rawani
 */
public class CarMapper {

  public static CarDO makeCarDO(CarDTO carDTO) {
    return new CarDO(carDTO.getLicensePlate(), carDTO.getEngineType(),
        carDTO.getManufacturer(), carDTO.getSeatCount(), carDTO.getRating());
  }

  public static CarDTO makeCarDTO(CarDO carDO) {
    CarDTO.CarDTOBuilder carDTOBuilder =
        new CarDTO.CarDTOBuilder(carDO.getId(), carDO.getLicensePlate(),
            carDO.getManufacturer())
            .setEngineType(carDO.getEngineType())
            .setRating(carDO.getRating())
            .setSeatCount(carDO.getSeatCount());
    return carDTOBuilder.build();
  }

  public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars) {
    return cars.stream().map(CarMapper::makeCarDTO).collect(Collectors.toList());
  }
}
