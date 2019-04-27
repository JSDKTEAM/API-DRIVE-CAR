package th.ac.ku.kps.eng.cpe.soa.driveCar.request;

public class SearchCar {
	private String start_date;
	
	private String end_date;
	
	private int province_id;

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}
	
	
}
