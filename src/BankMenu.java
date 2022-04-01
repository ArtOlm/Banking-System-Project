import java.util.HashMap;
import java.util.Scanner;

public abstract class BankMenu {
    //handles transactions
    private Transactions transactionHandler;
    //has useful methods which are related to the file information
    private Utilities myHandler;
    //manager action handler
    private ManagerActions manHandler;
    //scanner objects used to take user input
    private Scanner userInput;
    //map containing the customers
    private CustomerCollection customers;
    //map containing the items
    private HashMap<Integer,Item> items;
    //iterates over the customer iterator
    private CustomerCollectionIterator customerIterator;
    public BankMenu(Scanner userInput,CustomerCollection customers,HashMap<Integer,Item> items){
        this.transactionHandler = Transactions.getInstance();
        this.myHandler = Utilities.getInstance();
        this.manHandler = ManagerActions.getInstance();
        this.userInput = userInput;
        this.customers = customers;
        this.items = items;
        this.customerIterator = (CustomerCollectionIterator) this.customers.createIterator();
    }
    public abstract void display();
    public Transactions getTransactionHandler() {
        return transactionHandler;
    }

    public Utilities getMyHandler() {
        return myHandler;
    }

    public ManagerActions getManHandler() {
        return manHandler;
    }

    public Scanner getUserInput() {
        return userInput;
    }

    public CustomerCollection getCustomers() {
        return customers;
    }

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    public CustomerCollectionIterator getCustomerIterator() {
        return customerIterator;
    }
}
