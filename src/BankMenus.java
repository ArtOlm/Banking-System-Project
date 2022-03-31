import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author Arturo Olmos
 * @version 2.0
 * this class provides two methods that portray different menus based on the user
 */
public class BankMenus{
	//handles transactions
	private Transactions transactionHandler;
	//has useful methods which are relatied to the file infomation
	private Utilities myHandler;
	//manager action handler
	private ManagerActions manHandler;
	//scanner objects used to take user input
	private Scanner userInput;
	//map containing the customers
	private HashMap<String,Customer> customers;
	//map containing the items
	private HashMap<Integer,Item> items;

	//string pointer user to log the transaction
	private String transactionLog = "";
	//format to the date
	private DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public BankMenus(Scanner userInput,HashMap<String,Customer> customers,HashMap<Integer,Item> items){
		this.transactionHandler = Transactions.getInstance();
		this.myHandler = Utilities.getInstance();
		this.manHandler = ManagerActions.getInstance();
		this.userInput = userInput;
		this.customers = customers;
		this.items = items;
	}

	/**
	 * deals with the user creation
	 */
	public void creationMenu(){
		//create hashmaps to check all the pins
		Set<Integer> pins = new HashSet<Integer>();
		Set<String> keys = this.customers.keySet();
		Object[] ks = keys.toArray();
		for(int i = 0;i < ks.length;i++){
			Customer cus =  this.customers.get(ks[i].toString());
			pins.add(cus.getPin());
		}
		//get the user information needed to create account
		System.out.println("Please enter the following information");
		System.out.println("First Name: ");
		String fName = userInput.nextLine();
		System.out.println("Last Name");
		String lName = userInput.nextLine();
		System.out.println("Date of Birth");
		String dob = userInput.nextLine();
		System.out.println("Address");
		String add = userInput.nextLine();
		System.out.println("City");
		String city = userInput.nextLine();
		System.out.println("State");
		String state = userInput.nextLine();
		System.out.println("Zip");
		String zip = userInput.nextLine();
		System.out.println("Phone Number");
		String phoneNum = userInput.nextLine();
		System.out.println("Checking deposit");
		double chDepsoit = 0;
		//ensure a proper deposit for all customers
		while(true){
			try{
				chDepsoit = Double.parseDouble(userInput.nextLine());
			}
			catch (Exception e){
				System.out.println("Please enter a proper balance to deposit");
				continue;
			}
			if(chDepsoit < 0){
				System.out.println("Please enter a proper balance to deposit");
				continue;
			}
			break;
		}
		System.out.println("Savings Deposit");
		double saveDeposit = 0;
		while(true){
			try{
				saveDeposit = Double.parseDouble(userInput.nextLine());
			}
			catch (Exception e){
				System.out.println("Please enter a proper balance to deposit");
				continue;
			}
			if(saveDeposit < 0){
				System.out.println("Please enter a proper balance to deposit");
				continue;
			}
			break;
		}
		System.out.println("Credit score");
		int score = 0;
		while(true){
			try{
				score = Integer.parseInt(userInput.nextLine());
			}
			catch (Exception e){
				System.out.println("Please enter a proper integer");
				continue;
			}
			break;
		}

		int id = myHandler.getMaxCustomerIDX();
		int checkNum = id + 1000000;
		int saveNum = id + 2000000;
		int creditNum = id + 3000000;
		int generatedLimit = getCreditLimit(score);
		Checking ch = new Checking("" + checkNum,chDepsoit);
		Savings save = new Savings("" + saveNum,saveDeposit);
		Credit cr = new Credit("" + creditNum,0,generatedLimit,score);
		//generate key for user
		String key = myHandler.generateKey(fName,lName);
		//generate random pin
		int pin = generatePin(pins);
		Customer cus = new Customer(fName,lName,add,city,state,Integer.parseInt(zip),phoneNum,dob,id,ch,save,cr,pin);
		customers.put(key,cus);
		//add 1 to last max id for next user created
		myHandler.incrementMaxCustomerIDX();
		//let the user know of their credentials
		System.out.println("-------------------------------------------------");
		System.out.println("The account was successfully created!");
		System.out.println("Note: The following credential are important to you login keep safe");
		System.out.println("Your ID is: " + id);
		System.out.println("Your Pin is: " + pin);
		System.out.println("Your Checking Number is: " + checkNum);
		System.out.println("Your Savings Number is: " + saveNum);
		System.out.println("Your Credit Number is: " + creditNum);
	}

