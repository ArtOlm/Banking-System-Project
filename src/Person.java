/**
 * @author Arturo Olmos
 * @version 1.0
 * abstract class represents a person
 */
public abstract class Person{
	private String fname;
	private String lname;
	private String address;
	private String state;
	private String city;
	private String zip;
	private String phoneNum;
	private String dob;
	//costructors
	public Person(){

	}

	public Person(String fname,String lname,String address,String city,String state,String zip,String phoneNum,String dob){
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.state = state;
		this.zip = zip;
		this.phoneNum = phoneNum;
		this.dob = dob;
		this.city = city;

	}
	//set/get methods below

	/**
	 *
	 * @param fname sets first name attribute
	 */
	public void setFName(String fname){
		this.fname = fname;
	}

	/**
	 *
	 * @param lname sets last name attribute
	 */
	public void setLName(String lname){
		this.lname = lname;
	}

	/**
	 *
	 * @param add sets address attribute
	 */
	public void setAddress(String add){
		this.address = add;
	}

	/**
	 *
	 * @param st sets state attribute
	 */
	public void setState(String st){
		this.state = st;
	}

	/**
	 *
	 * @param zip sets zip attribute
	 */
	public void setZip(String zip){
		this.zip = zip;
	}

	/**
	 *
	 * @param phone sets phone attribute
	 */
	public void setPhone(String phone){
		this.phoneNum = phone;
	}

	/**
	 *
	 * @param dob sets date of birth attribute
	 */
	public void setDOB(String dob){
		this.dob = dob;
	}

	/**
	 *
	 * @return returns the first name attribute
	 */
	public String getFName(){
		return this.fname;
	}

	/**
	 *
	 * @return last name attribute
	 */
	public String getLName(){
		return this.lname;
	}

	/**
	 *
	 * @return returns address attribute
	 */
	public String getAddress(){
		return this.address;
	}

	/**
	 *
	 * @return returns state attribute
	 */
	public String getState(){
		return this.state;
	}

	/**
	 *
	 * @return returns zip attribute
	 */
	public String getZip(){
		return this.zip;
	}

	/**
	 *
	 * @return returns the phone number attribute
	 */
	public String getPhone(){
		return this.phoneNum;
	}

	/**
	 *
	 * @return returns the date of birth attribute
	 */
	public String getDOB(){
		return this.dob;
	}

	/**
	 *
	 * @return returns the city attribute
	 */
	public String getCity(){
		return this.city;
	}

	/**
	 *
	 * @param city sets the city attribute
	 */
	public void setCity(String city){
		this.city = city;
	}

	/**
	 *
	 * @return returns a formatted String containing all the Person information
	 */
	public String toString(){
		return ("Name: " + this.fname + " " + this.lname + "\nAddress: " + this.address + ", " + this.city + ", " + this.state + " " + this.zip + "\nPhone: " + this.phoneNum + "\nDOB: " + this.dob);
	}
}