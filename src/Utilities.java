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
	//default editor
	private Utilities(){}
	/**
	 * populates an array list with lines from the actions.csv
	 * @return an array list filled with an array of string
	 */
	public ArrayList<String[]> loadTransactions(){
		//arraylist containing the transactions
		ArrayList<String []> transactions = new ArrayList<>();
		File transactionFile = new File("src/Read_Only_Files/actions PA5.csv");
		Scanner transactionsReader = null;
		try {
			transactionsReader = new Scanner(transactionFile);
		}
		catch (FileNotFoundException e){
			System.out.println("Error: File actions PA5.csv not found, cannot continue");
			System.exit(1);
		}
		if(!transactionsReader.hasNextLine()){
			System.out.println("Error: actions PA5.csv is empty");
			System.exit(1);
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
	 * generates a key for the hash map
	 * @param fName the first name of the user
	 * @param lName the last name of the user
	 * @return returns a generated string based on the users first and last name
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
	 * my approach to a singleton design pattern
	 * @return returns the instance of this class to be called in different classes
	 */
	public static Utilities getInstance(){

		if(handler == null){
			handler = new Utilities();
		}
		return handler;
	}
	/**
	 *logs info to the log.txt file
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
	 * returns a string without any white spaces
	 * @param str1 string 1
	 * @param str2 string 2
	 * @return combines two string without any white spaces
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
	 * returns a string without any white spaces
	 * @param str1 string that will be edited
	 * @return
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