import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arturo Olmos
 * @version 2.0
 * class represents a customer of the bank
 */
public class Customer extends Person{
	private int id;
	private Checking cAcc;
	private Savings sAcc;
	private Credit crAcc;
	//keeps track of the items bought
	private HashMap<String,Integer> itemsBought;
	private double totalMoneySpent;
	private ArrayList<String> logTransactions;
	private String sessionStart;
	private String sesstionEnd;
	private double startCheckBal;
	private double startSaveBal;
	private double startCreditBal;
	private double endCheckBal;
	private double endSaveBal;
	private double endCreditBal;
	private String pin;
	private Lock cusLock;

	/**
	 * default
	 */
	public Customer(){
		this.cusLock = new ReentrantLock();
	}
	/**
	 * takes in all parameters needed to create a Customer
	 * @param fname the first name
	 * @param lname the last name
	 * @param address the address
	 * @param city the city
	 * @param state the state
	 * @param zip user zip code
	 * @param phoneNum user phone number
	 * @param dob user date of birth
	 * @param id user id
	 * @param cAcc user checking account
	 * @param sAcc user svings account
	 * @param crAcc user credit account
	 * @param pin user pin
	 */
	public Customer(String fname,String lname,String address,String city,String state,String zip,String phoneNum,String dob,int id,Checking cAcc,Savings sAcc,Credit crAcc,String pin){
		super(fname,lname,address,city,state,zip,phoneNum,dob);
		this.id = id;
		this.cAcc = cAcc;
		this.sAcc = sAcc;
		this.crAcc = crAcc;
		itemsBought = new HashMap<String,Integer>();
		logTransactions = new ArrayList<String>();
		this.pin = pin;
		this.cusLock = new ReentrantLock();
	}
	/**
	 * gets start balance when user first logs in
	 * @return starting balance for checking
	 */
	public double getStartCheckBal() {
		return startCheckBal;
	}
	/**
	 * sets start balance when user logs in
	 * @param startCheckBal the starting balance of checking
	 */
	public void setStartCheckBal(double startCheckBal) {
		this.cusLock.lock();
		this.startCheckBal = startCheckBal;
		this.cusLock.unlock();
	}
	/**
	 * gets start balance when user logs in
	 * @return the start vbalancve for the savings
	 */
	public double getStartSaveBal() {
		return startSaveBal;
	}
	/**
	 * sets start balance when user logs in
	 * @param startSaveBal the starting balance of the savings account
	 */
	public void setStartSaveBal(double startSaveBal) {
		this.cusLock.lock();
		this.startSaveBal = startSaveBal;
		this.cusLock.unlock();
	}
	/**
	 * gets start balance when user logs in
	 * @return the starting credit balance
	 */
	public double getStartCreditBal() {
		return startCreditBal;
	}
	/**
	 * sets start balance when user logs in
	 * @param startCreditBal the stating credit balance
	 */
	public void setStartCreditBal(double startCreditBal) {
		this.cusLock.lock();
		this.startCreditBal = startCreditBal;
		this.cusLock.unlock();
	}
	/**
	 * sets the id
	 * @param id sets id attribute
	 */
	public void setID(int id){
		this.cusLock.lock();
		this.id = id;
		this.cusLock.unlock();
	}
	/**
	 * sets the checking account
	 * @param c sets Checking attribute
	 */
	public void setCheck(Checking c){
		this.cusLock.lock();
		this.cAcc = c;
		this.cusLock.unlock();
	}
	/**
	 * sets the savings account
	 * @param s sets Savings attribute
	 */
	public void setSave(Savings s){
		this.cusLock.lock();
		this.sAcc = s;
		this.cusLock.unlock();
	}
	/**
	 * sets the credit account
	 * @param cr sets the Credit attribute
	 */
	public void setCredit(Credit cr){
		this.cusLock.lock();
		this.crAcc = cr;
		this.cusLock.lock();
	}
	/**
	 * gets the id
	 * @return returns the id attribute
	 */
	public int getID(){
		return this.id;
	}
	/**
	 * gets a reference to the checking object
	 * @return returns a reference to the Checking attribute
	 */
	public Checking getCheck(){
		return this.cAcc;
	}
	/**
	 * gets reference to the credit object
	 * @return returns reference to the Savings attribute
	 */
	public Savings getSave(){
		return this.sAcc;
	}
	/**
	 * gets reference to the savings object
	 * @return returns reference of the Credit attribute
	 */
	public Credit getCredit(){
		return this.crAcc;
	}
	/**
	 * override method from Object, returns a string based on the Customer
	 * @return returns a formatted String containing the customers information
	 */
	public String toString(){
		String temp = super.toString() +  "\nID: " + this.id + "\nPin: " + this.pin + "\nChecking Account Number: " + this.cAcc.getAccNum() + String.format("   Current Checking Account Balance: %.2f$",this.cAcc.getBalance());
		temp += "\nSavings Account Number: " + this.sAcc.getAccNum() + String.format("   Current Savings Account Balance: %.2f$",this.sAcc.getBalance());
		temp += "\nCredit Account Number: " + this.crAcc.getAccNum() + String.format("   Current Credit Account Balance: %.2f$",this.crAcc.getBalance()) + "   Credit Score: " + this.crAcc.getScore() + "   Credit Limit: " + this.crAcc.getLimit();
		return temp;
	}
	/**
	 * keeps track of the purchases made by a customer
	 * @param name the name of a bought Item
	 */
	public void addItemBought(String name){//keeps track of items bought
		    this.cusLock.lock();
			//maps items and the number of purchases made
			if(itemsBought.containsKey(name)){
				int currentValue = itemsBought.get(name).intValue();
				currentValue++;
				itemsBought.remove(name,itemsBought.get(name));
				itemsBought.put(name,(Integer)currentValue);
				this.cusLock.unlock();
				return;
			}
			//for new items just add to the map
			itemsBought.put(name,(Integer)1);
			this.cusLock.unlock();
	}
	/**
	 * prints everything the customer has purchased
	 */
	public void printItemsBought(){
		if(itemsBought.size() > 0){
			Set<String> keySet = itemsBought.keySet();
			Object[] itemNameArray = keySet.toArray();
			for(int i =0;i < itemNameArray.length;i++){
				String name = (String)itemNameArray[i];
				System.out.printf("customer bought %d %s\n",itemsBought.get(name).intValue(),name);
			}
			return;
		}
		System.out.println("No items bought yet");
	}
	/**
	 * adds a string containing the name and time of a purchase
	 * @param timeBought the time of purchase of an Item
	 */
	public void addTransaction(String timeBought){
		this.cusLock.lock();
		logTransactions.add(timeBought);
		this.cusLock.unlock();
	}
	/**
	 * prints the name and time a purchase was made by the customer
	 */
	public void printTransactions(){
		if(logTransactions.size() == 0){
			System.out.println("No items bought yet");
			return;
		}
		for(int i  = 0;i < logTransactions.size();i++){
			System.out.println(logTransactions.get(i));
		}
	}
	/**
	 * sets the total money spent by a customer at miners mall
	 * @param totalMoneySpent the total money spent by customer in miners mall
	 */
	public void setTotalMoneySpent(double totalMoneySpent){
		this.cusLock.lock();
		this.totalMoneySpent = totalMoneySpent;
		this.cusLock.unlock();
	}
	/**
	 * gets total money spent at miners mall
	 * @return the total money spent
	 */
	public double getTotalMoneySpent(){
		return totalMoneySpent;
	}
	/**
	 * returns the start of the session for this user objects
	 * @return returns the start of the session as a string
	 */
	public String getSessionStart() {
		return sessionStart;
	}
	/**
	 * sets the start of the session for this user objects
	 * @param sessionStart formatted string as the time
	 */
	public void setSessionStart(String sessionStart) {
		this.cusLock.lock();
		this.sessionStart = sessionStart;
		this.cusLock.unlock();
	}
	/**
	 * return the end of the session for this user objects
	 * @return returns the time of end as a string
	 */
	public String getSesstionEnd() {
		return sesstionEnd;
	}
	/**
	 * sets the end of the session for this user objects
	 * @param sesstionEnd time when session ends
	 */
	public void setSesstionEnd(String sesstionEnd) {
		this.cusLock.lock();
		this.sesstionEnd = sesstionEnd;
		this.cusLock.unlock();
	}
	/**
	 * returns all transactions of the user
	 * @return returns list of transactions
 	 */
	public ArrayList<String> getTransactions(){
		return logTransactions;
	}
	/**
	 * gets the ending checking balance
	 * @return the ending checking balance of a session
	 */
	public double getEndCheckBal() {
		return endCheckBal;
	}
	/**
	 * sets the ending checking balance of the session
	 * @param endCheckBal sets the ending checking balance of a session
	 */
	public void setEndCheckBal(double endCheckBal) {
		this.cusLock.lock();
		this.endCheckBal = endCheckBal;
		this.cusLock.unlock();
	}
	/**
	 * gets the ending balance of the session
	 * @return the ending savings balance of a session
	 */
	public double getEndSaveBal() {
		return endSaveBal;
	}
	/**
	 * sets the endong balance of the savings for the session
	 * @param endSaveBal sets the ending savings balance of a session
	 */
	public void setEndSaveBal(double endSaveBal) {
		this.cusLock.lock();
		this.endSaveBal = endSaveBal;
		this.cusLock.unlock();
	}
	/**
	 * gets the ending balance of the session
	 * @return returns ending balance for credit
	 */
	public double getEndCreditBal() {
		return endCreditBal;
	}
	/**
	 * sets ending credit balance of the session
	 * @param endCreditBal the ending balance of credit
	 */
	public void setEndCreditBal(double endCreditBal) {
		this.cusLock.lock();
		this.endCreditBal = endCreditBal;
		this.cusLock.unlock();
	}

	/**
	 * returns the users pin
	 * @return user pin
	 */
	public String  getPin() {
		return pin;
	}
	/**
	 * sets pin of the user
	 * @param pin users pin
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
}