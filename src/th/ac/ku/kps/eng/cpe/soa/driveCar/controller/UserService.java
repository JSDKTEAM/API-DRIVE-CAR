package th.ac.ku.kps.eng.cpe.soa.driveCar.controller;

import java.util.HashSet;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user.UserDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.helper.JwtHelper;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Company;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Rent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.CreateUser;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.Register;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.ResponseList;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.ResponseObj;

@DeclareRoles({"ADMIN","CUSTOMER","COMPANY"})
@Path("/services")
public class UserService {
	UserDAO userDAO = new UserDAO();

	@POST
	@PermitAll 
	@Path("/users/registers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(Register request) {
		try {
			ResponseObj<User> res = new ResponseObj<User>();

			User user = new User();
			user.setUsername(request.getUsername());
			user.setAddress(request.getAddress());
			user.setFname(request.getFname());
			user.setLname(request.getLname());
			user.setPassword(request.getPassword());
			user.setRememberToken(null);
			user.setPhoneNumber(request.getPhoneNumber());
			user.setTypeUser("CUSTOMER");
			user.setRents(new HashSet<Rent>());
			
			if(userDAO.persist(user)) {
				res.setResult(user);
				res.setStatus("201");
				res.setMsg("register success");
				return Response.status(201).entity(res).build();
			}
			
			res.setMsg("register fail");
			res.setStatus("500");
			return Response.status(500).entity(res).build();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@GET
	@RolesAllowed({"ADMIN"})
	@Path("/users/typeUser/{typeUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByType(@PathParam("typeUser") String typeUser) {
		try {
			ResponseList<User> res = new ResponseList<User>();
			res.setMsg("OK");
			res.setStatus("200");
			res.setResult(userDAO.findUserByType(typeUser));
			return Response.status(200).entity(res).build();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@DELETE
	@RolesAllowed({"ADMIN"})
	@Path("/users/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUserId(@PathParam("userId") int userId) {
		try {
			ResponseObj<User> res = new ResponseObj<User>();
	
			res.setStatus("200");
			User d = userDAO.findById(userId);
			boolean check = userDAO.delete(d);
			if(check) {
				res.setStatus("200");
				res.setResult(d);
				res.setMsg("delete success");
				return Response.status(200).entity(res).build();
			}
			res.setStatus("500");
			res.setResult(d);
			res.setMsg("delete fail");
			return Response.status(200).entity(res).build();
		}
		catch(Exception e) {
			throw e;
		}
	}

	@POST
	@RolesAllowed({"ADMIN"})
	@Path("/users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(CreateUser request) throws Exception {
		try {
			ResponseObj<User> res = new ResponseObj<User>();
			
			User user = new User();
			user.setUsername(request.getUsername());
			user.setAddress(request.getAddress());
			user.setFname(request.getFname());
			user.setLname(request.getLname());
			user.setPassword(request.getPassword());
			user.setPhoneNumber(request.getPhoneNumber());
			user.setTypeUser(request.getTypeUser());
			String token = JwtHelper.generateToken(user);
			user.setRememberToken(token);
			
			if(request.getTypeUser() == "COMPANY") {
				Company company = new Company();
				company.setName(request.getCompanyName());
				company.setPhoneNumber(request.getPhoneNumber());
				company.setUser(user);
			}
			
			if(userDAO.persist(user)) {
				res.setResult(user);
				res.setStatus("201");
				res.setMsg("create user success");
				return Response.status(201).entity(user).build();
			}
			
			res.setMsg("create user fail");
			res.setStatus("500");
			return Response.status(500).entity(user).build();
		} catch (Exception e) {
			throw e;
		}
	}
}
