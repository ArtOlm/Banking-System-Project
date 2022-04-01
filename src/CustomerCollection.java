import java.util.HashMap;

public class CustomerCollection implements Collections {

    private HashMap<String,Customer> customers;

    public CustomerCollection(HashMap<String,Customer> customers){
        this.customers = customers;
    }

    public void add(Object key,Object o) {
        customers.put(key.toString(),(Customer) o);
    }

    public Object get(Object key){
        return (Object) customers.get(key.toString());
    }
    public boolean hasKey(Object key){
        return this.customers.containsKey(key.toString());
    }
    public Iterator createIterator(){
            return new CustomerCollectionIterator(this.customers);
    }
    public int size(){
        return this.customers.size();
    }
}
