package th.ac.ku.kps.eng.cpe.soa.driveCar.controller;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.car.CarDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.province.ProvinceDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Car;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.CreateCar;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.UpdateCar;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.*;

@DeclareRoles({"ADMIN","CUSTOMER","COMPANY"})
@Path("/services")
public class CarService {

	CarDAO carDAO = new CarDAO();
	ProvinceDAO provinceDAO = new ProvinceDAO();

	public static String convertToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// System.out.println( c.findById(1));
		String jsonInString = mapper.writeValueAsString(obj);
		System.out.println(jsonInString);
		return jsonInString;
	}

	@GET
	@RolesAllowed({"ADMIN"})
	@Path("/cars")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Car> getCars(@Context HttpHeaders headers) throws JsonProcessingException {
		User auth = AuthService.getUserAuth(headers);
		try {
			return carDAO.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@GET
	@PermitAll
	@Path("/cars/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCar(@PathParam("carId") int carId) {
		ResponseObj<Car> res = new ResponseObj<Car>();
		Car car = carDAO.findById(carId);
		res.setStatus("200");
		res.setMsg("OK");
		res.setResult(car);
		return Response.status(200).entity(res).build();
	}

	@POST
	@RolesAllowed({"ADMIN"})
	@Path("/cars")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCar(CreateCar request) throws Exception {
		boolean result = false;
		ResponseObj<Car> res = new ResponseObj<Car>();
		try {
			Car car = new Car();
			car.setModel(request.getModel());
			car.setBrand(request.getBrand());
			car.setSeatCount(request.getSeatCount());
			car.setDiscount(request.getDiscount());
			car.setPrice(request.getPrice());
			car.setTypeCar(request.getTypeCar());
			car.setProvinceByAddressProvince(provinceDAO.findById(request.getProvinceByAddressProvince()));
			car.setProvinceByProvinceId(provinceDAO.findById(request.getProvinceByProvinceId()));
			car.setLicensePlate(request.getLicensePlate());

			result = carDAO.persist(car);
			res.setResult(car);
			if (result) {
				res.setStatus("201");
				res.setMsg("create success");
				return Response.status(201).entity(res).build();
			}
			res.setStatus("500");
			res.setMsg("create fail");
			return Response.status(201).entity(res).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@PUT
	@RolesAllowed({"ADMIN"})
	@Path("/cars/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(@PathParam("carId") int carId,UpdateCar request) {
		ResponseObj<Car> res = new ResponseObj<Car>();
		
		Car car = carDAO.findById(carId);
		car.setModel(request.getModel());
		car.setBrand(request.getBrand());
		car.setDiscount(request.getDiscount());
		car.setLicensePlate(request.getLicensePlate());
		car.setPrice(request.getPrice());
		car.setProvinceByAddressProvince(provinceDAO.findById(request.getProvinceByAddressProvince()));
		car.setProvinceByProvinceId(provinceDAO.findById(request.getProvinceByProvinceId()));
		car.setSeatCount(request.getSeatCount());
		car.setTypeCar(request.getTypeCar());
		
		boolean check = carDAO.update(car);
		res.setResult(car);
		if(check) {
			res.setMsg("update car success");
			res.setStatus("200");
			return Response.status(200).entity(res).build(); 
		}
		res.setMsg("update car fail");
		res.setStatus("500");
		return Response.status(200).entity(res).build(); 
	}

	@DELETE
	@RolesAllowed({"ADMIN"})
	@Path("/cars/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCar(@PathParam("carId") int carId) {
		ResponseObj<Car> res = new ResponseObj<Car>();
		Car car = carDAO.findById(carId);
		boolean check = carDAO.delete(car);
		res.setResult(car);
		if (check) {
			res.setMsg("delete car success");
			res.setStatus("200");
			return Response.status(200).entity(res).build();
		}
		res.setMsg("delete car fail");
		res.setStatus("500");
		return Response.status(200).entity(res).build();
	}
}
