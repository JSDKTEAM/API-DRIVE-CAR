package th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user;

import java.util.List;

import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.DaoInterface;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;

public interface UserDAOInterface extends DaoInterface<User, Long> {
	User findByUsername(String username);
	
	User findByUsername(String username,String password);
	
	List<User> findUserByType(String typeUSer);
}
