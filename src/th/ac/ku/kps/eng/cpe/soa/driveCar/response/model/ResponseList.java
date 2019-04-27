package th.ac.ku.kps.eng.cpe.soa.driveCar.response.model;

import java.util.List;


public class ResponseList<T> {
	private String status;

	private List<T> result;

	private String msg;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
