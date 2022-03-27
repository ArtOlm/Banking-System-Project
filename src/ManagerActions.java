

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Arturo
 * @version 2.0
 * class handles all the manager actions
 *
 */
public class ManagerActions{
	private Transactions transactionHandler;
	private Utilities handler;
	private DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private static ManagerActions manager = new ManagerActions();

	private ManagerActions(){
		handler = Utilities.getInstance();
		transactionHandler = Transactions.getInstance();
	}

	/**
	 * gets the only instance of this class
	 * @return
	 */
	public static ManagerActions getInstance(){
		return manager;
	}

	/**
	 * generates the bank statement
	 * @param cus customer whose transaction will be written
	 * @return
	 */
	public String generateBankStatement(Customer cus){
		String statement = cus.toString() + "\n----------------------------------------------------------------------\n";

		if(cus.getSessionStart() != null){
			statement += "\nSession Start: " + cus.getSessionStart() + "\n";
			statement += String.format("Starting Checking Balance: %.2f$\n",cus.getStartCheckBal());
			statement += String.format("Starting Savings Balance: %.2f$\n",cus.getStartSaveBal());
			statement += String.format("Starting Credit Balance: %.2f$\n",cus.getStartCreditBal());
			statement += "\n----------------------------------------------------------------------\n";
			statement += "Session End: " + cus.getSesstionEnd();
			statement += String.format("\nEnding Checking Balance: %.2f$\n",cus.getEndCheckBal());
			statement += String.format("Ending Savings Balance: %.2f$\n",cus.getEndSaveBal());
			statement += String.format("Ending Credit Balance: %.2f$\n",cus.getEndCreditBal());

			statement += "\n----------------------------------------------------------------------\n";
			statement += "Account Activity\n----------------\n";
			for(int i = 0;i < cus.getTransactions().size();i++){
				statement += cus.getTransactions().get(i) + "\n";
			}
			statement += "\n----------------------------------------------------------------------\n";
		}
		else {
			statement += "\n No session started yet";
		}
		return statement;


	}

