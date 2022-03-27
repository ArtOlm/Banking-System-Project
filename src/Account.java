/**
 * @author Arturo Olmos
 * @version 1.0
 * abstracts class of an account
 */
public abstract class Account{
	private double balance;
	private String accNum;

	//constructors
	public Account(){

	}

	/**
	 *
	 * @param num the account number
	 * @param bal sets the balance
	 */
	public Account(String num,double bal){
		this.balance = bal;
		this.accNum = num;

	}

	/**
	 *
	 * @param bal sets the balance of the account
	 * setter method
	 */
	//setters/getters
	public void setBalance(double bal){
		this.balance = bal;
	}

	/**
	 *setter method
	 * @param num sets the account number
	 */
	public void setAccNum(String num){
		this.accNum = num;
	}

	/**
	 *getter method
	 * @return returns the account balance
	 */
	public double getBalance(){
		return this.balance;
	}

	/**
	 *getter method
	 * @return returns the account number
	 */
	public String getAccNum(){
		return this.accNum;
	}
}