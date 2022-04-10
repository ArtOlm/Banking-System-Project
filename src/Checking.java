/**
 * @author Jaehyeon Park
 * @version 1.0
 * checking account of a Customer
 */
public class Checking extends Account{
	/**
	 * default constructor
	 */
	public Checking(){
		
	}

	/**
	 *Constructor-sets all the attributes of the object
	 * @param accNum the account number of the Checking account
	 * @param bal the balance of the Checking account
	 */
	public Checking(String accNum,double bal){
		super(accNum,bal);
	}	
}