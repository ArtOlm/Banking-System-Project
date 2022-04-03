import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;

/**
 * @author Arturo Olmos/Jaehyeon
 * @version 4.0
 * class has helpful methods used to solve problems in many classes
 * this class is my approach to a singleton design pattern
 * this file used to be known as FReaderWriter in my lab 2
 */
public class Utilities{
	private static Utilities handler;
	private int maxCustomerIDX;
	//default editor
	private Utilities(){ this.maxCustomerIDX = 0;}
	/**
	 *
	 * @return an array list filled with an array of string
	 * populates an array list with lines from the actions.csv
	 */
	public ArrayList<String[]> loadTransactions(){
		//arraylist containing the transactions
		ArrayList<String []> transactions = new ArrayList<>();
		File transactionFile = new File("src/Read_Only_Files/actions PA4.csv");
		Scanner transactionsReader = null;
		try {
			transactionsReader = new Scanner(transactionFile);
		}
		catch (FileNotFoundException e){
			System.out.println("Error: File actions PA4.csv not found, cannot continue");
			System.exit(1);
		}
		if(!transactionsReader.hasNextLine()){
			System.out.println("Error: actions PA4.csv is empty");
			return transactions;
		}
		//get rid of header
		transactionsReader.nextLine();
		while(transactionsReader.hasNextLine()){
			//while reading from the file I add the array containing the information of the transaction
			transactions.add(transactionsReader.nextLine().split(","));
		}
		return transactions;
	}
	/**
	 *
	 * @param fName the first name of the user
	 * @param lName the last name of the user
	 *
	 * @return returns a generated string based on the users first and last name
	 * generates a key for the hash map
	 */
	public String generateKey(String fName,String lName){
		String nwsFName = "";
		String nwsLName = "";
		String[] fnameNWS = fName.split("\\s+");
		for(int i = 0;i < fnameNWS.length;i++){
			nwsFName += fnameNWS[i];
		}
		String[] lnameNWS = lName.split("\\s+");
		for(int i = 0;i < lnameNWS.length;i++){
			nwsLName += lnameNWS[i];
		}
		return nwsFName + nwsLName;
	}
	/**
	 *
	 * @return returns the instance of this class to be called in different classes
	 * my approach to a singleton design pattern
	 */
	public static Utilities getInstance(){

		if(handler == null){
			handler = new Utilities();
		}
		return handler;
	}
	/**
	 * read Bank Customers 3 and populates an HashMap of Customers
	 * @return HashMap containing Customer Objects
	 */
	public HashMap<String,Customer> populateCustomers(){
		//File object so I can read with Scanner
		File customerInfoFile = new File("src/Read_Only_Files/Bank Customers 4.csv");
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(customerInfoFile);
		}
		catch (FileNotFoundException e){
			System.out.println("Error: Cannot find src/Read_Only_Files/Bank Customers 4.csv");
			System.exit(1);
		}
		//use the header to dynamically read csv file
		String[] header = fileReader.nextLine().split(",");
		int[] headerIndexes = new int[header.length];
		//setting up the indexes based on the header ordering
		for(int i = 0;i < header.length;i++) {
			if(header[i].equals("ID")){
				headerIndexes[0] = i;
			} else if(header[i].equals("PIN")){
				headerIndexes[1] = i;
			} else if(header[i].equals("First Name")){
				headerIndexes[2] = i;
			} else if(header[i].equals("Last Name")){
				headerIndexes[3] = i;
			} else if(header[i].equals("Address Checking Balance")){
				headerIndexes[4] = i;
			} else if(header[i].equals("City")){
				headerIndexes[5] = i;
			} else if(header[i].equals("State")){
				headerIndexes[6] = i;
			} else if(header[i].equals("Zip")){
				headerIndexes[7] = i;
			} else if(header[i].equals("Phone Number")){
				headerIndexes[8] = i;
			} else if(header[i].equals("Date of Birth")){
				headerIndexes[9] = i;
			} else if(header[i].equals("Checking Account Number")){
				headerIndexes[10] = i;
			} else if(header[i].equals("Checking Balance")){
				headerIndexes[11] = i;
			} else if(header[i].equals("Savings Account Number")){
				headerIndexes[12] = i;
			} else if(header[i].equals("Savings Balance")){
				headerIndexes[13] = i;
			} else if(header[i].equals("Credit Account Number")){
				headerIndexes[14] = i;
			} else if(header[i].equals("Credit Balance")){
				headerIndexes[15] = i;
			} else if(header[i].equals("Credit Score")){
				headerIndexes[16] = i;
			} else if(header[i].equals("Credit Limit")){
				headerIndexes[17] = i;
			}
		}

