import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

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
	/**
	 * default
	 */
	public Customer(){
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
		this.startCheckBal = startCheckBal;
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
		this.startSaveBal = startSaveBal;
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
		this.startCreditBal = startCreditBal;
	}
	/**
	 * sets the id
	 * @param id sets id attribute
	 */
	public void setID(int id){
		this.id = id;
	}
	/**
	 * sets the checking account
	 * @param c sets Checking attribute
	 */
	public void setCheck(Checking c){
		this.cAcc = c;
	}
	/**
	 * sets the savings account
	 * @param s sets Savings attribute
	 */
	public void setSave(Savings s){
		this.sAcc = s;
	}
	/**
	 * sets the credit account
	 * @param cr sets the Credit attribute
	 */
	public void setCredit(Credit cr){
		this.crAcc = cr;
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
		String temp = super.toString() +  "\nID: " + this.id + "\nPin: " + this.pin + "\nChecking Account Number: " + this.cAcc.getAccNum() + String.format("   Current Checking Account Balance: $%.2f",this.cAcc.getBalance());
		temp += "\nSavings Account Number: " + this.sAcc.getAccNum() + String.format("   Current Savings Account Balance: $%.2f",this.sAcc.getBalance());
		temp += "\nCredit Account Number: " + this.crAcc.getAccNum() + String.format("   Current Credit Account Balance: $%.2f",this.crAcc.getBalance()) + "   Credit Score: " + this.crAcc.getScore() + "   Credit Limit: " + this.crAcc.getLimit();
		return temp;
	}
	/**
	 * keeps track of the purchases made by a customer
	 * @param name the name of a bought Item
	 */
	public void addItemBought(String name){//keeps track of items bought
		    
			//maps items and the number of purchases made
			if(itemsBought.containsKey(name)){
				int currentValue = itemsBought.get(name).intValue();
				currentValue++;
				itemsBought.remove(name,itemsBought.get(name));
				itemsBought.put(name,(Integer)currentValue);
				
				return;
			}
			//for new items just add to the map
			itemsBought.put(name,(Integer)1);
	}
	/**
	 * prints everything the customer has purchased
	 * @return returns a string of all items bought
	 */
	public String getAllItemsBought(){
		if(itemsBought.size() > 0){
			String allItems = "";
			Set<String> keySet = itemsBought.keySet();
			Object[] itemNameArray = keySet.toArray();
			for(int i =0;i < itemNameArray.length;i++){
				String name = (String)itemNameArray[i];
				allItems += String.format("customer bought %d %s\n",itemsBought.get(name).intValue(),name);
			}
			return allItems;
		}
		return "No items bought yet";
	}
	/**
	 * adds a string containing the name and time of a purchase
	 * @param timeBought the time of purchase of an Item
	 */
	public void addTransaction(String timeBought){
		logTransactions.add(timeBought);
	}
	/**
	 * prints the name and time a purchase was made by the customer
	 * @return returns a string containing all transactions
	 */
	public String getAllTransactions(){
		if(logTransactions.size() == 0){
			return "No items bought yet";
		}
		String transactions = "";
		for(int i  = 0;i < logTransactions.size();i++){
			transactions += logTransactions.get(i) + "\n";
		}
		return transactions;
	}
	/**
	 * sets the total money spent by a customer at miners mall
	 * @param totalMoneySpent the total money spent by customer in miners mall
	 */
	public void setTotalMoneySpent(double totalMoneySpent){
		this.totalMoneySpent = totalMoneySpent;
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
		this.sessionStart = sessionStart;
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
		this.sesstionEnd = sesstionEnd;
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
		this.endCheckBal = endCheckBal;
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
		this.endSaveBal = endSaveBal;
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
		this.endCreditBal = endCreditBal;
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