import java.util.HashMap;

public class ItemCollectionIterator implements Iterator{
    private HashMap<Integer,Item> items;
    private int pos;
    private Object[] keys;
    public ItemCollectionIterator(HashMap<Integer,Item> items){
        this.items = items;
        this.pos = 0;
        this.keys = items.keySet().toArray();

    }
    public boolean hasNext(){
        return pos < this.keys.length;
    }
    public Item next(){
        Item temp = this.items.get((Integer) this.keys[pos]);
        pos++;
        return temp;
    }
    public void reset(){
        pos = 0;
    }

}
