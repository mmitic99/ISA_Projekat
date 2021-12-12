package isa.FishingBookingApp.dto;

public class ClassDTO {
	
	private Long id;
    private String name;
    private Long addressId;
    private String street;
    private String number;
    private String city;
    private String postalCode;
    private String country;
    private double longitude;
    private double latitude;
    private String promotionalDescription;
    private String rulesOfConduct;
    private Long FishingInstructorId;
    private String FishingInstructorUsername;
    private double price;
    private String bio;
    private String gear;
    private float ifcanceled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Long getFishingInstructorId() {
		return FishingInstructorId;
	}
	public void setFishingInstructorId(Long fishingInstructorId) {
		FishingInstructorId = fishingInstructorId;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getPromotionalDescription() {
		return promotionalDescription;
	}
	public void setPromotionalDescription(String promotionalDescription) {
		this.promotionalDescription = promotionalDescription;
	}
	public String getRulesOfConduct() {
		return rulesOfConduct;
	}
	public void setRulesOfConduct(String rulesOfConduct) {
		this.rulesOfConduct = rulesOfConduct;
	}
	public String getFishingInstructorUsername() {
		return FishingInstructorUsername;
	}
	public void setFishingInstructorUsername(String fishingInstructorUsername) {
		FishingInstructorUsername = fishingInstructorUsername;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getGear() {
		return gear;
	}
	public void setGear(String gear) {
		this.gear = gear;
	}
	public float getIfcanceled() {
		return ifcanceled;
	}
	public void setIfcanceled(float ifcanceled) {
		this.ifcanceled = ifcanceled;
	}

}
