import java.util.HashMap;
/**
 * @author Arturo Olmos/JaehYeon Park
 * @version 1.0
 * This class is used to handle the customers of the bank
 */
public class CustomerCollection implements Collections {
    //data structure used for the collection
    private HashMap<String,Customer> customers;
    /**
     * Constructor- sets the data structure
     * @param customers the customer collection
     */
    public CustomerCollection(HashMap<String,Customer> customers){
        this.customers = customers;
    }
    /**
     * adds a Customer objects to the collection
     * @param key the key to map with the Customer
     * @param o the Customer object
     */
    public void add(Object key,Object o) {
        customers.put(key.toString(),(Customer) o);
    }
    /**
     * returns a Customer object based on the key
     * @param key used to identify the Customer
     * @return returns customer if they exist
     */
    public Customer get(Object key){
        return customers.get(key.toString());
    }
    /**
     * checks to see if the key exists
     * @param key will be tested to see if it exists
     * @return returns true if it has the key
     */
    public boolean hasKey(Object key){
        return this.customers.containsKey(key.toString());
    }
    /**
     * creates an Iterator objects used to iterte over the collection
     * @return returns a CustomerCollectionIterator
     */
    public CustomerCollectionIterator createIterator(){
            return new CustomerCollectionIterator(this.customers);
    }

    /**
     * returns the current size of the Collection
     * @return size of the Collection
     */
    public int size(){
        return this.customers.size();
    }
}
