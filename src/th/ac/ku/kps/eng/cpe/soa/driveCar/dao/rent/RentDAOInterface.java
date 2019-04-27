package th.ac.ku.kps.eng.cpe.soa.driveCar.dao.rent;

import java.text.ParseException;
import java.util.List;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.DaoInterface;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Rent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.UpdateStatusRent;

public interface RentDAOInterface extends DaoInterface<Rent, Long> {
	
	boolean createRent(Rent rent);
	
	List<Rent> seachCar(String start_date,String end_date,int province_id) throws ParseException;
	
	Rent getRentByRentSeach(String rentSearch);
	
	List<Rent> getRentByUserId(int UserId);
	
	boolean updateStatusRent(UpdateStatusRent rent);
	
}
