package com.freenow.dataaccessobject;

import com.freenow.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;

/**
 * Created 10/17/2019 4:30 PM
 *
 * @author Rohit Rawani
 */
public interface CarRepository extends CrudRepository<CarDO, Long> {

}
