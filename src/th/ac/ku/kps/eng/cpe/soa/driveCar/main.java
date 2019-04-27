package th.ac.ku.kps.eng.cpe.soa.driveCar;

import java.text.ParseException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.car.CarDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.province.ProvinceDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.rent.RentDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Car;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.UpdateStatusRent;




public class main {
	
	

	public static void main(String[] args) throws JsonProcessingException, ParseException {
		// TODO Auto-generated method stub
		RentDAO c = new RentDAO() ;
		
		//convertToJson(c.seachCar("2019-04-16","2019-04-17",1));		
		
		convertToJson(c.getRentByRentSeach("c0cfacca-5a52-4f00-b7ba-cb123700cf56"));
//		CarDAO carDAO = new CarDAO();
//	    Car c1 = new Car();
//	    c1.setAddressProvince(1);
		
	    
//	    ProvinceDAO provinceDAO = new ProvinceDAO();
//	    convertToJson(provinceDAO.findById(1));
		
	}
	
	public static String convertToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		//System.out.println( c.findById(1));
		String jsonInString = mapper.writeValueAsString(obj);
		System.out.println(jsonInString);
		return jsonInString;
	}
	
	

}
