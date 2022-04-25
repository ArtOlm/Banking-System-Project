/**
 * @author Arturo Olmos/Jaehyeon Park
 * @version 2.0
 * represents an item that is created by reading file
 */
public class Item{

	private int id;
	private String name;
	private double price;
	private int max;

	/**
	 * default constructor
	 */
	public Item(){
		
	}

	/**
	 * Constructor for creating a complete Item
	 * @param id sets the id attribute
	 * @param name sets the name attribute
	 * @param price sets the price attribute
	 * @param max sets the max items
	 */
	public Item(int id,String name,double price,int max){
		this.max = max;
		this.id = id;
		this.name = name;
		this.price = price;

	}

	/**
	 *sets name
	 * @param name sets the name attribute
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * sets id
	 * @param id sets the id attribute
	 */
	public void setID(int id){
		this.id = id;
	}

	/**
	 *sets the price
	 * @param price sets the price attribute
	 */
	public void setPrice(double price){
		this.price = price;
	}

	/**
	 *gets item name
	 * @return returns the name of the Item
	 */
	public String getName(){
		return this.name;
	}

	/**
	 *gets item id
	 * @return returns the id of the Item
	 */
	public int getID(){
		return this.id;
	}

	/**
	 *gets item price
	 * @return returns the price of the Item
	 */
	public double getPrice(){
		return this.price;
	}

	/**
	 *returns a string based on the item
	 * @return returns a formatted String with the Item information
	 */
	public String toString(){
		return String.format("ID: %d Name: %s  Price: $%.2f Stock: %d",this.id,this.name,this.price,this.max);
	}

	/**
	 * returns the max number of purchases of the item
	 * @return max purchases
	 */
	public int getMax() {
		return max;
	}

	/**
	 * sets item max
	 * @param max the quantity for the item
	 */
	public void setMax(int max) {
		this.max = max;
	}

}