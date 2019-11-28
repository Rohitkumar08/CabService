package com.freenow.service.driver;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.DriverSearchCriteria;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

import java.sql.Driver;
import java.util.List;
import java.util.stream.Collectors;

import com.freenow.service.car.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    @Autowired
    private final CarService carService;


    public DefaultDriverService(final DriverRepository driverRepository,
        CarService carService)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

    /**
     * A car is selected by a driver by passing the carId to this method
     * @param carId
     * @param driverId
     * @throws CarAlreadyInUseException
     * @throws EntityNotFoundException
     * @return
     */
    @Override
    @Transactional
    public DriverDO selectThisCar(Long carId, Long driverId)
        throws CarAlreadyInUseException, EntityNotFoundException {
        CarDO car = carService.find(carId);
        DriverDO driver = find(driverId);
        if (car.getDriver() != null
            && car.getDriver().getOnlineStatus() == OnlineStatus.ONLINE) {
            throw new CarAlreadyInUseException();
        } else {
            driver.setCarDO(car);
        }
        return driver;
    }

    /**
     * selected car can be removed by the only driver who is currently using it
     *
     * @param carId
     * @param driverId
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public DriverDO deSelectThisCar(Long carId, Long driverId)
        throws EntityNotFoundException {
        CarDO car = carService.find(carId);
        DriverDO driver = find(driverId);
        if (car.getDriver() != null && car.getDriver().getId()==driverId){
            driver.setCarDO(null);
        }
        return driver;
    }

    /**
     * based on given search criteria we are filtering the results.
     * NOTE: could have used dynamic queries to build the query and fetch only
     * the filtered results from repository itself but was not aware of
     * writing queries in h2 db
     *
     * @param criteria
     * @return
     */
    @Override public List<DriverDO> searchDrivers(DriverSearchCriteria criteria) {
        List<DriverDO> results;
        if (criteria.getStatus() != null) {
            results = driverRepository.findByOnlineStatus(criteria.getStatus());
        } else {
            results = (List<DriverDO>) driverRepository.findAll();
        }
        if (criteria.getUsername() != null) {
            results = results.stream().filter(
                driver -> driver.getUsername().equalsIgnoreCase(criteria.getUsername()))
                .collect(Collectors.toList());
        }
        if (criteria.getLicensePlate() != null) {
            results = results.stream().filter(driver -> {
                if (driver.getCarDO() != null) {
                    return driver.getCarDO().getLicensePlate()
                        .equalsIgnoreCase(criteria.getLicensePlate());
                }
                return false;
            }).collect(Collectors.toList());
        }
        if (criteria.getRating() > 0.0) {
            results = results.stream().filter(driver -> {
                if (driver.getCarDO() != null) {
                    return driver.getCarDO().getRating() >= criteria.getRating();
                }
                return false;
            }).collect(Collectors.toList());
        }
        return results;
    }
}
