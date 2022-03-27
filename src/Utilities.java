import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Set;

/**
 * @author Arturo
 * @version 1.0
 * class has helpful methods used to solve problems in many classes
 * this class is my approach to a singleton design pattern
 * this file used to be known as FReaderWriter in my lab 2
 */
/*
I assumed that both Bank Customers 3.csv exists, as well Miner Mall.csv within the same directory as the running program
*/
//method calls of this class are made in RunBank.java and Transactions.java
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
		ArrayList<String []> transactions = new ArrayList<String[]>();
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
	 * @param fName
	 * @param lName
	 *
	 * @return
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
		String key = nwsFName + nwsLName;
		return key;
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
		fileReader.nextLine();//gets rid of the heading in the csv file

		HashMap<String,Customer> users = new HashMap<String,Customer>();
		//add customer objects from file
		while(fileReader.hasNextLine()){
			createCustomer(fileReader.nextLine(),users);//adds Customer object to the users list
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
	private void createCustomer(String accInfo,HashMap<String,Customer> users) {

		String[] info = accInfo.split(",");

		//give names what info contains to properly identify because csv file is a mess
		//order accordingly to the csv file
		String state = info[0];
		String city = info[1];
		double cBalance = Double.parseDouble(info[2]);
		String chNum = info[3];
		String zip = info[4];
		String fName = info[5];
		int cLimit = Integer.parseInt(info[6]);
		String lName = info[7];
		double sBalance = Double.parseDouble(info[8]);
		String phoneNum = info[9];
		int id = Integer.parseInt(info[10]);
		int pin = Integer.parseInt(info[11]);
		String sNum = info[12];
		String dob = info[13];
		String cNum = info[14];
		int cScore = Integer.parseInt(info[15]);
		String add = info[16];
		double chBalance = Double.parseDouble(info[17]);
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
		fileReader.nextLine();//gets rid of the heading in the csv file
		//ArrayList that will be populated
		HashMap<Integer,Item> items = new HashMap<Integer,Item>();

		while(fileReader.hasNextLine()){
			createItem(fileReader.nextLine(),items);//adds Item object to the users list
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
	private void createItem(String itemInfo,HashMap<Integer,Item> items) {
		//split the info
		String[] info = itemInfo.split(",");
		double price = Double.parseDouble(info[0]);
		String name = info[1];
		int max = Integer.parseInt(info[2]);
		int id = Integer.parseInt(info[3]);
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
			}
			else{//if file not found the create it and write to it
				findFile.createNewFile();
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
	public void updateCustomerInfo(HashMap<String,Customer> users){
		HashMap<Integer,Customer> integerMapped = new HashMap<Integer,Customer>();
		Set<String> keySet = users.keySet();
		Object[] keys = keySet.toArray();
		for(int i = 0;i < keys.length;i++){
			Customer cus = users.get(keys[i].toString());
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
					updateFileInfo.write(user.getID()+","+ user.getPin()+","+user.getFName()+","+user.getLName()+","+user.getAddress()+","+user.getCity()+","+user.getState()+","+user.getZip()+","+user.getPhone()+","+user.getDOB() + "," + user.getCheck().getAccNum() + "," + user.getCheck().getBalance() + "," + user.getSave().getAccNum() + "," + user.getSave().getBalance() + "," + user.getCredit().getAccNum() + "," + user.getCredit().getBalance()+ "," + user.getCredit().getScore()+ "," + user.getCredit().getLimit() + "\n");
				}
				updateFileInfo.close();
			}
			catch (IOException e){
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
			String path = String.format("src/Generated_Files/%s_%s_Bank_Statement.txt",cus.getFName(),cus.getLName());
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