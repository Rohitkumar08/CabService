package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.datatransferobject.DriverSearchDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.DriverSearchCriteria;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import java.util.List;
import javax.validation.Valid;
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

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;
    private final CarService carService;

    @Autowired
    public DriverController(final DriverService driverService, final CarService carService)
    {
        this.driverService = driverService;
        this.carService = carService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }

    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }

    @PutMapping("/selectACar") public void selectACar(@RequestParam long driverId,
        @RequestParam long carId)
        throws CarAlreadyInUseException, EntityNotFoundException {
        driverService.selectThisCar(carId, driverId);
    }

    @PutMapping("/deSelectACar") public void deSelectACar(@RequestParam long driverId,
        @RequestParam long carId)
        throws EntityNotFoundException {
        driverService.deSelectThisCar(carId, driverId);
    }

    @GetMapping("/searchDrivers") public List<DriverDTO> searchDrivers(
        @Valid @RequestBody DriverSearchDTO searchDTO) {
        DriverSearchCriteria searchCriteria =
            new DriverSearchCriteria.DriverSearchCriteriaBuilder(searchDTO.getUsername(),
                searchDTO.getStatus())
                .setLicensePlate(searchDTO.getLicensePlate())
                .setRating(searchDTO.getRating()).build();
        return DriverMapper
            .makeDriverDTOList(driverService.searchDrivers(searchCriteria));
    }
}
