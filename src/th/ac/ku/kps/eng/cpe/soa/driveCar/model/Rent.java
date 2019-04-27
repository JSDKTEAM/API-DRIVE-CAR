package th.ac.ku.kps.eng.cpe.soa.driveCar.model;
// Generated Apr 27, 2019 4:05:42 PM by Hibernate Tools 5.3.6.Final

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Rent generated by hbm2java
 */
public class Rent implements java.io.Serializable {

	@Id
	private Integer rentId;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name = "car_id")
	@JsonManagedReference
	private Car car;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonManagedReference
	private User user;
	private String rentSearch;
	private String status;
	private Integer refId;
	private byte withDriver;
	private double priceRent;
	private String addressNotMember;
	private String phoneNumberNotMember;
	private Date startDate;
	private Date endDate;
	private Date createDate;

	public Rent() {
	}

	public Rent(String rentSearch, String status, byte withDriver, double priceRent, Date startDate, Date endDate,
			Date createDate) {
		this.rentSearch = rentSearch;
		this.status = status;
		this.withDriver = withDriver;
		this.priceRent = priceRent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createDate = createDate;
	}

	public Rent(Car car, User user, String rentSearch, String status, Integer refId, byte withDriver, double priceRent,
			String addressNotMember, String phoneNumberNotMember, Date startDate, Date endDate, Date createDate) {
		this.car = car;
		this.user = user;
		this.rentSearch = rentSearch;
		this.status = status;
		this.refId = refId;
		this.withDriver = withDriver;
		this.priceRent = priceRent;
		this.addressNotMember = addressNotMember;
		this.phoneNumberNotMember = phoneNumberNotMember;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createDate = createDate;
	}

	public Integer getRentId() {
		return this.rentId;
	}

	public void setRentId(Integer rentId) {
		this.rentId = rentId;
	}

	public Car getCar() {
		return this.car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRentSearch() {
		return this.rentSearch;
	}

	public void setRentSearch(String rentSearch) {
		this.rentSearch = rentSearch;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRefId() {
		return this.refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	public byte getWithDriver() {
		return this.withDriver;
	}

	public void setWithDriver(byte withDriver) {
		this.withDriver = withDriver;
	}

	public double getPriceRent() {
		return this.priceRent;
	}

	public void setPriceRent(double priceRent) {
		this.priceRent = priceRent;
	}

	public String getAddressNotMember() {
		return this.addressNotMember;
	}

	public void setAddressNotMember(String addressNotMember) {
		this.addressNotMember = addressNotMember;
	}

	public String getPhoneNumberNotMember() {
		return this.phoneNumberNotMember;
	}

	public void setPhoneNumberNotMember(String phoneNumberNotMember) {
		this.phoneNumberNotMember = phoneNumberNotMember;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}