	/**
	 * generates a pin not found in the csv file
	 * @param pins
	 * @return
	 */
	private int generatePin(Set<Integer> pins){
		Random rand = new Random();
		int pin = rand.nextInt(9000) + 1000;
		//ensure pin is unique
		while(pins.contains(pin)){
			pin = rand.nextInt(9000) + 1000;;
		}
		return pin;
	}
	/**
	 *
	 * @param score
	 * @return return limit
	 * returns the limit based on the score
	 */
	private int getCreditLimit(int score){
		Random rand = new Random();
		//generate the limit based on their score
		if(score <= 580){
			int min = 100;
			int max = 699;

			int randomLimit = rand.nextInt((max - min) + 1) + min;
			return randomLimit;
		}
		else if(score >= 581 && score <= 669){
			int min = 700;
			int max = 4999;

			int randomLimit = rand.nextInt((max - min) + 1) + min;
			return randomLimit;
		}
		else if(score >= 670 && score <= 739){
			int min = 5000;
			int max = 7499;

			int randomLimit = rand.nextInt((max - min) + 1) + min;
			return randomLimit;
		}
		else if(score >= 740 && score <= 799){
			int min = 7500;
			int max = 15999;

			int randomLimit = rand.nextInt((max - min) + 1) + min;
			return randomLimit;
		}
		else if(score >= 800){
			int min = 16000;
			int max = 25000;

			int randomLimit = rand.nextInt((max - min) + 1) + min;
			return randomLimit;
		}
		//at this point something went wrong
		return 0;
	}
	/**
	 * this helps handle the manager interface
	 */
	 public void managerMenu(){
		System.out.println("Hello Manager what would you like to do?(Enter:1-5)");
		//option used for action
		int moption = -1;
		
		while(true){
			System.out.println("1.Inquire customer by name");
			System.out.println("2.Inquire customer by account type and number");
			System.out.println("3.Execute transactions");
			System.out.println("4.Create Bank Statement");
			System.out.println("5.Logout");
			//ensure a proper action happens
			try{
				moption = Integer.parseInt(userInput.nextLine());
			}
			catch(Exception e){
				System.out.println("error enter an appropiate integer(1-3)");
				continue;
			}
			switch(moption){
			//search by name
			case 1:
				System.out.println("-------------------------------------------------");
				System.out.println("Enter a name: ");

				manHandler.managerInquire(userInput.nextLine(),customers);
				System.out.println("-------------------------------------------------");
				break;
			//search by account
			case 2:
				System.out.println("-------------------------------------------------");
				System.out.println("Which type of account are you looking for?(1-3)");
				System.out.println("1.Checking");
				System.out.println("2.Savings");
				System.out.println("3.Credit");
				int op = -1;
				boolean outerloop = true;
				//ensure correct input happens
				while(outerloop && (op < 1 || op > 3)){
					while(true){
						try{
							op = Integer.parseInt(userInput.nextLine());
						}
						catch(Exception e){
							System.out.println("Choose appropriate option(1-3)");
							continue;
						}
						break;
					}
					System.out.println("Choose appropriate option(1-3)");
				}
				System.out.println("Provide the number of the account");
				String num = userInput.nextLine();
				manHandler.managerInquire(op,num,customers);
				System.out.println("-------------------------------------------------");
				break;
			//execute actions.csv file
			case 3:
				System.out.println("-------------------------------------------");
				manHandler.execTransactions(customers,items);
				System.out.println("-------------------------------------------");
				break;
			//create bank statement for a user
			case 4:
				System.out.println("-------------------------------------------");
				System.out.println("How would you like to generate the bank statement by?");
				System.out.println("1.Name");
				System.out.println("2.ID");
				int option = -1;
				while(true){
					try {
						option = Integer.parseInt(userInput.nextLine());
					}
					catch (Exception e){
						System.out.println("Please choose an appropriate option");
						continue;
					}
					if(option != 1 && option != 2){
						System.out.println("Please choose an appropriate option");
						continue;
					}
					break;
				}
				//pointers used inside the switch cases
				Customer cus;
				String statement;
				switch(option){
					//by name
					case 1:
						System.out.println("Please enter a name");
						//get name
						String name = userInput.nextLine();
						//create a key
						String key = myHandler.generateKey("",name);
						//check customer exists
						if(!customers.containsKey(key)){
							System.out.println("No user found, cannot generate bank statement");
							continue;
						}
						//generate key by name
						cus = customers.get(key);
						if(name.split("\\s+").length != (cus.getFirstName() + " " + cus.getLastName()).split("\\s+").length){
							System.out.println("no users found");
							continue;
						}
						//generate bank statement
						statement = manHandler.generateBankStatement(cus);
						if( statement != null){
							myHandler.writeBankStatement(cus,statement);
						}
						break;
					//by id
					case 2:
						System.out.println("Please enter an ID");
						int id = -1;
						//ensure id is an integer
						while(true){
							try {
								id = Integer.parseInt(userInput.nextLine());
							}
							catch (Exception e){
								System.out.println("Please choose an appropriate option");
								continue;
							}
							break;
						}
						//ensure id is in range
						if(id < 1 || id > customers.size()){
							System.out.println("ID is out of range");
							continue;
						}
						//search through the HashMap for the id
						Set<String> keys = customers.keySet();
						Object[] keyArray = keys.toArray();
						//set customer to null
						cus = null;
						for(int i = 0;i < keyArray.length;i++){
							if(id == customers.get(keyArray[i].toString()).getID()){
								cus = customers.get(keyArray[i].toString());
								break;
							}
						}
						//if the customer is not null it was found
						if(cus != null){
							statement = manHandler.generateBankStatement(cus);
							if( statement != null){//check the statement is not null
								myHandler.writeBankStatement(cus,statement);
							}
						}
						else {
							System.out.println("No user found, cannot generate bank statement");
						}
						break;
					default:
						//at this point something went gone wrong
						System.out.println("Something went wrong");
						break;
				}
				System.out.println("-------------------------------------------");
				break;
			//exit
			case 5:
				System.out.println("-------------------------------------------");
				System.out.println("Thank you manager have a great day!");
				return;
			//not a proper option chosen
			default:
				System.out.println("Error: enter an appropiate integer(1-3)");
				continue;
			}
			//ask what the manager wants to do next
			System.out.println("What else would you like to do today?");
		}
	}
	/**
	 * this helps handle the customer interface
	 */
 public void userMenu(){
		System.out.println("Welcome customer, please enter the following information to properly help you.");
	    //pointer to keys which is used to index into the hashmap
	    String key = "";
	     //ask user for their information which is how they will be identified
		boolean menuon = true;
		while(menuon){
			System.out.print("First Name: ");
			String userFirstName = userInput.nextLine();
			System.out.print("Last Name: ");
			String userLastName = userInput.nextLine();
			//getting their id helps since we stored customers with indexing 
			int userID = -1;
			System.out.print("ID: ");

			//ensure the user inputs an integer
			//user is able to type random string
			while(true){
				try{
				userID = Integer.parseInt(userInput.nextLine());
				}
				catch(Exception e){
					//tell user what they need to fix
					System.out.println("ID must be integer");
					continue;
				}
				break;
			}
			System.out.print("Checking Account Number: ");
			String userCheckNum = userInput.nextLine();
			System.out.print("Savings Account Number: ");
			String userSaveNum = userInput.nextLine();
			System.out.print("Credit Account Number: ");
			String userCreditNum = userInput.nextLine();
			// check that the user exists
			if(credentialValid(userID,userFirstName,userLastName,userCheckNum,userSaveNum,userCreditNum,customers)){
				key = myHandler.generateKey(userFirstName,userLastName);
				Customer userAccount = customers.get(key);
				//sets the time of loggin for that user
				//also set the starting balances
				if(userAccount.getSessionStart() == null){
					userAccount.setSessionStart(time.format(LocalDateTime.now()));
					userAccount.setStartCheckBal(userAccount.getCheck().getBalance());
					userAccount.setStartSaveBal(userAccount.getSave().getBalance());
					userAccount.setStartCreditBal(userAccount.getCredit().getBalance());
				}
				System.out.println("-------------------------------------------------");
				System.out.println("Hello " + userAccount.getFirstName() + " " + userAccount.getLastName() + " what would you like to do today?(Enter 1-7)");
				//stirng ponters is used later on to point to account chosen by user
				String accType;
				String from;
				String to;
				//control variable used to exit main while loop when user wants to stop running code
				boolean onOff = true;
				while(onOff){
					//menu for the user to make decision on what they want to do
					System.out.println("1.Inquire balance");
					System.out.println("2.Deposit");
					System.out.println("3.Transfer from an account to another");
					System.out.println("4.Withdraw");
					System.out.println("5.Pay another user");
					System.out.println("6.Go to Miners Mall");
					System.out.println("7.Logout");
					int option = 0;
					//ensure the user chooses and appropiate option
					while(true){
						try{
							option = Integer.parseInt(userInput.nextLine());
						}
						catch(Exception e){
							System.out.println("-------------------------------------------------");
							System.out.println("Please choose an appropiate option(1-7)");
							continue;
						}
						break;
					}
					//switch statement to do something based on decision
					switch(option){
						case 1://inquire procedure
							System.out.println("-------------------------------------------------");
							System.out.println("Enter the name of the account you would like to inquire?");
							System.out.println("1.Checking");
							System.out.println("2.Savings");
							System.out.println("3.Credit");
							accType = userInput.nextLine();
							//ensure porper account is chosen
							while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
								System.out.println("Please enter a correct option");
								accType = userInput.nextLine();
							}
							//let user know of success
							System.out.printf("%s currently has %.2f$\n", accType,transactionHandler.checkBalance(userAccount,accType));
							//log what happened
							transactionLog = String.format("%s %s inquired their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),accType,time.format(LocalDateTime.now()));
							myHandler.logToFile(transactionLog);

							System.out.println("-------------------------------------------------");
						break;
						case 2://deposit procedure
							System.out.println("-------------------------------------------------");
							System.out.println("Enter the name of the account would you like to deposit to?");
							System.out.println("1.Checking");
							System.out.println("2.Savings");
							System.out.println("3.Credit");
							accType = userInput.nextLine();
							//ensure proper account is chosen
							while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
								System.out.println("Please enter a correct option");
								accType = userInput.nextLine();
							}
							System.out.println("Please enter the amount you would like to Deposit:(DO NOT USE COMMA)");
							double deposit = 0;
							//ensure that entered value is acceptable
							while(true){
								try{
									deposit = Double.parseDouble(userInput.nextLine());
								}
								catch(Exception e){
									System.out.println("Error: not a proper value");
									continue;
								}
								break;
							}
							try {
								transactionHandler.userDeposit(userAccount,accType,deposit);
							}
							catch(TransactionException eDep){
								//Transaction was a failure
								System.out.println(eDep.getMessage());
								System.out.println("Returning to menu");
								System.out.println("-------------------------------------------------");
								System.out.println("Please choose an option");
								continue;
							}
							//if success then we tell user and log it
							transactionLog = String.format("%s %s deposited %.2f$ from their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),deposit,accType,time.format(LocalDateTime.now()));
							userAccount.addTransaction(String.format(transactionLog));
							myHandler.logToFile(transactionLog);


							System.out.printf("The deposit of %.2f$ into %s was a sucess!\n",deposit,accType);
							System.out.println("-------------------------------------------------");
						break;
						case 3://transaction between two customers of the same customer
							System.out.println("-------------------------------------------------");
							System.out.println("Please enter the name of the account you want to tranfer from");
							System.out.println("1.Checking");
							System.out.println("2.Savings");
							System.out.println("3.Credit");
						   from = userInput.nextLine();
							while(!from.equals("Checking") && !from.equals("Savings") && !from.equals("Credit")){
								System.out.println("Please enter a correct option");
								from = userInput.nextLine();

							}
							System.out.println("Please enter the name of the account you want to tranfer to");
							System.out.println("1.Checking");
							System.out.println("2.Savings");
							System.out.println("3.Credit");
							 to = userInput.nextLine();
							while(!to.equals("Checking") && !to.equals("Savings") && !to.equals("Credit")){
								System.out.println("Please enter a correct option");
								to = userInput.nextLine();

							}
							System.out.println("How much would you like to tranfer?(Do not include comma)");
							double transfer = -1;
							while(true){
								try{
									transfer = Double.parseDouble(userInput.nextLine());
								}
								catch(Exception e){
									System.out.println("Please enter a proper amount(e.g 100.05)");
									continue;
								}
								if(transfer < 0){
									System.out.println("No negative values allowed please enter a positive value");
									continue;
								}
								break;
							}
							try {
								transactionHandler.accountTransfer(userAccount,from,to,transfer);
							}
							catch (TransactionException eTransfer){
								System.out.println(eTransfer.getMessage());
								System.out.println("Returning to menu");
								System.out.println("-------------------------------------------------");
								System.out.println("Please choose an option");
								continue;
							}
							//if success then we tell user and log it
								System.out.printf("The transfer was a success, %.2f$ was transferred from %s to %s\n",transfer,from,to);
							    transactionLog = String.format("%s %s tranfered %.2f$ from %s to %s at %s\n",userAccount.getFirstName(),userAccount.getLastName(),transfer,from,to,time.format(LocalDateTime.now()));
							    userAccount.addTransaction(String.format(transactionLog));
							myHandler.logToFile(transactionLog);
						break;
						case 4://withdraw procedure
							System.out.println("-------------------------------------------------");
							System.out.println("Enter the name of the account would you like to withdraw from?");
							System.out.println("1.Checking");
							System.out.println("2.Savings");
							System.out.println("3.Credit");
							accType = userInput.nextLine();
							while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
								System.out.println("Please enter a correct option");
								accType = userInput.nextLine();

							}
							System.out.println("Please enter the amount you would like to withdraw:(DO NOT USE COMMA)");
							double withdrawl = 0;
							//ensure that entered value is acceptable
							while(true){
								try{
									withdrawl = Double.parseDouble(userInput.nextLine());
								}
								catch(Exception e){
									System.out.println("Error: not a proper value");
									continue;
								}
								break;
							}
							try{
								transactionHandler.userWithdraw(userAccount,accType,withdrawl);
							}
							catch(TransactionException eWith){
								//there was transaction failure
								System.out.println(eWith.getMessage());
								System.out.println("Returning to menu");
								System.out.println("-------------------------------------------------");
								System.out.println("Please choose an option");
								continue;
							}
							//if success then we tell user and log it
							transactionLog = String.format("%s %s withdrew %.2f$ from the %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),withdrawl,accType,time.format(LocalDateTime.now()));
							System.out.printf("The withdrawl of %.2f$ was a success\n",withdrawl);
							userAccount.addTransaction(String.format(transactionLog));
							myHandler.logToFile(transactionLog);
						break;
						case 5://pay another user procedure
							System.out.println("-------------------------------------------------");
							System.out.println("Please enter the name of the account you want to pay from");
							System.out.println("1.Checking");
							System.out.println("2.Savings");
							System.out.println("3.Credit");
							from = userInput.nextLine();
							//ensure a proper account is entered
							while(!from.equals("Checking") && !from.equals("Savings") && !from.equals("Credit")){
								System.out.println("Please enter a correct option");
								from = userInput.nextLine();

							}
							System.out.println("How much would you like to pay?(Do not include comma)");
							double pay = -1;
							//ensure proper pay is entered
							while(true){
								try{
									pay = Double.parseDouble(userInput.nextLine());
								}
								catch(Exception e){
									System.out.println("Please enter a proper amount(e.g 100.05)");
									continue;
								}
								if(pay < 0){
									System.out.println("No negative values allowed please enter a positive value");
									continue;
								}
								break;
							}
							System.out.println("Enter the following information about the user you would like to pay");
							System.out.print("First Name: ");
							String userToPayFirstName = userInput.nextLine();
							System.out.print("Last Name: ");
							String userToPayLastName = userInput.nextLine();
							int userToPayID = -1;
							System.out.print("ID: ");

							//ensure a proper id is entered
							while(true){
								try{
									userToPayID = Integer.parseInt(userInput.nextLine());
								}
								catch(Exception e){
									System.out.println("Please enter an integer");
									continue;
								}
								if(userToPayID < 0 || userToPayID > customers.size()){
									System.out.println("Not a valid ID");
								}
								break;
							}
							//check to see credentials are not to the same user
							if(credentialValid(userAccount,userToPayFirstName,userToPayLastName,userToPayID,customers)){
								System.out.println("Error: cannot pay yourself");
								System.out.println("returning to menu.....");
								System.out.println("-------------------------------------------------");
								System.out.println("Please choose an option");
								continue;
							}
							System.out.println("Please enter the name of the account you want to pay to");
							System.out.println("1.Checking");
							System.out.println("2.Savings");
							System.out.println("3.Credit");
							 to = userInput.nextLine();
							 //ensure a proper option
							while(!to.equals("Checking") && !to.equals("Savings") && !to.equals("Credit")){
								System.out.println("Please enter a correct option");
								to = userInput.nextLine();

							}
							//ensure a customer with the info exists
							if(credentialValid(userToPayFirstName,userToPayLastName,userToPayID,customers)){
								key = myHandler.generateKey(userToPayFirstName,userToPayLastName);
								Customer userToPay = customers.get(key);
								try{
									transactionHandler.transactionToOther(userAccount,userToPay,from,to,pay);
								}
								catch(TransactionException eTransfer){
									//there was transaction failure
									System.out.println(eTransfer.getMessage());
									System.out.println("Returning to menu");
									System.out.println("-------------------------------------------------");
									System.out.println("Please choose an option");
									continue;
								}
								//if success then we tell user and log it
								System.out.printf("payment of %.2f$ from %s account to %s %s into their %s account was a success\n",pay,from,userToPay.getFirstName(),userToPay.getLastName(),to);

								transactionLog = String.format("%s %s paid %.2f$ from their %s account to %s %s into their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),pay,from,userToPay.getFirstName(),userToPay.getLastName(),to,time.format(LocalDateTime.now()));
								userAccount.addTransaction(String.format(transactionLog));
								myHandler.logToFile(transactionLog);
							}
							else{
								System.out.println("Error: no user found with provided infromation");
							}
						break;
						case 6://buy
							//control variable used for outer loop
							boolean outerMenu = true;
							//control variable used for outer loop
							boolean malMenu;
							//option control
							int mallOpt = -1;
							//control used to ensure user views menu before purchasing item
							boolean viewedMenu = false;
							System.out.println("-------------------------------------------------");
							//print all items
							System.out.println("Hello Welcome to Miners Mall");
							while(outerMenu){
								System.out.println("What would you like to do?(1-3)");
								System.out.println("1.View Items Menu");
								System.out.println("2.Buy Items");
								System.out.println("3.Exit mall");
								//get the option and ensure it is appropiate
								while (true) {
									String buyOption = userInput.nextLine();
									try {
										mallOpt = Integer.parseInt(buyOption);
									} catch (Exception e) {
										System.out.println("Please choose an appropriate option(1-3)");
										continue;
									}
									if (mallOpt < 1 || mallOpt > 3) {
										System.out.println("Please choose an appropriate option(1-3)");
										continue;
									}
									else if(mallOpt == 2 && !viewedMenu){
										System.out.println("Please view the menu at least once first before purchasing an Item");
										continue;
									}
									break;
								}
								switch (mallOpt){
									//print the menu
									case 1:
										viewedMenu = true;
										printItemMenu(items);
										break;
									//buy item
									case 2:
										malMenu = true;
										System.out.println("-------------------------------------------------");
										while(malMenu) {
											//get item user wants
											System.out.println("Enter the ID of the item you would like to buy(Enter \"E\" to stop buying) ");
											int itemID = -1;
											//ensure a proper item is entered
											while (true) {
												String buyOption = userInput.nextLine();
												try {
													itemID = Integer.parseInt(buyOption);
												} catch (Exception e) {
													if(buyOption.equalsIgnoreCase("E")){
														malMenu = false;
													}
													if(malMenu){
														System.out.println("Please choose an appropriate option");
														continue;
													}
												}
												//ensure a proper id is used
												if ((itemID < 1 || itemID > items.size()) && malMenu) {
													System.out.println("Please choose an appropriate option");
													continue;
												}
												break;
											}
											//turn of the purchase feature when user wants to stop buying
											if(!malMenu){
												System.out.println("Thank you for your purchase");
												break;
											}
											//get item
											Item itemToBuy = items.get((Integer) itemID);
											//ask the user with which account they want to pay with
											System.out.println("-------------------------------------------------");
											System.out.println("Please enter the name of the account you want to pay with");
											System.out.println("1.Checking");
											System.out.println("2.Credit");
											accType = userInput.nextLine();
											//ensure a proper option is entered
											while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
												System.out.println("Please enter a correct option");
												accType = userInput.nextLine();
											}
											//do the transactions and check if successful or not

											try{
												transactionHandler.buyFromMinerMall(userAccount, accType, itemToBuy.getPrice());
											}catch (TransactionException eBuy){
												System.out.printf("unable to buy %s using your %s account\n",itemToBuy.getName(),accType);
												continue;
											}
											//keeps track of items bought plus money spent
											userAccount.addItemBought(itemToBuy.getName());
											userAccount.setTotalMoneySpent(userAccount.getTotalMoneySpent() + itemToBuy.getPrice());
											//log transaction info
											System.out.printf("your purchase of %s for %.2f$ was a success\n", itemToBuy.getName(), itemToBuy.getPrice());
											transactionLog = String.format("%s %s purchased a %s for %.2f$ from miners bank using their %s account at %s\n", userAccount.getFirstName(), userAccount.getLastName(), itemToBuy.getName(), itemToBuy.getPrice(), accType, time.format(LocalDateTime.now()));
											userAccount.addTransaction(String.format(transactionLog));
											myHandler.logToFile(transactionLog);
										}
										System.out.println("-------------------------------------------------");
										break;
									case 3:
										outerMenu = false;
										break;
									default:
										System.out.println("Something went wrong");
										break;
								}
							}
							System.out.println("-------------------------------------------");
							break;
						case 7:
							//user logs out
							System.out.println("-------------------------------------------------");
							System.out.println("Thank you " + userAccount.getFirstName() + " " + userAccount.getLastName() + ", have a great day!");
							System.out.println("Logging out in");
							for(int i = 3;i > 0;i--){
								System.out.println(i);
								try {
									Thread.sleep(1000);
								}catch (InterruptedException e){
									//nothing crazy should happen here
									e.printStackTrace();
								}
							}
							System.out.println("-------------------------------------------------");
							//break does not work within the switch cases so I used boolean to exit loop
							onOff = false;
							menuon = false;
							//sets the ending session for the user and the balance for the session
							//the balance can change if another user pays a customer
							//thus we want to set the edning balance for this session
							userAccount.setSesstionEnd(time.format(LocalDateTime.now()));
							userAccount.setEndCheckBal(userAccount.getCheck().getBalance());
							userAccount.setEndSaveBal(userAccount.getSave().getBalance());
							userAccount.setEndCreditBal(userAccount.getCredit().getBalance());
						break;
						default:
							//ensure user enters one of the right oprtions
							System.out.println("-------------------------------------------------");
							System.out.println("Please look at the options again and choose an appropiate one");
							continue;
					}
					System.out.println("-------------------------------------------------");
					//only done if the user chooses to continue doing stuff
					if(onOff){
						System.out.println("What else would you like to do today?");
					}
				}
			}
			else{
				System.out.println("-------------------------------------------");
				// user did not enter matching credential, we then ask if they would like to retry of just exit
				System.out.println("Look like something went wrong, would you like to try again(Enter Y to continue or n to exit)");
				String decision = userInput.nextLine();
				while(!decision.equals("Y") && !decision.equals("n")){
					System.out.println("Please enter Y or n");
					decision = userInput.nextLine();
				}
				if(decision.equals("Y")){
					System.out.println("-------------------------------------------");
					continue;
				}
				else{
					System.out.println("-------------------------------------------");
					break;
				}

			}
		}
 }
	/**
	 *
	 * @param id user id
	 * @param fName first name of user
	 * @param lName last name of user
	 * @param userCheckNum user checking account number
	 * @param userSaveNum user savings account number
	 * @param userCreditNum user credit account number
	 * @param customers customers of customers
	 * @return returns true if matching
	 * ensures use logging in exists
	 */
	private boolean credentialValid(int id, String fName, String lName, String userCheckNum,String userSaveNum,String userCreditNum,HashMap<String,Customer> customers){
		String key = myHandler.generateKey(fName,lName);
		//check the customer exists
		if(id <= 0 || id > customers.size() || !customers.containsKey(key)){
			return false;
		}
		else{
			//get the customer
			Customer user = customers.get(key);
			//ensure that there are no wired strings the are split apart
			if(user.getFirstName().split("\\s+").length != fName.split("\\s+").length || user.getLastName().split("\\s+").length != lName.split("\\s+").length || user.getSave().getAccNum().split("\\s+").length != userSaveNum.split("\\s+").length || user.getCredit().getAccNum().split("\\s+").length != userCreditNum.split("\\s+").length || user.getCheck().getAccNum().split("\\s+").length != userCheckNum.split("\\s+").length){
				System.out.println("no");
				return false;
			}
			//return true if info matches
			return  user.getID() == id && user.getCheck().getAccNum().equals(myHandler.strNWS(userCheckNum)) && user.getSave().getAccNum().equals(myHandler.strNWS(userSaveNum)) && user.getCredit().getAccNum().equals(myHandler.strNWS(userCreditNum));
		}
	}
	/**
	 *
	 * @param fName first name of user
	 * @param lName last name of user
	 * @param id id of user
	 * @param customers all customers of the bank
	 * @return returns true if user exits
	 * ensures customer exists
	 */
	private boolean credentialValid(String fName,String lName,int id,HashMap<String,Customer> customers){
		String key = myHandler.generateKey(fName,lName);
		//check the customer exists
		if(id <= 0 || id > customers.size() || !customers.containsKey(key)){
			return false;
		}
		//get customer
		Customer user = customers.get(key);
		if(user.getFirstName().split("\\s+").length != fName.split("\\s+").length || user.getLastName().split("\\s+").length != lName.split("\\s+").length){
			return false;
		}
		//compare information
		return  user.getID() == id && myHandler.strNWS(user.getFirstName()).equals(myHandler.strNWS(fName)) && myHandler.strNWS(user.getLastName()).equals(myHandler.strNWS(lName));
	}
	/**
	 *
	 *
	 * @param userAccount users account
	 * @param userToPayFirstName user that will be paid
	 * @param userToPayLastName user to be paid last name
	 * @param userToPayID user to be paid id
	 * @param customers all customers of the bank
	 * @return returns true if user exists
	 * ensures customer cannot pay themselves
	 */
	private boolean credentialValid(Customer userAccount,String userToPayFirstName,String userToPayLastName,int userToPayID,HashMap<String,Customer> customers){
		if(userToPayID <= 0 || userToPayID > customers.size()){
			return false;
		}
		//compare infromation
		if(userAccount.getFirstName().split("\\s+").length != userToPayFirstName.split("\\s+").length || userAccount.getLastName().split("\\s+").length != userToPayLastName.split("\\s+").length){
			return false;
		}
		return myHandler.strNWS(userAccount.getFirstName()).equals(myHandler.strNWS(userToPayFirstName)) && myHandler.strNWS(userAccount.getLastName()).equals(myHandler.strNWS(userToPayLastName)) && userAccount.getID() == userToPayID;
	}
	/**
	 * prints all the items found in miner mall
	 * @param items
	 */
	private void printItemMenu(HashMap<Integer, Item> items){
		System.out.println("-------------------------------------------------");
		for(int i = 1;i < items.size() + 1;i++){
			System.out.println(items.get(i));
		}
		System.out.println("-------------------------------------------------");
	}
}