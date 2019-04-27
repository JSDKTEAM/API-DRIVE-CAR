package th.ac.ku.kps.eng.cpe.soa.driveCar.request;

public class UpdateStatusRent {
	
	private int rentId;
	
	private String status;

	public int getRentId() {
		return rentId;
	}

	public void setRentId(int rentId) {
		this.rentId = rentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