	/**
	 * executes the transactions in actions.csv file
	 * @param customers all customers in the system
	 * @param items all items in the system
	 * @throws FileNotFoundException
	 */
	public void execTransactions(HashMap<String,Customer> customers,HashMap<Integer,Item> items){
		ArrayList<String[]> transactions = handler.loadTransactions();
		for(int i = 0;i < transactions.size();i++){
			Object[] transaction = transactions.get(i);
			//generate key based on the name
			String key = handler.generateKey(transaction[0].toString(),transaction[1].toString());
			//check if the customer exists within the hashmap
			//and check that action is not a deposit
			//get the customer
			Customer cus = null;
			if(customers.containsKey(key)){
				 cus = customers.get(key);
			}
			//check if customer was set
			if(cus != null){
				//set a session start if it was previously null
				if(cus.getSessionStart() == null){
					cus.setSessionStart(time.format(LocalDateTime.now()));
					cus.setStartCheckBal(cus.getCheck().getBalance());
					cus.setStartSaveBal(cus.getSave().getBalance());
					cus.setStartCreditBal(cus.getCredit().getBalance());
				}
			}
			if(transaction[3].toString().equals("Buy")){
				//do the buy item procedure
				buyProcedure(cus,items,transaction);
			}
			else if(transaction[3].toString().equals("Pay")){
				//look at the pay procedure to see how it is handled
				payProcedure(cus,customers,transaction);
			}
			else if(transaction[3].toString().equals("Deposit")){
					depositProcedure(customers,transaction);
			}
			else{
				System.out.println("No Action found");
			}
			//set a session end of the customer
			//as well as the ending balance
			if(cus != null) {
				cus.setSesstionEnd(time.format(LocalDateTime.now()));
				cus.setEndCheckBal(cus.getCheck().getBalance());
				cus.setEndSaveBal(cus.getSave().getBalance());
				cus.setEndCreditBal(cus.getCredit().getBalance());
			}
		}
	}
	/**
	 * helper function that does the procedure of a deposit
	 * @param customers list of customers
	 * @param transaction transaction being executed
	 */
	private void depositProcedure(HashMap<String,Customer> customers,Object[] transaction){
		//get the customer who will have money deposited to their account
		String key  = handler.generateKey(transaction[4].toString(),transaction[5].toString());
		//check if customer exists
		if(!customers.containsKey(key)){
			System.out.println("No customer exist");
			return;
		}
		//get the customer if they exist
		Customer depositDestination = customers.get(key);
		//set start time and balances

		if (depositDestination.getSessionStart() == null){
			depositDestination.setSessionStart(time.format(LocalDateTime.now()));
			depositDestination.setStartCheckBal(depositDestination.getCheck().getBalance());
			depositDestination.setStartSaveBal(depositDestination.getSave().getBalance());
			depositDestination.setStartCreditBal(depositDestination.getCredit().getBalance());
		}
		//try the deposit
		try{
			transactionHandler.userDeposit(depositDestination,transaction[6].toString(),Double.parseDouble(transaction[7].toString()));
		}catch (TransactionException eDep){
			System.out.println(eDep.getMessage());
			return;
		}
		//if everything goes log happens
		String fString = String.format("%s %s deposited %.2f$ from their %s account at %s\n",depositDestination.getFName(),depositDestination.getLName(),Double.parseDouble(transaction[7].toString()),transaction[6].toString(),time.format(LocalDateTime.now()));
		depositDestination.addTransaction(fString);
		handler.logToFile(fString);
		//set the end of the session
		depositDestination.setSesstionEnd(time.format(LocalDateTime.now()));
	}
	/**
	 * helper method does the pay procedure
	 * @param cus the customer who will pay
	 * @param customers map of customers
	 * @param transaction array holding information about the transaction
	 */
	private void payProcedure(Customer cus,HashMap<String,Customer> customers,Object[] transaction){
		String key  = handler.generateKey(transaction[4].toString(),transaction[5].toString());
		//check if customer exists
		if(!customers.containsKey(key)){
			System.out.println("No customer exist");
			return;
		}
		//get the customer if they exist
		Customer customerToPay = customers.get(key);
		//see if the transaction was successful
		//if not then just return from the method
		try{
			transactionHandler.transactionToOther(cus,customerToPay,transaction[2].toString(),transaction[6].toString(),Double.parseDouble(transaction[7].toString()));
		}catch (TransactionException ePay){
			System.out.println(ePay.getMessage());
			return;
		}
		//at this point everything went well so just log everything
		//formatted string to be logged
		String fString = String.format("%s %s paid %.2f$ from their %s account to %s %s into their %s account at %s\n",cus.getFName(),cus.getLName(),Double.parseDouble(transaction[7].toString()),transaction[2].toString(),customerToPay.getFName(),customerToPay.getLName(),transaction[6].toString(),time.format(LocalDateTime.now()));
		handler.logToFile(fString);
		//log transaction to customer
		cus.addTransaction(fString);
	}
	/**
	 * helper function deals with the buy transaction
	 * @param cus customer who will buy
	 * @param items map of items
	 * @param transaction information about the transaction
	 */
	private void buyProcedure(Customer cus,HashMap<Integer,Item> items,Object[] transaction){
		//check if the item exists within the hash map
		if(!items.containsKey(Integer.parseInt(transaction[8].toString()))){
			System.out.println("Error: no item with index found");
			return;
		}
		//get the item based on the file
		Item item = items.get(Integer.parseInt(transaction[8].toString()));
		//ensure the description of the item matches the id
		if(!handler.strNWS(item.getName()).equalsIgnoreCase(handler.strNWS(transaction[9].toString()))){
			System.out.println("Error: item id and description do not match");
			return;
		}
		//do the transaction process and check if it was successful, if it was not then continue
		//the buy method will print the error
		String accType = transaction[2].toString();
		// the method updates the customer info
		try{
			transactionHandler.buyFromMinerMall(cus,accType,item.getPrice());
		}catch (TransactionException eBuy){
			System.out.println(eBuy.getMessage());
			return;
		}

		//at this point everything went well so i then just proceed log everything
		String fString = String.format("%s %s purchased a %s for %.2f$ from miners bank using their %s account at %s\n", cus.getFName(), cus.getLName(), item.getName(), item.getPrice(), transaction[2].toString(), time.format(LocalDateTime.now()));
		//updates transactions made by the user
		cus.addTransaction(fString);
		//update the total money spent
		cus.setTotalMoneySpent(cus.getTotalMoneySpent() + item.getPrice());
		//update items
		cus.addItemBought(item.getName());
		//log to file
		handler.logToFile(fString);

		return;
	}
	//method managerInquire is overloaded
	//this one below handles id manager is looking by name
	/**
	 * method handles inquire if an account by name
	 * @param name the name used for searching
	 * @param customers the HashMap containing the Customer objects
	 */
	public void managerInquire(String name, HashMap<String,Customer> customers){
		System.out.println("The following accounts where found given the name");
		System.out.println("-------------------------------------------------");

		if(name.length() <= 0){
			System.out.println("Error: not enough info provided");
			return;
		}
		int count = 0;
		//goes through HashMap comparing names
		Set<String> kS = customers.keySet();
		Object[] allKeys = kS.toArray();
		for(int i = 0;i < allKeys.length;i++){
			Customer cus = customers.get(allKeys[i].toString());
			//compare names without any white spaces
			if(handler.strNWS(name).equalsIgnoreCase(handler.strNWS(cus.getFName(),cus.getLName()))){
				//ensure the name is not split somewhere in the string
				if(name.split("\\s+").length != (cus.getFName() + " " + cus.getLName()).split("\\s+").length){
					System.out.println("no users found");
					return;
				}
				//Check Customer toString method
				// in general everything abut a Customer is printed
				System.out.println(cus);
				System.out.println("Items bought");
				System.out.println("-------------------------");
				cus.printItemsBought();
				System.out.println("Time of purchase and item purchased ");
				System.out.println("-------------------------");
				cus.printTransactions();
				System.out.println("Total Money Spent at Miners Mall");
				System.out.println("---------------------------------");
				System.out.printf("%.2f$\n",cus.getTotalMoneySpent());

				count++;
			}
		}
		if(count == 0){//if count is 0 then no users were found
			System.out.println("No user with the provided name found");
		}
	}
	/**
	 * method handles inquire if an account by account type and number
	 * @param type integer that describes account type
	 * @param number the account number used for searching
	 * @param customers HashMap of Customer objects
	 */
	//this one below handles id manager is looking by account
	public void managerInquire(int type,String number,HashMap<String,Customer> customers){
		System.out.println("The following accounts where found given the name");
		System.out.println("-------------------------------------------------");
		int c = 0;
		//getting all keys to search hash table
		Set<String> keys = customers.keySet();
		Object[] allKeys = keys.toArray();
		//based on the account type 1 is checking 2 is savings 3 is credit
		//based on the type method searched for the account number
		//Check Customer toString method for more detail
		// in general everything abut a Customer is printed
		if(type == 1){//this is node for checking
			for(int i = 0;i < allKeys.length;i++){
				Customer cus = customers.get(allKeys[i].toString());
				Checking ch = cus.getCheck();
				if(handler.strNWS(ch.getAccNum()).equals(handler.strNWS(number))){
					//ensure string is of proper length
					if(ch.getAccNum().split("\\s+").length != number.split("\\s+").length){
						System.out.println("no users found");
						return;
					}
					System.out.println(cus);
					System.out.println("Items bought");
					System.out.println("-------------------------");
					cus.printItemsBought();
					System.out.println("Time of purchased items");
					System.out.println("-------------------------");
					cus.printTransactions();
					System.out.println("Total Money Spent at Miners Mall");
					System.out.println("---------------------------------");
					System.out.printf("%.2f$\n",cus.getTotalMoneySpent());
					c++;
				}
			}
		}
		else if (type == 2) {//this is done for savings
			for(int i = 0;i < allKeys.length;i++){
				Customer cus = customers.get(allKeys[i].toString());
				Savings s = cus.getSave();
				//print info if number matches
				if(handler.strNWS(s.getAccNum()).equals(handler.strNWS(number))){
					//ensure string is of proper length
					if(s.getAccNum().split("\\s+").length != number.split("\\s+").length){
						System.out.println("no users found");
						return;
					}
					System.out.println(cus);
					System.out.println("Items bought");
					System.out.println("-------------------------");
					cus.printItemsBought();
					System.out.println("Time of purchased items");
					System.out.println("-------------------------");
					cus.printTransactions();
					System.out.println("Total Money Spent at Miners Mall");
					System.out.println("---------------------------------");
					System.out.printf("%.2f$\n",cus.getTotalMoneySpent());
					c++;
				}
			}
		}
		else if (type == 3) {//this is done for credit
			//search for the customer based on their info
			for(int i= 0;i < allKeys.length;i++){
				Customer cus = customers.get(allKeys[i].toString());
				//get temp customer
				Credit cr = cus.getCredit();
				//if number matches print their info
				if(handler.strNWS(cr.getAccNum()).equals(handler.strNWS(number))){
					//ensure string is of proper length
					if(cr.getAccNum().split("\\s+").length != number.split("\\s+").length){
						System.out.println("no users found");
						return;
					}
					System.out.println(cus);
					System.out.println("Items bought");
					System.out.println("-------------------------");
					cus.printItemsBought();
					System.out.println("Time of purchased items");
					System.out.println("-------------------------");
					cus.printTransactions();
					System.out.println("Total Money Spent at Miners Mall");
					System.out.println("---------------------------------");
					System.out.printf("%.2f$\n",cus.getTotalMoneySpent());
					c++;
				}
			}
		}
		if(c == 0){
			//at this point there is no user found so we let user know
			System.out.println("No user with the provided number found");
		}
	}
}