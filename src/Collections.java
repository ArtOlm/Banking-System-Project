/**
 * @author Arturo Olmos
 * @version 1.0
 * This interface is used to implement Collections
 */
public interface Collections {
    /**
     * creates an iterator for the collection
     * @return returns an iterator
     */
    public Iterator createIterator();

    /**
     * adds on object to the Collection
     * @param key the keys used to access the added object
     * @param o the object mapped to the key
     */
    public void add(Object key, Object o);

    /**
     * get an Object mapped to the key
     * @param key used to access the Object
     * @return returns the object mapped to the key
     */
    public Object get(Object key);

    /**
     * check to see if the Collection has the key provided
     * @param key key testes for existence
     * @return returns true if it does contain the key
     */
    public boolean hasKey(Object key);

    /**
     * get the current size of the collection
     * @return returns the size of the Collection
     */
    public int size();

    /**
     * populates the Collection with a file provided
     */
    public void populate();
}
