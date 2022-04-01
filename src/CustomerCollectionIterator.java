import java.util.HashMap;

public class CustomerCollectionIterator implements Iterator{
    private int pos;
    private HashMap<String,Customer> customers;
    private Object[] keys;
    public CustomerCollectionIterator(HashMap<String,Customer> customers){
        this.customers = customers;
        this.keys = customers.keySet().toArray();
        this.pos = 0;
    }

    public boolean hasNext(){
        return this.pos < this.customers.size();
    }
    public Customer next(){
        Customer cus = this.customers.get(keys[pos]);
        pos++;
        return cus;
    }
    public void reset(){
        this.pos = 0;
    }
}
