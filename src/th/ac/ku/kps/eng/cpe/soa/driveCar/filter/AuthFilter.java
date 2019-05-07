package th.ac.ku.kps.eng.cpe.soa.driveCar.filter;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jose4j.jwt.consumer.InvalidJwtException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.media.jfxmedia.logging.Logger;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user.UserDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;

public class AuthFilter implements ContainerRequestFilter {

	
	UserDAO userDao = new UserDAO();
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	 
	public static final String JWT_TOKEN_KEY = "#111EaD8825DDGIT";

	public static final String HEADER_PROPERTY_ID = "USER_ID";
	
	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws WebApplicationException, AccessDeniedException, UnsupportedEncodingException {
		Method method = resourceInfo.getResourceMethod();
		// everybody can access (e.g. user/create or user/authenticate)
		if (!method.isAnnotationPresent(PermitAll.class)) {
			// nobody can access
			if (method.isAnnotationPresent(DenyAll.class)) {
				throw new WebApplicationException(Status.FORBIDDEN);
			}

			// get request headers to extract jwt token
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
			
			System.out.println(authorization);

			// block access if no authorization information is provided
			if (authorization == null || authorization.isEmpty()) {
				// logger.warn("No token provided!");
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

			User auth = null;
			String jwt = authorization.get(0);

			auth = validateToken(jwt);

			// check if token matches an user token (set in user/authenticate)

			// verify user access from provided roles ("ADMIN", "CUSTOMER", "COMPANY")
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				// get annotated roles
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
				// user valid?
				if (!isUserAllowed(auth.getTypeUser(), rolesSet)) {
					// logger.warn("User does not have the rights to acces this resource!");
					throw new WebApplicationException(Status.UNAUTHORIZED);
				}
			}
		
			List<String> idList = new ArrayList<String>();
		    idList.add(auth.getUserId().toString());
			headers.put(HEADER_PROPERTY_ID , idList);
		}
	}

	private boolean isUserAllowed(String typeUser, Set<String> rolesSet) {
		boolean isAllow = false;
		if(rolesSet.contains(typeUser))
        {
			isAllow = true;
        }
		return isAllow;
	}

	private User validateToken(String token) throws IllegalArgumentException, UnsupportedEncodingException {

		try {
			if (token != null) {
				Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
				JWTVerifier verifier = JWT.require(algorithm).withIssuer("jwtauth").build(); // Reusable verifier
																								// instance
				DecodedJWT jwt = verifier.verify(token);
				// Get the userId from token claim.
				Claim cus = jwt.getClaim("username");
				// Find user by token subject(id).
				// c userDao = new UserDao();
				return userDao.findByUsername(cus.asString());
			}
		} catch (JWTVerificationException e) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		return null;
	}
}
