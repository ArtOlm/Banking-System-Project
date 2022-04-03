/**
 * @author Arturo Olmos
 * @version 1.0
 * class represents the savings account of a Customer
 */
public class Savings extends Account{
	public Savings(){
		
	}

	/**
	 *
	 * @param accNum sets the account number for Savings object
	 * @param bal sets the balance for the Savings object
	 */
	public Savings(String accNum,double bal){
		super(accNum,bal);
	}
}