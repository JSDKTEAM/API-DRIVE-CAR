package th.ac.ku.kps.eng.cpe.soa.driveCar.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.Login;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.CommonResponse;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.ResponseObj;

@Path("/services")
public class AuthService {
	
	private static final String JWT_TOKEN_KEY = "#111EaD8825DDGIT";
	
	static UserDAO userDAO = new UserDAO();
	
	public static User getUserAuth(HttpHeaders headers) {
		List<String> id = headers.getRequestHeader(AuthFilter.HEADER_PROPERTY_ID);
		
		if( id == null || id.size() != 1 )
			throw new NotAuthorizedException("Unauthorized!");
		
		return userDAO.findById(Integer.valueOf(id.get(0)));
	}
	
	private String generateToken(User c) throws IllegalArgumentException, UnsupportedEncodingException {

		try {
			Algorithm algorithm = Algorithm.HMAC256(AuthFilter.JWT_TOKEN_KEY);
			Date expirationDate = Date.from(ZonedDateTime.now().plusHours(24).toInstant());
			Date issuedAt = Date.from(ZonedDateTime.now().toInstant());
			return JWT.create()
					// Issue date.
					.withIssuedAt(issuedAt)
					// Expiration date.
					//.withExpiresAt(expirationDate)
					// User id - here we can put anything we want, but for the example userId is
					// appropriate.
					.withClaim("username", c.getUsername())
					// Issuer of the token.
					.withIssuer("jwtauth")
					// And the signing algorithm.
					.sign(algorithm);
		} catch (JWTCreationException e) {
			// LOGGER.error(e.getMessage(), e);
		}

		return null;
	}
	
	private User validateToken(String token) throws IllegalArgumentException, UnsupportedEncodingException {

		try {
			if (token != null) {
				Algorithm algorithm = Algorithm.HMAC256(AuthFilter.JWT_TOKEN_KEY);
				JWTVerifier verifier = JWT.require(algorithm).withIssuer("jwtauth").build(); // Reusable verifier
																								// instance
				DecodedJWT jwt = verifier.verify(token);
				// Get the userId from token claim.
				Claim cus = jwt.getClaim("username");
				System.out.println(cus.asString());
				// Find user by token subject(id).
				// c userDao = new UserDao();
				return userDAO.findByUsername(cus.asString());
			}
		} catch (JWTVerificationException e) {
			// LOGGER.error(e.getMessage(), e);
		}

		return null;
	}
	
	private User validUser(String username) {
		return userDAO.findByUsername(username);
	}
	
	@Path("/auth/login")
	@PermitAll 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Login request)
			throws JsonGenerationException, JsonMappingException, IOException {

		User user = validUser(request.getUsername());
		CommonResponse responsePojo = new CommonResponse();
		if (user == null) {
			responsePojo.setMsg("Invalid Username or password");
			responsePojo.setStatus("401");
			return Response.status(401).entity(responsePojo).build();
		}

		responsePojo.setResult(generateToken(user));
		responsePojo.setStatus("200");
		responsePojo.setMsg("OK");
		return Response.ok().entity(responsePojo).build();
	}
}
