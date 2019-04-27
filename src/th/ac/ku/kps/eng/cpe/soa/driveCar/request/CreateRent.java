package th.ac.ku.kps.eng.cpe.soa.driveCar.request;

public class CreateRent {
	private int carId;

	private int refId;
	
	private String startDate;

	private String endDate;
	
	private String addressNotMember;
	
	private String phoneNumberNotMember;

	private boolean withDriver;

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAddressNotMember() {
		return addressNotMember;
	}

	public void setAddressNotMember(String addressNotMember) {
		this.addressNotMember = addressNotMember;
	}

	public String getPhoneNumberNotMember() {
		return phoneNumberNotMember;
	}

	public void setPhoneNumberNotMember(String phoneNumberNotMember) {
		this.phoneNumberNotMember = phoneNumberNotMember;
	}

	public boolean isWithDriver() {
		return withDriver;
	}

	public void setWithDriver(boolean withDriver) {
		this.withDriver = withDriver;
	}
	
	
}
