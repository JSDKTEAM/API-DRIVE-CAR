package th.ac.ku.kps.eng.cpe.soa.driveCar.helper;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user.UserDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.filter.AuthFilter;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;

public class JwtHelper {
	
	static UserDAO userDAO = new UserDAO();
	
	public static String generateToken(User c) throws IllegalArgumentException, UnsupportedEncodingException {

		try {
			Algorithm algorithm = Algorithm.HMAC256(AuthFilter.JWT_TOKEN_KEY);
			//Date expirationDate = Date.from(ZonedDateTime.now().plusHours(24).toInstant());
			Date issuedAt = Date.from(ZonedDateTime.now().toInstant());
			return JWT.create()
					// Issue date.
					.withIssuedAt(issuedAt)
					// Expiration date.
					//.withExpiresAt(expirationDate)
					// User id - here we can put anything we want, but for the example userId is
					// appropriate.
					.withClaim("userId", c.getUserId())
					.withClaim("username", c.getUsername())
					.withClaim("typeUser", c.getTypeUser())
					// Issuer of the token.
					.withIssuer("jwtauth")
					// And the signing algorithm.
					.sign(algorithm);
		} catch (JWTCreationException e) {
			// LOGGER.error(e.getMessage(), e);
		}

		return null;
	}
	
	public static User validateToken(String token) throws IllegalArgumentException, UnsupportedEncodingException {

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
}
