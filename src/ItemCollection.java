import java.util.HashMap;

/**
 * @author Arturo Olmos
 * @version 1.0
 * this class servers as the collection for the items
 */
public class ItemCollection implements Collections{
    //the data structure used for the collection
    private HashMap<Integer,Item> items;
    /**
     * Constructor-used to set the data structure
     * @param items HashMap used for the collection
     */
    public ItemCollection(HashMap<Integer,Item> items){
        this.items = items;
    }

    /**
     * adds an object to the collection
     * @param key the key used to access the object
     * @param item the object mapped to the key
     */
    public void add(Object key,Object item){
        this.items.put((Integer) key,(Item) item);
    }
    /**
     * gets the object mapped to the key
     * @param key used to access the objects
     * @return returns an item of the collection
     */
    public Item get(Object key){
        return this.items.get((Integer) key);
    }

    /**
     * checks to see if the collection has the key
     * @param key used to check
     * @return returns true if it does contain the key
     */
    public boolean hasKey(Object key){
        return this.items.containsKey((Integer) key);
    }

    /**
     * returns the size of the Collection
     * @return returns the size
     */
    public int size(){
        return this.items.size();
    }

    /**
     * creates an Iterator for the Collection
     * @return returns an iterator
     */
    public ItemCollectionIterator createIterator(){
        return new ItemCollectionIterator(this.items);
    }
}
