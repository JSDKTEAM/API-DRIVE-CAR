package th.ac.ku.kps.eng.cpe.soa.driveCar.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.car.CarDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.rent.RentDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user.UserDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Rent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.CreateRent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.CreateRentMember;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.DeleteRent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.UpdateStatusRent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.*;

@DeclareRoles({ "ADMIN", "CUSTOMER", "GUEST" })
@Path("/services")
public class RentService {

	RentDAO rentDAO = new RentDAO();
	CarDAO carDAO = new CarDAO();
	UserDAO userDAO = new UserDAO();

	@GET
	@Path("/rents")
	@RolesAllowed({ "ADMIN" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRents() {
		try {

			ResponseList<Rent> res = new ResponseList<Rent>();
			res.setMsg("ok");
			res.setStatus("200");
			res.setResult(rentDAO.findAll());

			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@GET
	@PermitAll
	@Path("/rents/rentSearch/{rentSearch}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRentByRentSeach(@PathParam("rentSearch") String rentSearch) {
		try {

			ResponseObj<Rent> res = new ResponseObj<Rent>();
			res.setMsg("ok");
			res.setStatus("200");
			res.setResult(rentDAO.getRentByRentSeach(rentSearch));
			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@GET
	@RolesAllowed({ "ADMIN", "CUSTOMER" })
	@Path("/rents/users/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRentByRentSeach(@PathParam("userId") int userId) {
		try {

			ResponseList<Rent> res = new ResponseList<Rent>();
			res.setMsg("ok");
			res.setStatus("200");
			res.setResult(rentDAO.getRentByUserId(userId));
			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@POST
	@RolesAllowed({ "ADMIN", "CUSTOMER" })
	@Path("/rents/member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRentMember(@Context HttpHeaders headers, CreateRentMember request) throws Exception {
		boolean result = true;
		Rent rent = new Rent();
		User auth = AuthService.getUserAuth(headers);
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = df.parse(request.getStartDate());
			Date endDate = df.parse(request.getEndDate());

			rent.setAddressNotMember(null);
			rent.setPhoneNumberNotMember(null);
			rent.setRefId(null);
			rent.setStatus("รอการยืนยัน");
			rent.setCar(carDAO.findById(request.getCarId()));
			rent.setEndDate(endDate);
			rent.setStartDate(startDate);
			rent.setUser(auth);
			rent.setCreateDate(new Date());
			rent.setWithDriver(request.getWithDriver() ? (byte) 1 : (byte) 0);

			result = rentDAO.createRent(rent);
			if (result) {
				return Response.status(201).entity(rent).build();
			}
			return Response.status(500).entity(rent).build();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@POST
	@PermitAll
	@Path("/rents")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRentNotMember(CreateRent request) throws Exception {
		boolean result = true;
		Rent rent = new Rent();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = df.parse(request.getStartDate());
			Date endDate = df.parse(request.getEndDate());

			rent.setAddressNotMember(request.getAddressNotMember());
			rent.setPhoneNumberNotMember(request.getPhoneNumberNotMember());
			rent.setStatus("รอการยืนยัน");
			rent.setCar(carDAO.findById(request.getCarId()));
			rent.setEndDate(endDate);
			rent.setStartDate(startDate);
			rent.setRefId(request.getRefId());
			rent.setUser(null);
			rent.setCreateDate(new Date());
			rent.setWithDriver(request.isWithDriver() ? (byte) 1 : (byte) 0);

			result = rentDAO.createRent(rent);
			if (result) {
				return Response.status(201).entity(rent).build();
			}
			return Response.status(500).entity(rent).build();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@PUT
	@RolesAllowed({ "ADMIN" })
	@Path("/rents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatusRent(UpdateStatusRent request) throws Exception {
		try {
			ResponseObj<Rent> res = new ResponseObj<Rent>();

			res.setStatus("200");
			if (rentDAO.updateStatusRent(request)) {
				res.setMsg("update status rent success");
			} else {
				res.setMsg("update status rent fail");
			}
			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@DELETE
	@RolesAllowed({ "ADMIN" })
	@Path("/rents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRent(DeleteRent request) {
		ResponseObj<Rent> res = new ResponseObj<Rent>();
		Rent rent = rentDAO.findById(request.getRentId());
		res.setResult(rent);
		res.setStatus("200");
		if (rent.getRentId() > 0) {
			res.setMsg("delete success");
			rentDAO.delete(rent);
			return Response.status(200).entity(res).build();
		}
		res.setMsg("not have rentId : " + request.getRentId());
		return Response.status(500).entity(res).build();
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/rents/{rent_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRentById(@PathParam("rent_id") Long rent_id) {
		try {
			ResponseObj<Rent> res = new ResponseObj<Rent>();
			res.setMsg("ok");
			res.setStatus("200");
			res.setResult(rentDAO.findById(rent_id));
			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@GET
	@PermitAll
	@Path("/rents/cars/startDate/{startDate}/endDate/{endDate}/province/{provinceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRentCars(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate,
			@PathParam("provinceId") int provinceId) throws Exception {
		try {
			ResponseList<Rent> res = new ResponseList<Rent>();
			res.setMsg("ok");
			res.setStatus("200");
			res.setResult(rentDAO.seachCar(startDate, endDate, provinceId));
			System.out.println(res);
			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

}
