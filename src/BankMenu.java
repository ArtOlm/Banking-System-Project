import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Arturo Olmos
 * @version 1.0
 * Abstract class used to template a menu of the bank
 */
public abstract class BankMenu {
    //handles transactions
    private Transactions transactionHandler;
    //scanner objects used to take user input
    private Scanner userInput;
    //map containing the customers
    private CustomerCollection customers;
    //map containing the items
    private ItemCollection items;

    private DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    //iterates over the customer iterator
    /**
     * Constructor-sets the fields for the menu
     * @param userInput Scanner object used to take in the user input
     * @param customers Collection of Customer Objects
     * @param items Collection of Item objects
     */
    public BankMenu(Scanner userInput,CustomerCollection customers,ItemCollection items){
        this.transactionHandler = Transactions.getInstance();
        this.userInput = userInput;
        this.customers = customers;
        this.items = items;
    }
    /**
     * displays the menu
     * @param userAccount the current user for the menu
     */
    public void display(Customer userAccount){
        System.out.println("Menu");
    }
    /**
     * get a reference to the single Transactions object
     * @return returns Transactions object
     */
    public Transactions getTransactionHandler() {
        return transactionHandler;
    }
    /**
     * get the reference to a Scanner object used to take in user input
     * @return returns Scanner object
     */
    public Scanner getUserInput() {
        return userInput;
    }
    /**
     * gets a referance to the CustomerCollection
     * @return returns the CustomerCollection of the bank
     */
    public CustomerCollection getCustomers() {
        return customers;
    }
    /**
     * gets a referance to the ItemCollection
     * @return returns an ItemCollection
     */
    public ItemCollection getItems() {
        return items;
    }
    /**
     * returns a string without any white spaces
     * @param str1 string that will be edited
     * @return returns a string with no white space
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
     * is used to format the time
     * @return returns a format for the time
     */
    public DateTimeFormatter getTime() {
        return time;
    }

}
