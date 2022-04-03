import java.util.Scanner;

/**
 * @author Arturo Olmos
 * @version 1.0
 * Abstract class used to template a menu of the bank
 */
public abstract class BankMenu {
    //handles transactions
    private Transactions transactionHandler;
    //has useful methods which are related to the file information
    private Utilities myHandler;
    //scanner objects used to take user input
    private Scanner userInput;
    //map containing the customers
    private CustomerCollection customers;
    //map containing the items
    private ItemCollection items;
    //iterates over the customer iterator
    private CustomerCollectionIterator customerIterator;
    /**
     * Constructor-sets the fields for the menu
     * @param userInput Scanner object used to take in the user input
     * @param customers Collection of Customer Objects
     * @param items Collection of Item objects
     */
    public BankMenu(Scanner userInput,CustomerCollection customers,ItemCollection items){
        this.transactionHandler = Transactions.getInstance();
        this.myHandler = Utilities.getInstance();
        this.userInput = userInput;
        this.customers = customers;
        this.items = items;
        this.customerIterator = this.customers.createIterator();
    }
    /**
     * displays the menu
     */
    public abstract void display();
    /**
     * get a reference to the single Transactions object
     * @return returns Transactions object
     */
    public Transactions getTransactionHandler() {
        return transactionHandler;
    }
    /**
     * gets a reference to the single Utilities object
     * @return returns Utilities object
     */
    public Utilities getMyHandler() {
        return myHandler;
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
     * gets a referance to the CustomerCollectionIterator of the menu
     * @return retusn a CustomerCollectionIterator object
     */
    public CustomerCollectionIterator getCustomerIterator() {
        return customerIterator;
    }
}
