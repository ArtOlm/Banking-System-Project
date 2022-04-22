import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Arturo Olmos/JaehYeon Park
 * @version 1.0
 * This class is used to handle the customers of the bank
 */
public class CustomerCollection implements Collections {
    //data structure used for the collection
    private HashMap<String,Customer> customers;
    private Utilities handler;
    private int maxCustomerIDX;
    /**
     * Constructor- sets the data structure
     */
    public CustomerCollection(){
        maxCustomerIDX = 0;
        this.handler = Utilities.getInstance();
        this.customers = new HashMap<>();
        populateCustomers();
    }
    /**
     * adds a Customer objects to the collection
     * @param key the key to map with the Customer
     * @param o the Customer object
     */
    public void add(Object key,Object o) {
        customers.put(key.toString(),(Customer) o);
    }
    /**
     * returns a Customer object based on the key
     * @param key used to identify the Customer
     * @return returns customer if they exist
     */
    public Customer get(Object key){
        return customers.get(key.toString());
    }
    /**
     * checks to see if the key exists
     * @param key will be tested to see if it exists
     * @return returns true if it has the key
     */
    public boolean hasKey(Object key){
        return this.customers.containsKey(key.toString());
    }
    /**
     * creates an Iterator objects used to iterte over the collection
     * @return returns a CustomerCollectionIterator
     */
    public CustomerCollectionIterator createIterator(){
            return new CustomerCollectionIterator(this.customers);
    }

    /**
     * returns the current size of the Collection
     * @return size of the Collection
     */
    public int size(){
        return this.customers.size();
    }
    /**
     * read Bank Customers 4.csv and populates an HashMap of Customers
     * @return HashMap containing Customer Objects
     */
    private void populateCustomers(){
        //File object so I can read with Scanner
        File customerInfoFile = new File("src/Read_Only_Files/Bank Customers 5.csv");
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(customerInfoFile);
        }
        catch (FileNotFoundException e){
            System.out.println("Error: Cannot find src/Read_Only_Files/Bank Customers 5.csv");
            System.exit(1);
        }
        //use the header to dynamically read csv file
        String[] header = fileReader.nextLine().split(",");
        int[] headerIndexes = new int[header.length];
        //setting up the indexes based on the header ordering
        for(int i = 0;i < header.length;i++) {
            if(header[i].equals("ID")){
                headerIndexes[0] = i;
            } else if(header[i].equals("PIN")){
                headerIndexes[1] = i;
            } else if(header[i].equals("First Name")){
                headerIndexes[2] = i;
            } else if(header[i].equals("Last Name")){
                headerIndexes[3] = i;
            } else if(header[i].equals("Address Checking Balance")){
                headerIndexes[4] = i;
            } else if(header[i].equals("City")){
                headerIndexes[5] = i;
            } else if(header[i].equals("State")){
                headerIndexes[6] = i;
            } else if(header[i].equals("Zip")){
                headerIndexes[7] = i;
            } else if(header[i].equals("Phone Number")){
                headerIndexes[8] = i;
            } else if(header[i].equals("Date of Birth")){
                headerIndexes[9] = i;
            } else if(header[i].equals("Checking Account Number")){
                headerIndexes[10] = i;
            } else if(header[i].equals("Checking Balance")){
                headerIndexes[11] = i;
            } else if(header[i].equals("Savings Account Number")){
                headerIndexes[12] = i;
            } else if(header[i].equals("Savings Balance")){
                headerIndexes[13] = i;
            } else if(header[i].equals("Credit Account Number")){
                headerIndexes[14] = i;
            } else if(header[i].equals("Credit Balance")){
                headerIndexes[15] = i;
            } else if(header[i].equals("Credit Score")){
                headerIndexes[16] = i;
            } else if(header[i].equals("Credit Limit")){
                headerIndexes[17] = i;
            }
        }
        //add customer objects from file
        while(fileReader.hasNextLine()){
            createCustomer(fileReader.nextLine(),headerIndexes);//adds Customer object to the users list
        }
        fileReader.close();
    }
    /**
     * creates a Customer object based on the info
     * @param accInfo given information based on a csv we use it to split into correct data
     */
    private void createCustomer(String accInfo,int[] indexes) {
        String[] info = accInfo.split(",");
        //give names what info contains to properly identify because csv file is a mess
        //order accordingly to the csv file
        String state = info[indexes[6]];
        String city = info[indexes[5]];
        double cBalance = Double.parseDouble(info[indexes[15]]);
        String chNum = info[indexes[10]];
        String zip = info[indexes[7]];
        String fName = info[indexes[2]];
        int cLimit = Integer.parseInt(info[indexes[17]]);
        String lName = info[indexes[3]];
        double sBalance = Double.parseDouble(info[indexes[13]]);
        String phoneNum = info[indexes[8]];
        int id = Integer.parseInt(info[indexes[0]]);
        String pin = info[indexes[1]];
        String sNum = info[indexes[12]];
        String dob = info[indexes[9]];
        String cNum = info[indexes[14]];
        int cScore = Integer.parseInt(info[indexes[16]]);
        String add = info[indexes[4]];
        double chBalance = Double.parseDouble(info[indexes[11]]);
        //create account objects needed
        Checking chAcc =  new Checking(chNum,chBalance);
        Savings sAcc  = new Savings(sNum,sBalance);
        Credit cACC = new Credit(cNum,cBalance,cLimit,cScore);
        Customer cus = new Customer(fName,lName,add,city,state,zip,phoneNum,dob,id,chAcc,sAcc,cACC,pin);
        if(this.getMaxCustomerIDX() < id){
            this.setMaxCustomerIDX(id);
        }
        String key = handler.generateKey(fName,lName);
        //add the customer to the hash map
        this.customers.put(key,cus);
    }
    /**
     *gets the next id available
     * @return returns next biggest index
     */
    public int getMaxCustomerIDX(){
        return this.maxCustomerIDX + 1;
    }

    /**
     * sets the idx
     * @param idx current max index
     */
    public void setMaxCustomerIDX(int idx){
        this.maxCustomerIDX = idx;
    }
    /**
     * increments max id when new user is added
     */
    public void incrementMaxCustomerIDX(){
        this.maxCustomerIDX++;
    }
}
