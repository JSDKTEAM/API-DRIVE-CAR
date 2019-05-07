package th.ac.ku.kps.eng.cpe.soa.driveCar.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user.UserDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.filter.AuthFilter;
import th.ac.ku.kps.eng.cpe.soa.driveCar.helper.JwtHelper;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.Login;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.CommonResponse;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.ResponseObj;

@DeclareRoles({"ADMIN","CUSTOMER","COMPANY"})
@Path("/services")
public class AuthService {
	
	
	
	static UserDAO userDAO = new UserDAO();
	
	public static User getUserAuth(HttpHeaders headers) {
		List<String> id = headers.getRequestHeader(AuthFilter.HEADER_PROPERTY_ID);
		
		if( id == null || id.size() != 1 )
			throw new NotAuthorizedException("Unauthorized!");
		
		return userDAO.findById(Integer.valueOf(id.get(0)));
	}
	
	private User validUser(String username,String password) {
		return userDAO.findByUsername(username,password);
	}
	
	@Path("/auth/login")
	@PermitAll 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Login request)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = userDAO.findByUsername(request.getUsername(), request.getPassword());
		CommonResponse responsePojo = new CommonResponse();
		
		if (user == null) {
			responsePojo.setMsg("Invalid Username or password");
			responsePojo.setStatus("401");
			return Response.status(401).entity(responsePojo).build();
		}
		
		String token = JwtHelper.generateToken(user);
		user.setRememberToken(token);
		userDAO.update(user);
		System.out.println(token);

		responsePojo.setResult(token);
		responsePojo.setStatus("200");
		responsePojo.setMsg("OK");
		return Response.ok().entity(responsePojo).build();
	}
	
	
	@Path("/auth/logout")
	@RolesAllowed({"ADMIN","COMPANY",""})
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpHeaders headers)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = getUserAuth(headers);
		CommonResponse responsePojo = new CommonResponse();
		if (user == null) {
			responsePojo.setMsg("Can not logout");
			responsePojo.setStatus("401");
			return Response.status(401).entity(responsePojo).build();
		}
		
		//user.setRememberToken(null);
		//userDAO.update(user);
		
		responsePojo.setResult(null);
		responsePojo.setStatus("200");
		responsePojo.setMsg("OK");
		return Response.ok().entity(responsePojo).build();
	}
}
