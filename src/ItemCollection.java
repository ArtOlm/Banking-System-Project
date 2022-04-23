import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

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
     */
    public ItemCollection(){
        this.items = new HashMap<>();
        populate();
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
    /**
     * reads Miners Mall.csv and populates HashMap of Item objects
     */
    public void populate(){
        //File object so I can read with Scanner
        File customerInfoFile = new File("src/Read_Only_Files/Miner Mall 5.csv");
        Scanner fileReader = null;
        try{
            fileReader = new Scanner(customerInfoFile);
        }
        catch (FileNotFoundException e){
            System.out.println("Error: Cannot find src/Read_Only_Files/Miner Mall 5.csv");
            System.exit(1);
        }
        //assuming it has a header
        //based on the header set up indexes for attributes of the item
        String[] header = fileReader.nextLine().split(",");
        int[] headerIndexes = new int[header.length];
        //set up indexes so that file can be read in any order
        for(int i = 0;i < header.length;i++) {
            if (header[i].equals("ID")) {
                headerIndexes[0] = i;
            } else if (header[i].equals("Item")) {
                headerIndexes[1] = i;
            } else if (header[i].equals("Price")) {
                headerIndexes[2] = i;
            } else if (header[i].equals("Max")) {
                headerIndexes[3] = i;
            }
        }
        while(fileReader.hasNextLine()){
            createItem(fileReader.nextLine(),headerIndexes);//adds Item object to the users list
        }
        fileReader.close();
    }
    /**
     *creates an Item object based on the info
     * @param itemInfo info from a csv file which is split and allocated accordingly
     */
    private void createItem(String itemInfo,int[] indexes) {
        //split the info
        String[] info = itemInfo.split(",");
        double price = Double.parseDouble(info[indexes[2]]);
        String name = info[indexes[1]];
        int max = Integer.parseInt(info[indexes[3]]);
        int id = Integer.parseInt(info[indexes[0]]);
        //add item
        this.items.put(id,new Item(id,name,price,max));
    }
}
