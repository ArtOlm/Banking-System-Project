/**
 * @author Jaehyeon Park
 * abstarct class, serves as the template for Cutomer
 */
public abstract class Person {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String phoneNum;
	private String DOB;

	/**
	 * default constructor
	 */
	public Person() {
	}

	/**
	 * Constructor - sets all the attributes of the Person
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param address the address of the person
	 * @param city the city where person lives
	 * @param state state where person lives
	 * @param zipCode zip code of the person
	 * @param phoneNum phone number on the person
	 * @param DOB date of birth of the perosn
	 */
	public Person(String firstName, String lastName, String address, String city, String state, String  zipCode, String phoneNum, String DOB) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNum = phoneNum;
		this.DOB = DOB;
	}

	/**
	 * sets the first name
	 * @param firstName user firtname
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * sets the last name
	 * @param lastName user last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * set the address
	 * @param address user address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * sets the city
	 * @param city user city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * sets the state
	 * @param state user state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * sets the zip code
	 * @param zipCode user zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * sets the phone number
	 * @param phoneNum user phone number
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * sets the date of birth
	 * @param DOB user date of birth
	 */
	public void setDOB(String DOB) {
		this.DOB = DOB;
	}

	/**
	 * gets the first name
	 * @return returns user first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * gets the last name
	 * @return returns users last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * get the address
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * gets the city
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * gets the state
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * ges the zip code
	 * @return the zip code
	 */
	public String getZipCode() {
		return this.zipCode;
	}

	/**
	 * gets the phone number
	 * @return the phone number
	 */
	public String getPhoneNum() {
		return this.phoneNum;
	}

	/**
	 * gets the date of birth
	 * @return the date of birth
	 */
	public String getDOB() {
		return this.DOB;
	}

	/**
	 * returns a formatted string based on the persons information
	 * @return persons information
	 */
	public String toString(){
		return ("Name: " + this.firstName + " " + this.lastName + "\nAddress: " + this.address + ", " + this.city + ", " + this.state + " " + this.zipCode + "\nPhone: " + this.phoneNum + "\nDOB: " + this.DOB);
	}
}