package th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import th.ac.ku.kps.eng.cpe.soa.dao.SessionUtil;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.BaseDao;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.rent.RentDAOInterface;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Rent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;

public class UserDAO  extends BaseDao<User, Long> implements UserDAOInterface{

	public UserDAO() {
		super(User.class);
	}

	@Override
	public User findByUsername(String username) {
		Session session = SessionUtil.getSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("username", username));
		return (User) cr.list().get(0);
	}
	

}
