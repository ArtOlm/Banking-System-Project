/**
 * @author Arturo Olmos/Jaehyeon Park
 * @version 1.0
 * This interface helps iterate over a Collection
 */
public interface Iterator {
    /**
     * checks to see if the Iterator has more objects to iterate over
     * @return returns true if there is still more items
     */
    public boolean hasNext();

    /**
     * returns the next object available
     * @return an object of the Collection
     */
    public Object next();
}