		HashMap<String,Customer> users = new HashMap<>();
		//add customer objects from file
		while(fileReader.hasNextLine()){
			createCustomer(fileReader.nextLine(),users,headerIndexes);//adds Customer object to the users list
		}
		fileReader.close();

		return users;
	}
	/**
	 * *
	 * @param accInfo given information based on a csv we use it to split into correct data
	 * @param users array list that is populated as Customer objects are made
	 */
	//small method to simplify the creating of a checking object so it doesn't get to messy
	private void createCustomer(String accInfo,HashMap<String,Customer> users,int[] indexes) {
		String[] info = accInfo.split(",");
		//give names what info contains to properly identify because csv file is a mess
		//order accordingly to the csv file
		String state = info[indexes[6]];
		String city = info[indexes[5]];
		double cBalance = Double.parseDouble(info[indexes[15]]);
		String chNum = info[indexes[10]];
		String zip = info[indexes[7]];
		String fName = info[indexes[2]];
		int cLimit = Integer.parseInt(info[indexes[17]]);
		String lName = info[indexes[3]];
		double sBalance = Double.parseDouble(info[indexes[13]]);
		String phoneNum = info[indexes[8]];
		int id = Integer.parseInt(info[indexes[0]]);
		int pin = Integer.parseInt(info[indexes[1]]);
		String sNum = info[indexes[12]];
		String dob = info[indexes[9]];
		String cNum = info[indexes[14]];
		int cScore = Integer.parseInt(info[indexes[16]]);
		String add = info[indexes[4]];
		double chBalance = Double.parseDouble(info[indexes[11]]);
		//create account objects needed
		Checking chAcc =  new Checking(chNum,chBalance);
		Savings sAcc  = new Savings(sNum,sBalance);
		Credit cACC = new Credit(cNum,cBalance,cLimit,cScore);
		Customer cus = new Customer(fName,lName,add,city,state,zip,phoneNum,dob,id,chAcc,sAcc,cACC,pin);
		if(this.maxCustomerIDX < id){
			this.maxCustomerIDX = id;
		}
		String key = generateKey(fName,lName);
		//add the customer to the hash map
		users.put(key,cus);
	}
	/**
	 * reads Miners Mall.csv and populates HashMap of Item objects
	 * @return returns a HashMap populated with Item objects
	 */
	public HashMap<Integer,Item> populateItems(){
		//File object so I can read with Scanner
		File customerInfoFile = new File("src/Read_Only_Files/Miner Mall.csv");
		Scanner fileReader = null;
		try{
			fileReader = new Scanner(customerInfoFile);
		}
		catch (FileNotFoundException e){
			System.out.println("Error: Cannot find src/Read_Only_Files/Miner Mall.csv");
			System.exit(1);
		}
		//assuming it has a header
		//based on the header set up indexes for attributes of the item
		String[] header = fileReader.nextLine().split(",");
		int[] headerIndexes = new int[header.length];
		//set up indexes so that file can be read in any order
		for(int i = 0;i < header.length;i++){
			if(header[i].equals("ID")){
				headerIndexes[0] = i;
			} else if(header[i].equals("Item")){
				headerIndexes[1] = i;
			} else if(header[i].equals("Price")){
				headerIndexes[2] = i;
			} else if(header[i].equals("Max")){
				headerIndexes[3] = i;
			}
		}
		//Hashmap that will be populated
		HashMap<Integer,Item> items = new HashMap<>();
		while(fileReader.hasNextLine()){
			createItem(fileReader.nextLine(),items,headerIndexes);//adds Item object to the users list
		}
		fileReader.close();
		return items;
	}

	/**
	 *
	 * @return returns next biggest index
	 */
	public int getMaxCustomerIDX(){
		return this.maxCustomerIDX + 1;
	}
	/**
	 *
	 *
	 * increments max id when new user is added
	 */
	public void incrementMaxCustomerIDX(){
		 this.maxCustomerIDX++;
	}
	/**
	 *
	 * @param itemInfo info from a csv file which is split and allocated accordingly
	 * @param items ArrayList which will be populated as the Item is created
	 */
	private void createItem(String itemInfo,HashMap<Integer,Item> items,int[] indexes) {
		//split the info
		String[] info = itemInfo.split(",");
		double price = Double.parseDouble(info[indexes[2]]);
		String name = info[indexes[1]];
		int max = Integer.parseInt(info[indexes[3]]);
		int id = Integer.parseInt(info[indexes[0]]);
		//add item
		items.put(id,new Item(id,name,price,max));
	}
	/**
	 *
	 * @param stringToLog a string that will be logged onto a file called Log.txt
	 */
	//logs a formatted string to a file
	public void logToFile(String stringToLog){
		//Initialize File object, this helps in using the identifier within the try and catch scopes
		File findFile = new File("src/Generated_Files/Log.txt");
		try{
			if(findFile.exists()){//look for file and append new transaction
				//write to file
				BufferedWriter logger = new BufferedWriter(new FileWriter("src/Generated_Files/Log.txt",true));
				logger.write(stringToLog);
				logger.close();
			} else{//if file not found the create it and write to it
				BufferedWriter logger = new BufferedWriter(new FileWriter("src/Generated_Files/Log.txt"));
				logger.write(stringToLog);
				logger.close();
			}
		}
		catch(IOException e){
			System.out.println("Something went wrong");
			e.printStackTrace();
			//exits with 1 to let it be known the code exited with error
			System.exit(1);
		}
	}
	//updates the CVS file containing customer info
	/**
	 * updates the Bank Customers 2.csv file
	 * @param users an HashMap populated by Customer Objects which is used to write to the file
	 */
	public void updateCustomerInfo(CustomerCollection users){
		HashMap<Integer,Customer> integerMapped = new HashMap<>();
		CustomerCollectionIterator customerCollectionIterator = users.createIterator();
		while (customerCollectionIterator.hasNext()){
			Customer cus = customerCollectionIterator.next();
			integerMapped.put(cus.getID(),cus);
		}
		while(true){
			try{
				BufferedWriter updateFileInfo =  new BufferedWriter(new FileWriter("src/Generated_Files/Updated Customers Sheet.csv",false));
				//header of the file to keep data organized
				//we rewrite the file based on updates
				updateFileInfo.write("ID,Pin,First Name,Last Name,Address,City,State,Zip,Phone Number,Date of Birth,Checking Account Number,Checking Balance,Savings Account Number,Savings Balance,Credit Account Number,Credit Balance,Credit Score,Credit Limit\n");
				for(int i = 1;i < integerMapped.size() + 1;i++){
					Customer user = integerMapped.get(i);
					updateFileInfo.write(user.getID()+","+ user.getPin()+","+user.getFirstName()+","+user.getLastName()+","+user.getAddress()+","+user.getCity()+","+user.getState()+","+user.getZipCode()+","+user.getPhoneNum()+","+user.getDOB() + "," + user.getCheck().getAccNum() + "," + user.getCheck().getBalance() + "," + user.getSave().getAccNum() + "," + user.getSave().getBalance() + "," + user.getCredit().getAccNum() + "," + user.getCredit().getBalance()+ "," + user.getCredit().getScore()+ "," + user.getCredit().getLimit() + "\n");
				}
				updateFileInfo.close();
			} catch (IOException e){
				//if the file is open then we let user know to close it and give them some time to close it
				System.out.println("ERROR: Please close the file src/Generated_Files/Updated Customers Sheet.csv");
				System.out.println("Data might not have been updated!");
				System.out.println("We will resume in three seconds, please close by then");
				System.out.println("-------------------------------------------------");
				try {
					Thread.sleep(3000);
				}catch (InterruptedException ex){
					//honestly nothing should happen
					ex.printStackTrace();
				}
				continue;
			}
			break;
		}
	}
	/**
	 *
	 * @param str1
	 * @param str2
	 * @return combines two string without any white spaces
	 * returns a string without any white spaces
	 */
	public String strNWS(String str1,String str2){
		String nwsName = "";
		String[] fnameNWS = str1.split("\\s+");
		for(int i = 0;i < fnameNWS.length;i++){
			nwsName += fnameNWS[i];
		}
		String[] lnameNWS = str2.split("\\s+");
		for(int i = 0;i < lnameNWS.length;i++){
			nwsName += lnameNWS[i];
		}
		return nwsName;
	}
	/**
	 *
	 * @param str1 string that will be edited
	 * @return
	 * returns a string without any white spaces
	 */
	public String strNWS(String str1){
		String nwsName = "";
		String[] fnameNWS = str1.split("\\s+");
		for(int i = 0;i < fnameNWS.length;i++){
			nwsName += fnameNWS[i];
		}
		return nwsName;
	}
	/**
	 * handles the bank statement creation
	 * @param cus customers whose statement will be written for
	 * @param statement the statement of the customer
	 */
	public void writeBankStatement(Customer cus, String statement){
		try {
			String path = String.format("src/Generated_Files/%s_%s_Bank_Statement.txt",cus.getFirstName(),cus.getLastName());
			File f = new File(path);
			if(!f.exists()){
				f.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write(statement);
			writer.close();
		}
		catch (Exception e){
			System.out.println("Something went wrong");
		}
	}
}