import java.util.HashMap;

/**
 * @author Arturo Olmos
 * @version 1.0
 * this class helps iterate over the CustomerCollection
 */
public class CustomerCollectionIterator implements Iterator{
    //used to keep track of the current position used for traversal
    private int pos;
    //data structure used in CustomerCollection
    private HashMap<String,Customer> customers;
    //keys are used to iterate over the HashMap
    private Object[] keys;
    /**
     * Constructor-sets the data structure of the Iterator
     * @param customers the same structure used in CustomerCollection
     */
    public CustomerCollectionIterator(HashMap<String,Customer> customers){
        this.customers = customers;
        this.keys = customers.keySet().toArray();
        this.pos = 0;
    }
    /**
     * checks to see if there are still more objects to iterate over
     * @return returns true if it has another object to iterate over
     */
    public boolean hasNext(){
        return this.pos < this.keys.length;
    }
    /**
     * get the next available object
     * @return returns a Customer object upon success
     */
    public Customer next() throws IndexOutOfBoundsException{
        if(pos >= this.keys.length){
            throw new IndexOutOfBoundsException("Error: No more Objects, call this.reset to start over");
        }
        Customer cus = this.customers.get(keys[pos]);
        pos++;
        return cus;
    }
}
