import java.util.HashMap;

/**
 * @author Arturo Olmos
 * @version 1.0
 * Class helps iterate over the ItemCollection
 */
public class ItemCollectionIterator implements Iterator{
    //the same data structure as the Collection
    private HashMap<Integer,Item> items;
    //current position for the iterator
    private int pos;
    //keys used to access the HashMap
    private Object[] keys;
    /**
     * Constructor-sets the same data structure used in the collection
     * @param items same HashMap used in the Collection
     */
    public ItemCollectionIterator(HashMap<Integer,Item> items){
        this.items = items;
        this.pos = 0;
        this.keys = items.keySet().toArray();

    }
    /**
     * checks to see if there are more objects to iterate over
     * @return true if there are more objects to iterate over
     */
    public boolean hasNext(){
        return pos < this.keys.length;
    }

    /**
     * gets the next available Item object
     * @return an Item object
     * @throws IndexOutOfBoundsException if no more objects to oterate over
     */
    public Item next() throws IndexOutOfBoundsException{
        if(pos >= this.keys.length){
            throw new IndexOutOfBoundsException("Error:no more Items to iterate over, use this.reset for reuse");
        }
        Item temp = this.items.get((Integer) this.keys[pos]);
        pos++;
        return temp;
    }


}
