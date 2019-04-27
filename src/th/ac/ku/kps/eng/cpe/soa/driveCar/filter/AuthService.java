package th.ac.ku.kps.eng.cpe.soa.driveCar.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user.UserDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;

public class AuthService {
	public boolean authenticate(String authCredentials) {
		if (null == authCredentials)
			return false;
		final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");

		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		UserDAO u = new UserDAO();
		User user = u.findByUsername(username);
		boolean authenticationStatus = user.getUsername().equals(username) && user.getPassword().equals(password);

		return authenticationStatus;
	}
}
