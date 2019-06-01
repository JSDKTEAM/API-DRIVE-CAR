package th.ac.ku.kps.eng.cpe.soa.driveCar.dao.car;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.BaseDao;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.car.CarDAOInterface;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Car;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Rent;

public class CarDAO extends BaseDao<Car, Long> implements CarDAOInterface {

	public CarDAO() {
		 super(Car.class);
	}


}
