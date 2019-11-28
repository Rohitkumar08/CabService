package com.freenow.service.driver;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.DriverSearchCriteria;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    DriverDO selectThisCar(Long carId, Long driverId) throws EntityNotFoundException,
        CarAlreadyInUseException;

    DriverDO deSelectThisCar(Long carId, Long driverId) throws EntityNotFoundException;

    List<DriverDO> searchDrivers(DriverSearchCriteria criteria);
}
