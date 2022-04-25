import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * @author Arturo Olmos
 * @version 1.0
 * this class handles the manager menu
 */
public class ManagerMenu extends BankMenu{
    /**
     * creates a manager menu
     * @param scnr Scanner object for user input
     * @param customers customer collection
     * @param items item collection
     */
    public ManagerMenu(Scanner scnr,CustomerCollection customers,ItemCollection items){
        super(scnr,customers,items);
    }
    /**
     * this helps handle the manager interface
     */
    public void display(Customer userAccount){
        System.out.println("Hello Manager what would you like to do?(Enter:1-5)");
        //option used for action
        int moption;

        while(true){
            System.out.println("1.Inquire customer by name");
            System.out.println("2.Inquire customer by account type and number");
            System.out.println("3.Execute transactions");
            System.out.println("4.Create Bank Statement");
            System.out.println("5.Logout");
            //ensure a proper action happens
            try{
                moption = Integer.parseInt(super.getUserInput().nextLine());
            }
            catch(Exception e){
                System.out.println("error enter an appropriate integer(1-5)");
                continue;
            }
            switch(moption){
                //search by name
                case 1:
                    System.out.println("################################################################################");
                    System.out.println("Enter a name: ");

                    this.managerInquire(super.getUserInput().nextLine());
                    System.out.println("################################################################################");
                    break;
                //search by account
                case 2:
                    System.out.println("################################################################################");
                    System.out.println("Which type of account are you looking for?(1-3)");
                    System.out.println("1.Checking");
                    System.out.println("2.Savings");
                    System.out.println("3.Credit");
                    int op = -1;
                    //ensure correct input happens
                    while((op < 1 || op > 3)){
                        while(true){
                            try{
                                op = Integer.parseInt(super.getUserInput().nextLine());
                            }
                            catch(Exception e){
                                System.out.println("Choose appropriate option(1-3)");
                                continue;
                            }
                            break;
                        }
                        if((op < 1 || op > 3)) {
                            System.out.println("Choose appropriate option(1-3)");
                        }
                    }
                    System.out.println("Provide the number of the account");
                    String num = super.getUserInput().nextLine();
                    this.managerInquire(op,num);
                    System.out.println("################################################################################");
                    break;
                //execute actions.csv file
                case 3:
                    System.out.println("################################################################################");
                    System.out.println("Are you sure you want to execute transactions?(Y/N)");
                    System.out.println("Action can take a while");
                    String cont = super.getUserInput().nextLine();
                    while(!cont.equalsIgnoreCase("N") && !cont.equalsIgnoreCase("y")){
                        System.out.println("Please enter Y or N");
                        cont = super.getUserInput().nextLine();
                    }
                    if(cont.equalsIgnoreCase("n")){
                        System.out.println("Execution aborted");
                        System.out.println("################################################################################");
                        continue;
                    }
                    // at this pin the actions are executed in another thread
                    System.out.println("################################################################################");
                    System.out.println("YOU HAVE BEEN WARNED");
                    //execute the actions in half in parallel to execute faster
                    this.execTransactions();
                    System.out.println("################################################################################");
                    break;
                //create bank statement for a user
                case 4:
                    System.out.println("################################################################################");
                    System.out.println("How would you like to generate the bank statement by?");
                    System.out.println("1.Name");
                    System.out.println("2.ID");
                    System.out.println("3.Abort");
                    int option;
                    while(true){
                        try {
                            option = Integer.parseInt(super.getUserInput().nextLine());
                        }
                        catch (Exception e){
                            System.out.println("Please choose an appropriate option 1-3");
                            continue;
                        }
                        if(option != 1 && option != 2 && option != 3){
                            System.out.println("Please choose an appropriate option 1-3");
                            continue;
                        }
                        break;
                    }
                    //pointers used inside the switch cases
                    Customer cus;
                    String statement;
                    switch(option){
                        //by name
                        case 1:
                            System.out.println("Please enter a name");
                            //get name
                            String name = super.getUserInput().nextLine();
                            //create a key
                            String key = super.getCustomers().generateKey("",name);
                            //check customer exists
                            if(!super.getCustomers().hasKey(key)){
                                System.out.println("No user found, cannot generate bank statement");
                                System.out.println("################################################################################");
                                continue;
                            }
                            //generate key by name
                            cus = super.getCustomers().get(key);
                            if(name.split("\\s+").length != (cus.getFirstName() + " " + cus.getLastName()).split("\\s+").length){
                                System.out.println("no users found");
                                System.out.println("################################################################################");
                                continue;
                            }
                            //generate bank statement
                            statement = this.generateBankStatement(cus);
                            if( statement != null){
                                this.writeBankStatement(cus,statement);
                            }
                            break;
                        //by id
                        case 2:
                            System.out.println("Please enter an ID");
                            int id;
                            //ensure id is an integer
                            while(true){
                                try {
                                    id = Integer.parseInt(super.getUserInput().nextLine());
                                }
                                catch (Exception e){
                                    System.out.println("Please choose an appropriate option");
                                    continue;
                                }
                                break;
                            }
                            //ensure id is in range
                            if(id < 1 || id > super.getCustomers().size()){
                                System.out.println("ID is out of range");
                                System.out.println("################################################################################");

                                continue;
                            }
                            //search for customer by id
                            CustomerCollectionIterator newIter = this.getCustomers().createIterator();
                            cus = null;
                            while(newIter.hasNext()){
                                Customer temp = null;
                                try {
                                    temp = newIter.next();
                                }catch (IndexOutOfBoundsException e){
                                    System.out.println(e.getMessage());
                                }

                                if(id == temp.getID()){
                                    cus = temp;
                                    break;
                                }
                            }
                            //if the customer is not null it was found
                            if(cus != null){
                                statement = this.generateBankStatement(cus);
                                if( statement != null){//check the statement is not null
                                    this.writeBankStatement(cus,statement);
                                }
                            }
                            else {
                                System.out.println("No user found, cannot generate bank statement");
                                System.out.println("################################################################################");
                            }
                            break;
                        case 3:
                            System.out.println("Bank statement aborted");
                            break;
                        default:
                            //at this point something went gone wrong
                            System.out.println("Please enter 1-3");
                            break;
                    }
                    System.out.println("################################################################################");
                    break;
                //exit
                case 5:
                    System.out.println("################################################################################");
                    System.out.println("Thank you manager have a great day!");
                    System.out.println("Login out in");
                    for(int i = 3;i > 0;i--){
                        System.out.println(i);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            //nothing crazy should happen here
                            e.printStackTrace();
                        }
                    }
                    return;
                //not a proper option chosen
                default:
                    System.out.println("Error: enter an appropriate integer(1-5)");
                    continue;
            }
            //ask what the manager wants to do next
            System.out.println("What else would you like to do today?");
        }
    }
    /**
     * handles the bank statement creation
     * @param cus customers whose statement will be written for
     * @param statement the statement of the customer
     */
    private void writeBankStatement(Customer cus, String statement){
        try {
            String path = String.format("src/Generated_Files/%s_%s_Bank_Statement.txt",cus.getFirstName(),cus.getLastName());
            File f = new File(path);
            if(!f.exists()){
                f.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(statement);
            writer.close();
        }
        catch (Exception e){
            System.out.println("Something went wrong");
        }
    }
    /**
     * method handles inquire if an account by account type and number
     * @param type integer that describes account type
     * @param number the account number used for searching
     */
    //this one below handles id manager is looking by account
    private void managerInquire(int type,String number){
        System.out.println("The following accounts where found given the name");
        System.out.println("#################################################");
        int c = 0;
        CustomerCollectionIterator customerCollectionIterator = super.getCustomers().createIterator();
        //based on the account type 1 is checking 2 is savings 3 is credit
        //based on the type method searched for the account number
        //Check Customer toString method for more detail
        // in general everything abut a Customer is printed
        if(type == 1){//this is node for checking
            while(customerCollectionIterator.hasNext()){
                Customer cus = null;
                try {
                    cus = customerCollectionIterator.next();
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                }
                Checking ch = cus.getCheck();
                if(this.strNWS(ch.getAccNum()).equals(this.strNWS(number))){
                    //ensure string is of proper length
                    if(ch.getAccNum().split("\\s+").length != number.split("\\s+").length){
                        System.out.println("no users found");
                        return;
                    }
                    System.out.println(cus);
                    System.out.println("Items bought");
                    System.out.println("############");
                    System.out.println(cus.getAllItemsBought());
                    System.out.println("Time of purchased items");
                    System.out.println("#######################");
                    System.out.println(cus.getAllTransactions());
                    System.out.println("Total Money Spent at Miners Mall");
                    System.out.println("#################################");
                    System.out.printf("%.2f$\n",cus.getTotalMoneySpent());
                    c++;
                }
            }
        } else if (type == 2) {//this is done for savings
            while(customerCollectionIterator.hasNext()){
                Customer cus = null;
                try {
                    cus = customerCollectionIterator.next();
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                }
                Savings s = cus.getSave();
                //print info if number matches
                if(this.strNWS(s.getAccNum()).equals(this.strNWS(number))){
                    //ensure string is of proper length
                    if(s.getAccNum().split("\\s+").length != number.split("\\s+").length){
                        System.out.println("no users found");
                        return;
                    }
                    System.out.println(cus);
                    System.out.println("Items bought");
                    System.out.println("############");
                    System.out.println(cus.getAllItemsBought());
                    System.out.println("Time of purchased items");
                    System.out.println("#######################");
                    System.out.println(cus.getAllTransactions());
                    System.out.println("Total Money Spent at Miners Mall");
                    System.out.println("#################################");
                    System.out.printf("%.2f$\n",cus.getTotalMoneySpent());
                    c++;
                }
            }
        }else if (type == 3) {//this is done for credit
            //search for the customer based on their info
            while(customerCollectionIterator.hasNext()){
                Customer cus = null;
                try {
                    cus = customerCollectionIterator.next();
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                }
                //get temp customer
                Credit cr = cus.getCredit();
                //if number matches print their info
                if(this.strNWS(cr.getAccNum()).equals(this.strNWS(number))){
                    //ensure string is of proper length
                    if(cr.getAccNum().split("\\s+").length != number.split("\\s+").length){
                        System.out.println("no users found");
                        return;
                    }
                    System.out.println(cus);
                    System.out.println("Items bought");
                    System.out.println("############");
                    System.out.println(cus.getAllItemsBought());
                    System.out.println("Time of purchased items");
                    System.out.println("#######################");
                    System.out.println(cus.getAllTransactions());
                    System.out.println("Total Money Spent at Miners Mall");
                    System.out.println("################################");
                    System.out.printf("%.2f$\n",cus.getTotalMoneySpent());
                    c++;
                }
            }
        }
        if(c == 0){
            //at this point there is no user found, so we let user know
            System.out.println("No user with the provided number found");
        }
    }
    /**
     * method handles inquire if an account by name
     * @param name the name used for searching
     */
    private void managerInquire(String name){
        System.out.println("The following accounts where found given the name");
        System.out.println("#################################################");

        if(name.length() <= 0){
            System.out.println("Error: not enough info provided");
            return;
        }

        String key = super.getCustomers().generateKey("",name);
        if(!super.getCustomers().hasKey(key)){
            System.out.println("No user with the provided name found");
            return;
        }
        Customer cus = super.getCustomers().get(key);
        //ensure the name is not split somewhere in the string
        if(name.split("\\s+").length != (cus.getFirstName() + " " + cus.getLastName()).split("\\s+").length){
            System.out.println("no users found");
            return;
        }
        //Check Customer toString method
        // in general everything abut a Customer is printed
        System.out.println(cus);
        System.out.println("Items bought");
        System.out.println("############");
        System.out.println(cus.getAllItemsBought());
        System.out.println("Time of purchase and item purchased ");
        System.out.println("###################################");
        System.out.println(cus.getAllTransactions());
        System.out.println("Total Money Spent at Miners Mall");
        System.out.println("#################################");
        System.out.printf("%.2f$\n",cus.getTotalMoneySpent());
    }
    /**
     * helper function deals with the buy transaction
     * @param cus customer who will buy
     * @param transaction information about the transaction
     */
    private void buyProcedure(Customer cus,Object[] transaction){
        //check if the item exists within the hash map
        if(!super.getItems().hasKey(Integer.parseInt(transaction[8].toString()))){
            System.out.println("Error: no item with index found");
            return;
        }
        //get the item based on the file
        Item item = super.getItems().get(Integer.parseInt(transaction[8].toString()));
        //ensure the description of the item matches the id
        if(!this.strNWS(item.getName()).equalsIgnoreCase(this.strNWS(transaction[9].toString()))){
            System.out.println("Error: item id and description do not match");
            return;
        }
        //do the transaction process and check if it was successful, if it was not then continue
        //the buy method will print the error
        String accType = transaction[2].toString();
        // the method updates the customer info
        try{
            super.getTransactionHandler().buyFromMinerMall(cus,accType,item.getPrice());
        }catch (TransactionException eBuy){
            System.out.println(eBuy.getMessage());
            return;
        }
        //at this point everything went well so i then just proceed log everything
        String fString = String.format("%s %s purchased a %s for %.2f$ from miners bank using their %s account at %s\n", cus.getFirstName(), cus.getLastName(), item.getName(), item.getPrice(), transaction[2].toString(), super.getTime().format(LocalDateTime.now()));
        //updates transactions made by the user
        cus.addTransaction(fString);
        //update the total money spent
        cus.setTotalMoneySpent(cus.getTotalMoneySpent() + item.getPrice());
        //update items
        cus.addItemBought(item.getName());
        //log to file
        super.logToFile(fString);
    }
    /**
     * helper function that does the procedure of a deposit
     * @param transaction transaction being executed
     */
    private void depositProcedure(Object[] transaction){
        //get the customer who will have money deposited to their account
        String key  = super.getCustomers().generateKey(transaction[4].toString(),transaction[5].toString());
        //check if customer exists
        if(!super.getCustomers().hasKey(key)){
            System.out.println("No customer exist");
            return;
        }
        //get the customer if they exist
        Customer depositDestination = super.getCustomers().get(key);
        //set start time and balances

        if (depositDestination.getSessionStart() == null){
            depositDestination.setSessionStart(super.getTime().format(LocalDateTime.now()));
            depositDestination.setStartCheckBal(depositDestination.getCheck().getBalance());
            depositDestination.setStartSaveBal(depositDestination.getSave().getBalance());
            depositDestination.setStartCreditBal(depositDestination.getCredit().getBalance());
        }
        //try the deposit
        try{
            super.getTransactionHandler().userDeposit(depositDestination,transaction[6].toString(),Double.parseDouble(transaction[7].toString()));
        }catch (TransactionException eDep){
            System.out.println(eDep.getMessage());
            return;
        }
        //if everything goes log happens
        String fString = String.format("%s %s deposited %.2f$ from their %s account at %s\n",depositDestination.getFirstName(),depositDestination.getLastName(),Double.parseDouble(transaction[7].toString()),transaction[6].toString(),super.getTime().format(LocalDateTime.now()));
        depositDestination.addTransaction(fString);
		super.logToFile(fString);
        //set the end of the session
        depositDestination.setSesstionEnd(super.getTime().format(LocalDateTime.now()));
    }
    /**
     * helper method does the pay procedure
     * @param cus the customer who will pay
     * @param transaction array holding information about the transaction
     */
    private void payProcedure(Customer cus,Object[] transaction){
        String key  = super.getCustomers().generateKey(transaction[4].toString(),transaction[5].toString());
        //check if customer exists
        if(!super.getCustomers().hasKey(key)){
            System.out.println("No customer exist");
            return;
        }
        //get the customer if they exist
        Customer customerToPay = super.getCustomers().get(key);
        //see if the transaction was successful
        //if not then just return from the method
        try{
            super.getTransactionHandler().transactionToOther(cus,customerToPay,transaction[2].toString(),transaction[6].toString(),Double.parseDouble(transaction[7].toString()));
        }catch (TransactionException ePay){
            System.out.println(ePay.getMessage());
            return;
        }
        //at this point everything went well so just log everything
        //formatted string to be logged
        String fString = String.format("%s %s paid %.2f$ from their %s account to %s %s into their %s account at %s\n",cus.getFirstName(),cus.getLastName(),Double.parseDouble(transaction[7].toString()),transaction[2].toString(),customerToPay.getFirstName(),customerToPay.getLastName(),transaction[6].toString(),super.getTime().format(LocalDateTime.now()));
        super.logToFile(fString);
        //log transaction to customer
        cus.addTransaction(fString);
    }
    /**
     * executes the transactions in actions.csv file
     */
    private void execTransactions(){
        File transactionFile = new File("src/Read_Only_Files/actions PA5.csv");
        Scanner transactionsReader = null;
        try {
            transactionsReader = new Scanner(transactionFile);
        }
        catch (FileNotFoundException e){
            System.out.println("Error: File actions PA5.csv not found, cannot continue");
            System.exit(1);
        }
        if(!transactionsReader.hasNextLine()){
            System.out.println("Error: actions PA5.csv is empty");
            System.exit(1);
        }
        //get rid of header
        transactionsReader.nextLine();
        //execute the transactions as we read from the file
        while(transactionsReader.hasNextLine()){
            //while reading from the file I add the array containing the information of the transactions

            Object[] transaction = transactionsReader.nextLine().split(",");
            //generate key based on the name
            String key = super.getCustomers().generateKey(transaction[0].toString(),transaction[1].toString());
            //check if the customer exists within the hashmap
            //and check that action is not a deposit
            //get the customer
            Customer cus = null;
            if(super.getCustomers().hasKey(key)){
                cus = super.getCustomers().get(key);
            }
            //check if customer was set
            if(cus != null){
                //set a session start if it was previously null
                if(cus.getSessionStart() == null){
                    cus.setSessionStart(super.getTime().format(LocalDateTime.now()));
                    cus.setStartCheckBal(cus.getCheck().getBalance());
                    cus.setStartSaveBal(cus.getSave().getBalance());
                    cus.setStartCreditBal(cus.getCredit().getBalance());
                }
            }
            if(transaction[3].toString().equals("Buy")){
                //do the buy item procedure
                buyProcedure(cus,transaction);
            }
            else if(transaction[3].toString().equals("Pay")){
                //look at the pay procedure to see how it is handled
                payProcedure(cus,transaction);
            }
            else if(transaction[3].toString().equals("Deposit")){
                depositProcedure(transaction);
            }
            else{
                System.out.println("No Action found");
            }
            //set a session end of the customer
            //as well as the ending balance
            if(cus != null) {
                cus.setSesstionEnd(super.getTime().format(LocalDateTime.now()));
                cus.setEndCheckBal(cus.getCheck().getBalance());
                cus.setEndSaveBal(cus.getSave().getBalance());
                cus.setEndCreditBal(cus.getCredit().getBalance());
            }
        }
    }
    /**
     * generates the bank statement
     * @param cus customer whose transaction will be written
     * @return returns a formatted string for the bank statement
     */
    private String generateBankStatement(Customer cus){
        String statement = cus.toString() + "\n###############################################\n";

        if(cus.getSessionStart() != null){
            statement += "\nSession Start: " + cus.getSessionStart() + "\n";
            statement += String.format("Starting Checking Balance: %.2f$\n",cus.getStartCheckBal());
            statement += String.format("Starting Savings Balance: %.2f$\n",cus.getStartSaveBal());
            statement += String.format("Starting Credit Balance: %.2f$\n",cus.getStartCreditBal());
            statement += "\n###############################################\n";
            statement += "Session End: " + cus.getSesstionEnd();
            statement += String.format("\nEnding Checking Balance: %.2f$\n",cus.getEndCheckBal());
            statement += String.format("Ending Savings Balance: %.2f$\n",cus.getEndSaveBal());
            statement += String.format("Ending Credit Balance: %.2f$\n",cus.getEndCreditBal());

            statement += "\n###############################################\n";
            statement += String.format("Total money spent at Miners Mall: %.2f$",cus.getTotalMoneySpent());
            statement += "\n###############################################\n";
            statement += "Account Activity\n################\n";
            for(int i = 0;i < cus.getTransactions().size();i++){
                statement += cus.getTransactions().get(i) + "\n";
            }
            statement += "\n###############################################\n";
        }
        else {
            statement += "\n No session started yet";
        }
        return statement;


    }
}
