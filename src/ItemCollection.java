import java.util.HashMap;

public class ItemCollection implements Collections{
    private HashMap<Integer,Item> items;
    public ItemCollection(){

    }
    public ItemCollection(HashMap<Integer,Item> items){
        this.items = items;
    }
    public void add(Object key,Object item){
        this.items.put((Integer) key,(Item) item);
    }
    public Item get(Object key){
        return this.items.get((Integer) key);
    }
    public boolean hasKey(Object key){
        return this.items.containsKey((Integer) key);
    }
    public int size(){
        return this.items.size();
    }
    public ItemCollectionIterator createIterator(){
        return new ItemCollectionIterator(this.items);
    }
}
