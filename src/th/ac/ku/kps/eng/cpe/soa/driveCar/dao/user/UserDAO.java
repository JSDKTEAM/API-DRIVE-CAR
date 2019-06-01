package th.ac.ku.kps.eng.cpe.soa.driveCar.dao.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.BaseDao;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.SessionUtil;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.rent.RentDAOInterface;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Rent;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.User;

public class UserDAO extends BaseDao<User, Long> implements UserDAOInterface {

	public UserDAO() {
		super(User.class);
	}

	@Override
	public User findByUsername(String username) {
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("username", username));
			return (User) cr.list().get(0);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public User findByUsername(String username, String password) {
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("username", username));
			cr.add(Restrictions.eq("password", password));
			return (User) cr.list().get(0);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<User> findUserByType(String typeUser) {
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(User.class, "user");
			cr.add(Restrictions.eq("typeUser", typeUser))
			.createCriteria("user.companies" , "company");
//	        .setProjection( Projections.projectionList()
//	        .add(Projections.property("user"))
//            .add(Projections.property("user.companies")));
			return cr.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
