import java.io.*;
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
    private int maxCustomerIDX;
    /**
     * Constructor- sets the data structure
     */
    public CustomerCollection(){
        maxCustomerIDX = 0;
        this.customers = new HashMap<>();
        populate();
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
     */
    public void populate(){
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
        String key = this.generateKey(fName,lName);
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
    /**
     * updates the Bank Customers.csv file
     */
    public void updateCSVFile(){
        HashMap<Integer,Customer> integerMapped = new HashMap<>();
        CustomerCollectionIterator customerCollectionIterator = this.createIterator();
        while (customerCollectionIterator.hasNext()){
            Customer cus = customerCollectionIterator.next();
            integerMapped.put(cus.getID(),cus);
        }
        while(true){
            try{
                BufferedWriter updateFileInfo =  new BufferedWriter(new FileWriter("src/Generated_Files/Updated Customers Sheet.csv",false));
                //header of the file to keep data organized
                //we rewrite the file based on updates
                updateFileInfo.write("ID,Pin,First Name,Last Name,Address,City,State,Zip,Phone Number,Date of Birth,Checking Account Number,Checking Balance,Savings Account Number,Savings Balance,Credit Account Number,Credit Balance,Credit Score,Credit Limit\n");
                for(int i = 1;i < integerMapped.size() + 1;i++){
                    Customer user = integerMapped.get(i);
                    updateFileInfo.write(user.getID()+","+ user.getPin()+","+user.getFirstName()+","+user.getLastName()+","+user.getAddress()+","+user.getCity()+","+user.getState()+","+user.getZipCode()+","+user.getPhoneNum()+","+user.getDOB() + "," + user.getCheck().getAccNum() + "," + user.getCheck().getBalance() + "," + user.getSave().getAccNum() + "," + user.getSave().getBalance() + "," + user.getCredit().getAccNum() + "," + user.getCredit().getBalance()+ "," + user.getCredit().getScore()+ "," + user.getCredit().getLimit() + "\n");
                }
                updateFileInfo.close();
            } catch (IOException e){
                //if the file is open then we let user know to close it and give them some time to close it
                System.out.println("ERROR: Please close the file src/Generated_Files/Updated Customers Sheet.csv");
                System.out.println("Data might not have been updated!");
                System.out.println("We will resume in three seconds, please close by then");
                System.out.println("-------------------------------------------------");
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException ex){
                    //honestly nothing should happen
                    ex.printStackTrace();
                }
                continue;
            }
            break;
        }
    }
    /**
     * generates a key for the hash map
     * @param fName the first name of the user
     * @param lName the last name of the user
     * @return returns a generated string based on the users first and last name
     */
    public String generateKey(String fName,String lName){
        String nwsFName = "";
        String nwsLName = "";
        String[] fnameNWS = fName.split("\\s+");
        for(int i = 0;i < fnameNWS.length;i++){
            nwsFName += fnameNWS[i];
        }
        String[] lnameNWS = lName.split("\\s+");
        for(int i = 0;i < lnameNWS.length;i++){
            nwsLName += lnameNWS[i];
        }
        return nwsFName + nwsLName;
    }
}
