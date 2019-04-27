package th.ac.ku.kps.eng.cpe.soa.driveCar.request;

import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Province;

public class UpdateCar {

	private String licensePlate;

	private int provinceByProvinceId;

	private int provinceByAddressProvince;

	private int seatCount;

	private String typeCar;

	private String brand;

	private double price;

	private int discount;

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getProvinceByProvinceId() {
		return provinceByProvinceId;
	}

	public void setProvinceByProvinceId(int provinceByProvinceId) {
		this.provinceByProvinceId = provinceByProvinceId;
	}

	public int getProvinceByAddressProvince() {
		return provinceByAddressProvince;
	}

	public void setProvinceByAddressProvince(int provinceByAddressProvince) {
		this.provinceByAddressProvince = provinceByAddressProvince;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public String getTypeCar() {
		return typeCar;
	}

	public void setTypeCar(String typeCar) {
		this.typeCar = typeCar;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}
