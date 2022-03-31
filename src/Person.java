public class Person {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private int zipCode;
	private String phoneNum;
	private String DOB;

	public Person() {
	}

	public Person(String firstName, String lastName, String address, String city, String state, int zipCode, String phoneNum, String DOB) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNum = phoneNum;
		this.DOB = DOB;
	}

	//setters

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setDOB(String DOB) {
		this.DOB = DOB;
	}

	//getters

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}
	public int getZipCode() {
		return this.zipCode;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public String getDOB() {
		return this.DOB;
	}

	public String toString(){
		return ("Name: " + this.firstName + " " + this.lastName + "\nAddress: " + this.address + ", " + this.city + ", " + this.state + " " + this.zipCode + "\nPhone: " + this.phoneNum + "\nDOB: " + this.DOB);
	}
}