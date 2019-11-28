package com.freenow;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.DriverSearchCriteria;
import com.freenow.domainvalue.EngineType;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FreeNowServerApplicantTestApplication.class)
public class FreeNowServerApplicantTestApplicationTests {

    @Autowired
    private DriverService driverService;
    @Autowired
    private CarService carService;


    public CarDO getCar(String licensePlate, EngineType type, String manufacturer,
        int seatCount, double rating) {
        CarDO car = new CarDO(licensePlate, type, manufacturer, seatCount, rating);
        try {
            car = carService.create(car);
        } catch (ConstraintsViolationException ex) {
        }
        return car;
    }

    public DriverDO getDriver(String username, String pwd) {
        DriverDO driver = new DriverDO(username, pwd);
        try {
            driver = driverService.create(driver);
        } catch (ConstraintsViolationException ex) {
        }
        return driver;
    }

    @Test public void contextLoads() {
    }

    @Test public void testSelectACar() {
        CarDO car = getCar("UP231700", EngineType.ELECTRIC, "TESLA", 4, 2.0);
        DriverDO driver = getDriver("driver09", "driver09pw");
        try {
            driver = driverService.selectThisCar(car.getId(), driver.getId());
            Assert.assertEquals("this car should be selected by the driver", car.getId(),
                driver.getCarDO().getId());
        } catch (Exception ex) {
            System.out.println("something went wrong..");
        }
    }

    @Test public void testDeSelectACar() {
        CarDO car = getCar("BR231700", EngineType.PETROL, "HONDA", 4, 3.0);
        DriverDO driver = getDriver("driver10", "driver10pw");
        try {
            driver = driverService.selectThisCar(car.getId(), driver.getId());
            driver = driverService.deSelectThisCar(car.getId(), driver.getId());
            Assert.assertNull("there should be no car selected by the driver now",
                driver.getCarDO());
        } catch (Exception ex) {
            System.out.println("something went wrong..");
        }
    }

    /**
     * searches for a driver based on the search criteria passed, if the driver has selected a car then only
     * it will be able to search with car characteristics.
     */
    @Test public void testSearchDrivers() {
        CarDO car = getCar("DL231700", EngineType.ELECTRIC, "HYUNDAI", 4, 4.0);
        DriverDO driver = getDriver("driver11", "driver11pw");
        try {
            driver = driverService.selectThisCar(car.getId(), driver.getId());
        } catch (Exception ex) {
        }
        /**
         * here we can see the benefit of builder pattern in DriverSearchCriteria
         * we need to only set the required attributes for which we want to search.
         * e.g. here we have not passed licensePlate but still it will give us DriverSearchCriteria object
         *
         * that's how it saves us to pass each parameters into the constructor.
         */
        DriverSearchCriteria searchCriteria =
            new DriverSearchCriteria.DriverSearchCriteriaBuilder(null, null)
                .setRating(1.0).build();
        List<DriverDO> drivers = driverService.searchDrivers(searchCriteria);
        Assert.assertEquals(
            "For now the test driver defined above is the only driver who is having a car with rating more than 1.0",
            driver.getUsername(), drivers.get(0).getUsername());
    }
}
