import java.util.ArrayList;

/**
 * this class helps execute the transactions in a much faster time
 */
public class ExecutionHelper extends Thread{
    int start;
    int end;
    ArrayList<String[]> transactions;
    ManagerActions manHandler;
    CustomerCollection customers;
    ItemCollection items;

    /**
     * the constructor
     * @param start the start from where the thread will execute from
     * @param end the end which the thread will reach
     * @param transactions the transactions
     * @param customers
     * @param items
     */
    public ExecutionHelper(int start,int end,ArrayList<String[]> transactions, CustomerCollection customers, ItemCollection items){
        this.start = start;
        this.end = end;
        this.transactions = transactions;
        this.manHandler = ManagerActions.getInstance();
        this.customers = customers;
        this.items = items;
    }

    /**
     * this method helps execute the transactions in parallel for faster execution
     */
    public void run(){
        this.manHandler.execTransactions(customers,items, (transactions.size() / 2), transactions.size(), transactions);
    }
}
