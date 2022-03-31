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
	private int pin;
	public Customer(){}
	/**
	 * @param fname
	 * @param lname
	 * @param address
	 * @param city
	 * @param state
	 * @param zip
	 * @param phoneNum
	 * @param dob
	 * @param id
	 * @param cAcc
	 * @param sAcc
	 * @param crAcc
	 * @param pin
	 * takes in all parameters needed to create a cusotmer
	 */
	public Customer(String fname,String lname,String address,String city,String state,int zip,String phoneNum,String dob,int id,Checking cAcc,Savings sAcc,Credit crAcc,int pin){
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
	 * @return
	 * gets start balance when user first logs in
	 */
	public double getStartCheckBal() {
		return startCheckBal;
	}
	/**
	 * sets start balance when user logs in
	 * @param startCheckBal
	 */
	public void setStartCheckBal(double startCheckBal) {
		this.startCheckBal = startCheckBal;
	}
	/**
	 * gets start balance when user logs in
	 * @return
	 */
	public double getStartSaveBal() {
		return startSaveBal;
	}
	/**
	 * sets start balance when user logs in
	 * @param startSaveBal
	 */
	public void setStartSaveBal(double startSaveBal) {
		this.startSaveBal = startSaveBal;
	}
	/**
	 * gets start balance when user logs in
	 * @return
	 */
	public double getStartCreditBal() {
		return startCreditBal;
	}
	/**
	 * sets start balance when user logs in
	 * @param startCreditBal
	 */
	public void setStartCreditBal(double startCreditBal) {
		this.startCreditBal = startCreditBal;
	}
	/**
	 * @param id sets id attribute
	 */
	public void setID(int id){
		this.id = id;
	}

	/**
	 * @param c sets Checking attribute
	 */
	public void setCheck(Checking c){
		this.cAcc = c;
	}

	/**
	 * @param s sets Savings attribute
	 */
	public void setSave(Savings s){
		this.sAcc = s;
	}

	/**
	 * @param cr sets the Credit attribute
	 */
	public void setCredit(Credit cr){
		this.crAcc = cr;
	}

	/**
	 * @return returns the id attribute
	 */
	public int getID(){
		return this.id;
	}

	/**
	 * @return returns a reference to the Checking attribute
	 */
	public Checking getCheck(){
		return this.cAcc;
	}

	/**
	 * @return returns reference to the Savings attribute
	 */
	public Savings getSave(){
		return this.sAcc;
	}

	/**
	 * @return returns reference of the Credit attribute
	 */
	public Credit getCredit(){
		return this.crAcc;
	}

	/**
	 * @return returns a formatted String containing the customers information
	 * override method from Object
	 */

	public String toString(){
		String temp = super.toString() +  "\nID: " + id + "\nChecking Account Number: " + cAcc.getAccNum() + String.format("   Current Checking Account Balance: %.2f$",cAcc.getBalance());
		temp += "\nSavings Account Number: " + sAcc.getAccNum() + String.format("   Current Savings Account Balance: %.2f$",sAcc.getBalance());
		temp += "\nCredit Account Number: " + crAcc.getAccNum() + String.format("   Current Credit Account Balance: %.2f$",crAcc.getBalance()) + "   Credit Score: " + crAcc.getScore() + "   Credit Limit: " + crAcc.getLimit();
		return temp;
	}
	/**
	 * @param name the name of a bought Item
	 * keeps track of the purchases made by a customer
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
	 * @param timeBought the time of purchase of an Item
	 * adds a string containing the name and time of a purchase
	 */
	public void addTransaction(String timeBought){
		logTransactions.add(timeBought);
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
	 * @param totalMoneySpent the total money spent by customer in miners mall
	 * sets the total money spent by a customer
	 */
	public void setTotalMoneySpent(double totalMoneySpent){
		this.totalMoneySpent = totalMoneySpent;
	}
	/**
	 * @return the total money spent
	 */
	public double getTotalMoneySpent(){
		return totalMoneySpent;
	}
	/**
	 * @return
	 * returns the start of the session for this user objects
	 */
	public String getSessionStart() {
		return sessionStart;
	}
	/**
	 * @param sessionStart
	 * zets the start of the session for this user objects
	 */
	public void setSessionStart(String sessionStart) {
		this.sessionStart = sessionStart;
	}
	/**
	 * @return
	 * return the end of the seddion for this user objects
	 */
	public String getSesstionEnd() {
		return sesstionEnd;
	}
	/**
	 *
	 * @param sesstionEnd
	 * sets the end of the session for this user objects
	 */
	public void setSesstionEnd(String sesstionEnd) {
		this.sesstionEnd = sesstionEnd;
	}
	/**
	 * returns all transactions of the user
	 * @return
	 */
	public ArrayList<String> getTransactions(){
		return logTransactions;
	}

	/**
	 * gets the ending checking balance
	 * @return
	 */
	public double getEndCheckBal() {
		return endCheckBal;
	}

	/**
	 * sets the ending checking balance of the session
	 * @param endCheckBal
	 */
	public void setEndCheckBal(double endCheckBal) {
		this.endCheckBal = endCheckBal;
	}

	/**
	 * gets the ending balance of the session
	 * @return
	 */
	public double getEndSaveBal() {
		return endSaveBal;
	}

	/**
	 * sets the endong balance of the savings for the session
	 * @param endSaveBal
	 */
	public void setEndSaveBal(double endSaveBal) {
		this.endSaveBal = endSaveBal;
	}

	/**
	 * gets the ending balance of the session
	 * @return
	 */
	public double getEndCreditBal() {
		return endCreditBal;
	}

	/**
	 * sets ending credit balance of the session
	 * @param endCreditBal
	 */
	public void setEndCreditBal(double endCreditBal) {
		this.endCreditBal = endCreditBal;
	}
	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
}