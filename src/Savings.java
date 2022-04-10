/**
 * @author Jaehyeon Park
 * @version 1.0
 * class represents the savings account of a Customer
 */
public class Savings extends Account{
	/**
	 * default constructor
	 */
	public Savings(){
	}
	/**
	 *sets all the attributes of the Savings account
	 * @param accNum sets the acount number for Savings object
	 * @param bal sets the balance for the Savings object
	 */
	public Savings(String accNum,double bal){
		super(accNum,bal);
	}
}