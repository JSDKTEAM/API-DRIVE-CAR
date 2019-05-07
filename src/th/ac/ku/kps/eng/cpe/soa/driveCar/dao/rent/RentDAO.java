package th.ac.ku.kps.eng.cpe.soa.driveCar.dao.rent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import th.ac.ku.kps.eng.cpe.soa.dao.SessionUtil;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.BaseDao;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.car.CarDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.*;
import th.ac.ku.kps.eng.cpe.soa.driveCar.request.UpdateStatusRent;

public class RentDAO extends BaseDao<Rent, Long> implements RentDAOInterface {

	public RentDAO() {
		super(Rent.class);
	}

	private StringBuffer randomRentSearch() {
		Random rnd = new Random();
		long unixTime = System.currentTimeMillis() / 1000L;
		StringBuffer data = new StringBuffer();
		// data.append("#");
		data.append((char) (rnd.nextInt(26) + 'A'));
		data.append(unixTime);
		data.append((char) (rnd.nextInt(26) + 'A'));
		data.append((char) (rnd.nextInt(26) + 'A'));
		data.append((char) (rnd.nextInt(26) + 'A'));
		data.append((char) (rnd.nextInt(26) + 'A'));
		return data;
	}

	@Override
	public boolean createRent(Rent rent) {
		long diffInMillies = Math.abs(rent.getEndDate().getTime() - rent.getStartDate().getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		double price = rent.getCar().getPrice();
		if (rent.getWithDriver() == (byte) 1) {
			price += 500;
		}
		rent.setPriceRent(price * diff);
		rent.setRentSearch(randomRentSearch().toString());
		boolean check = this.persist(rent);
		return check;
	}

	@Override
	public List<Rent> seachCar(String start_date, String end_date, int province_id) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(start_date);
		Date endDate = df.parse(end_date);
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(Rent.class, "rent");
			cr.createAlias("rent.car", "car");
			cr.createAlias("car.provinceByAddressProvince", "province");
//		cr.add(Restrictions.eq("startDate", startDate));
//		cr.add(Restrictions.eq("endDate", endDate));
			cr.add(Restrictions.eq("province.provinceId", province_id));
			// .setProjection(Projections.projectionList();
			// .add(Projections.property("car.carId"), "carId"));
			// .add(Projections.property("car.licensePlate"), "licensePlate"));
			// cr.setResultTransformer(Transformers.aliasToBean(Car.class));
			List<Rent> rent = cr.list();
			List<Integer> car = new ArrayList<Integer>();

			Criteria cc = session.createCriteria(Car.class, "car");
			if (!cr.list().isEmpty()) {
				for (Rent r : rent) {
					int checkDate = r.getStartDate().compareTo(startDate) * r.getEndDate().compareTo(startDate)
							* r.getStartDate().compareTo(endDate) * r.getEndDate().compareTo(endDate);
					if (checkDate <= 0) {
						car.add(r.getCar().getCarId());
					}
				}

				if (car.size() > 0) {
					cc.createAlias("car.provinceByAddressProvince", "province")
							.add(Restrictions.eq("province.provinceId", province_id))
							.add(Restrictions.not(Restrictions.in("carId", car)));
				}
			} else {
				cc.createAlias("car.provinceByAddressProvince", "province")
						.add(Restrictions.eq("province.provinceId", province_id));
			}

			return cc.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Rent> seachCar(String start_date, String end_date, String province, String typeCar)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(start_date);
		Date endDate = df.parse(end_date);
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(Rent.class, "rent");
			cr.createAlias("rent.car", "car");
			cr.add(Restrictions.eq("car.typeCar", typeCar));
			cr.createAlias("car.provinceByAddressProvince", "province");
			cr.add(Restrictions.eq("province.name", province));

			List<Rent> rent = cr.list();
			List<Integer> car = new ArrayList<Integer>();
//		System.out.println(startDate);
//		System.out.println(endDate);
//		System.out.println( cr.list());
			Criteria cc = session.createCriteria(Car.class, "car");
			cc.add(Restrictions.eq("car.typeCar", typeCar));

			if (!cr.list().isEmpty()) {
				for (Rent r : rent) {
					int checkDate = r.getStartDate().compareTo(startDate) * r.getEndDate().compareTo(startDate)
							* r.getStartDate().compareTo(endDate) * r.getEndDate().compareTo(endDate);
					if (checkDate <= 0) {
						car.add(r.getCar().getCarId());
					}
				}

				if (car.size() > 0) {
					cc.createAlias("car.provinceByAddressProvince", "province")
							.add(Restrictions.eq("province.name", province))
							.add(Restrictions.not(Restrictions.in("carId", car)));
				}
			} else {
				cc.createAlias("car.provinceByAddressProvince", "province")
						.add(Restrictions.eq("province.name", province));
			}

			return cc.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Rent> seachCar(String start_date, String end_date, int province_id, boolean isAsc)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(start_date);
		Date endDate = df.parse(end_date);
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(Rent.class, "rent");
			cr.createAlias("rent.car", "car");
			cr.createAlias("car.provinceByAddressProvince", "province");
			cr.add(Restrictions.eq("province.provinceId", province_id));
			List<Rent> rent = cr.list();
			List<Integer> car = new ArrayList<Integer>();

			Criteria cc = session.createCriteria(Car.class, "car");

			if (isAsc) {
				cc.addOrder(Order.asc("price"));
			} else {
				cc.addOrder(Order.desc("price"));
			}

			if (!cr.list().isEmpty()) {
				for (Rent r : rent) {
					int checkDate = r.getStartDate().compareTo(startDate) * r.getEndDate().compareTo(startDate)
							* r.getStartDate().compareTo(endDate) * r.getEndDate().compareTo(endDate);
					if (checkDate <= 0) {
						car.add(r.getCar().getCarId());
					}
				}

				if (car.size() > 0) {
					cc.createAlias("car.provinceByAddressProvince", "province")
							.add(Restrictions.eq("province.provinceId", province_id))
							.add(Restrictions.not(Restrictions.in("carId", car)));
				}
			} else {
				cc.createAlias("car.provinceByAddressProvince", "province")
						.add(Restrictions.eq("province.provinceId", province_id));
			}

			return cc.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Rent> seachCar(String start_date, String end_date, String province) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(start_date);
		Date endDate = df.parse(end_date);
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(Rent.class, "rent");
			cr.createAlias("rent.car", "car");
			cr.createAlias("car.provinceByAddressProvince", "province");
			cr.add(Restrictions.eq("province.name", province));
			List<Rent> rent = cr.list();
			List<Integer> car = new ArrayList<Integer>();
			System.out.println(startDate);
			System.out.println(endDate);
			System.out.println(cr.list());
			Criteria cc = session.createCriteria(Car.class, "car");

			if (!cr.list().isEmpty()) {
				for (Rent r : rent) {
					int checkDate = r.getStartDate().compareTo(startDate) * r.getEndDate().compareTo(startDate)
							* r.getStartDate().compareTo(endDate) * r.getEndDate().compareTo(endDate);
					if (checkDate <= 0) {
						car.add(r.getCar().getCarId());
					}
				}

				if (car.size() > 0) {
					cc.createAlias("car.provinceByAddressProvince", "province")
							.add(Restrictions.eq("province.name", province))
							.add(Restrictions.not(Restrictions.in("carId", car)));
				}
			} else {
				cc.createAlias("car.provinceByAddressProvince", "province")
						.add(Restrictions.eq("province.name", province));
			}

			return cc.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Rent> getRentByUserId(int userId) {
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(Rent.class, "rent");
			cr.createAlias("rent.user", "user");
			cr.add(Restrictions.eq("user.userId", userId));
			return cr.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean deleteRentByRentSearch(String rentSearch) {
		Session session = SessionUtil.getSession();
		try {
			Criteria cr = session.createCriteria(Rent.class);
			cr.add(Restrictions.eq("rentSearch", rentSearch));
			if (cr.list().size() >= 0) {
				Rent rent = (Rent) cr.list().get(0);
				if (session != null) {
					session.close();
				}
				if (rent.getStatus().contentEquals("รอการยืนยัน")) {
					System.out.println(rent.getStatus());
					this.delete(rent);
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public Rent getRentByRentSeach(String rentSearch) {
		Session session = SessionUtil.getSession();
		try {
			Criteria cc = session.createCriteria(Rent.class);
			cc.add(Restrictions.eq("rentSearch", rentSearch));
			return (Rent) cc.list().get(0);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean updateStatusRent(UpdateStatusRent rentInput) {
		try {
			Rent r = this.findById(rentInput.getRentId());
			r.setStatus(rentInput.getStatus());
			System.out.println(r.getStatus());
			return this.update(r);
		} catch (Exception e) {
			return false;
		}
	}

}
