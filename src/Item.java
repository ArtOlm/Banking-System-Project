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

	public Item(){
		
	}

	/**
	 *
	 * @param id sets the id attribute
	 * @param name sets the name attribute
	 * @param price sets the price attribute
	 */
	public Item(int id,String name,double price,int max){
		this.max = max;
		this.id = id;
		this.name = name;
		this.price = price;

	}

	/**
	 *
	 * @param name sets the name attribute
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 *
	 * @param id sets the id attribute
	 */
	public void setID(int id){
		this.id = id;
	}

	/**
	 *
	 * @param price sets the price attribute
	 */
	public void setPrice(double price){
		this.price = price;
	}

	/**
	 *
	 * @return returns the name of the Item
	 */
	public String getName(){
		return this.name;
	}

	/**
	 *
	 * @return returns the id of the Item
	 */
	public int getID(){
		return this.id;
	}

	/**
	 *
	 * @return returns the price of the Item
	 */
	public double getPrice(){
		return this.price;
	}

	/**
	 *
	 * @return returns a formatted String with the Item information
	 */
	public String toString(){
		return ("ID: " + id + " Name: " + name + " Price: " + price);
	}

	/**
	 *
	 * @return max purchases
	 * returns the max number of purchases of the item
	 */
	public int getMax() {
		return max;
	}

	/**
	 *
	 * @param max
	 * sets item max
	 */
	public void setMax(int max) {
		this.max = max;
	}

}