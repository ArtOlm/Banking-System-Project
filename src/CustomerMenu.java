import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Arturo Olmos/Jaehyeon Park
 * @version 4.0
 * Class is used to handle the user menu
 */
public class CustomerMenu extends BankMenu{
    //string pointer user to log the transaction
    private String transactionLog = "";
    //format to the date
    final private DecimalFormat df = new DecimalFormat("#.##");
    /**
     * crates a complete user menu
     * @param scnr user input
     * @param customers customer collection
     * @param items items collection
     */
    public CustomerMenu(Scanner scnr, CustomerCollection customers, ItemCollection items){
        super(scnr,customers,items);
        df.setRoundingMode(RoundingMode.DOWN);
    }
    /**
     * this helps handle the customer interface
     */
    public void display(Customer userAccount){
        //pointer to keys which is used to index into the hashmap
        String key;
        //ask user for their information which is how they will be identified
        boolean menuon = true;
        while(menuon){
                //sets the time of login for that user
                //also set the starting balances
                if(userAccount.getSessionStart() == null){
                    userAccount.setSessionStart(super.getTime().format(LocalDateTime.now()));
                    userAccount.setStartCheckBal(userAccount.getCheck().getBalance());
                    userAccount.setStartSaveBal(userAccount.getSave().getBalance());
                    userAccount.setStartCreditBal(userAccount.getCredit().getBalance());
                }
                System.out.println("Hello " + userAccount.getFirstName() + " " + userAccount.getLastName() + " what would you like to do today?(Enter 1-7)");
                //string pointers is used later on to point to account chosen by user
                String accType;
                String from;
                String to;
                //control variable used to exit main while loop when user wants to stop running code
                boolean onOff = true;
                while(onOff){
                    //menu for the user to make decision on what they want to do
                    System.out.println("1.Inquire balance");
                    System.out.println("2.Deposit");
                    System.out.println("3.Transfer from an account to another");
                    System.out.println("4.Withdraw");
                    System.out.println("5.Pay another user");
                    System.out.println("6.Go to Miners Mall");
                    System.out.println("7.Logout");
                    int option;
                    //ensure the user chooses and appropriate option
                    while(true){
                        try{
                            option = Integer.parseInt(super.getUserInput().nextLine());
                        }
                        catch(Exception e){
                            System.out.println("################################################################################");
                            System.out.println("Please choose an appropriate option(1-7)");
                            continue;
                        }
                        break;
                    }
                    //switch statement to do something based on decision
                    switch(option){
                        case 1://inquire procedure
                            System.out.println("################################################################################");
                            System.out.println("Enter the name of the account you would like to inquire?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = super.getUserInput().nextLine();
                            //ensure proper account is chosen
                            while(!accType.equalsIgnoreCase("Checking") && !accType.equalsIgnoreCase("Savings") && !accType.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = super.getUserInput().nextLine();
                            }
                            //let user know of success
                            try {
                                String amm = df.format(super.getTransactionHandler().checkBalance(userAccount, accType));
                                System.out.printf("%s currently has $%s\n", accType,amm);
                            }catch (Exception iq){
                                System.out.println(iq.getMessage());
                                System.out.println("Returning to menu");
                                System.out.println("################################################################################");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            //log what happened
                            transactionLog = String.format("%s %s inquired their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),accType,super.getTime().format(LocalDateTime.now()));
                            super.logToFile(transactionLog);

                            break;
                        case 2://deposit procedure
                            System.out.println("################################################################################");
                            System.out.println("Enter the name of the account would you like to deposit to?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = super.getUserInput().nextLine();
                            //ensure proper account is chosen
                            while(!accType.equalsIgnoreCase("Checking") && !accType.equalsIgnoreCase("Savings") && !accType.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = super.getUserInput().nextLine();
                            }
                            System.out.println("Please enter the amount you would like to Deposit:(DO NOT USE COMMA)");
                            double deposit;
                            //ensure that entered value is acceptable
                            while(true){
                                try{
                                    deposit = Double.parseDouble(super.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Error: not a proper value");
                                    continue;
                                }
                                break;
                            }
                            try {
                                super.getTransactionHandler().userDeposit(userAccount,accType,deposit);
                            }
                            catch(TransactionException eDep){
                                //Transaction was a failure
                                System.out.println(eDep.getMessage());
                                System.out.println("Returning to menu");
                                System.out.println("################################################################################");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            //if success then we tell user and log it
                            transactionLog = String.format("%s %s deposited $%s from their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),df.format(deposit),accType,super.getTime().format(LocalDateTime.now()));
                            userAccount.addTransaction(transactionLog);
                            super.logToFile(transactionLog);
                            System.out.printf("The deposit of $%s into %s was a success!\n",df.format(deposit),accType);
                            break;
                        case 3://transaction between two accounts of the same customer
                            System.out.println("################################################################################");
                            System.out.println("Please enter the name of the account you want to transfer from");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            from = super.getUserInput().nextLine();
                            while(!from.equalsIgnoreCase("Checking") && !from.equalsIgnoreCase("Savings") && !from.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                from = super.getUserInput().nextLine();
                            }
                            System.out.println("Please enter the name of the account you want to transfer to");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            to = super.getUserInput().nextLine();
                            while(!to.equalsIgnoreCase("Checking") && !to.equalsIgnoreCase("Savings") && !to.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                to = super.getUserInput().nextLine();
                            }
                            System.out.println("How much would you like to transfer?(Do not include comma)");
                            double transfer;
                            while(true){
                                try{
                                    transfer = Double.parseDouble(super.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Please enter a proper amount(e.g 100.05)");
                                    continue;
                                }
                                if(transfer < 0){
                                    System.out.println("No negative values allowed please enter a positive value");
                                    continue;
                                }
                                break;
                            }
                            try {
                                super.getTransactionHandler().accountTransfer(userAccount,from,to,transfer);
                            }
                            catch (TransactionException eTransfer){
                                System.out.println(eTransfer.getMessage());
                                System.out.println("Returning to menu");
                                System.out.println("################################################################################");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            //if success then we tell user and log it
                            String transferStr = df.format(transfer);
                            System.out.printf("The transfer was a success, $%s was transferred from %s to %s\n",transferStr,from,to);
                            transactionLog = String.format("%s %s transferred $%s from %s to %s at %s\n",userAccount.getFirstName(),userAccount.getLastName(),transferStr,from,to,super.getTime().format(LocalDateTime.now()));
                            userAccount.addTransaction(transactionLog);
                            super.logToFile(transactionLog);
                            break;
                        case 4://withdraw procedure
                            System.out.println("################################################################################");
                            System.out.println("Enter the name of the account would you like to withdraw from?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = super.getUserInput().nextLine();
                            while(!accType.equalsIgnoreCase("Checking") && !accType.equalsIgnoreCase("Savings") && !accType.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = super.getUserInput().nextLine();
                            }
                            System.out.println("Please enter the amount you would like to withdraw:(DO NOT USE COMMA)");
                            double withdrawl;
                            //ensure that entered value is acceptable
                            while(true){
                                try{
                                    withdrawl = Double.parseDouble(super.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Error: not a proper value");
                                    continue;
                                }
                                break;
                            }
                            try{
                                super.getTransactionHandler().userWithdraw(userAccount,accType,withdrawl);
                            }
                            catch(TransactionException eWith){
                                //there was transaction failure
                                System.out.println(eWith.getMessage());
                                System.out.println("Returning to menu");
                                System.out.println("################################################################################");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            //if success then we tell user and log it
                            String withStr = df.format(withdrawl);
                            transactionLog = String.format("%s %s withdrew $%s from the %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),withStr,accType,super.getTime().format(LocalDateTime.now()));
                            System.out.printf("The withdrawl of $%s was a success\n",withStr);
                            userAccount.addTransaction(transactionLog);
                            super.logToFile(transactionLog);
                            break;
                        case 5://pay another user procedure
                            System.out.println("################################################################################");
                            System.out.println("Please enter the name of the account you want to pay from");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            from = super.getUserInput().nextLine();
                            //ensure a proper account is entered
                            while(!from.equalsIgnoreCase("Checking") && !from.equalsIgnoreCase("Savings") && !from.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                from = super.getUserInput().nextLine();
                            }
                            System.out.println("How much would you like to pay?(Do not include comma)");
                            double pay;
                            //ensure proper pay is entered
                            while(true){
                                try{
                                    pay = Double.parseDouble(super.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Please enter a proper amount(e.g 100.05)");
                                    continue;
                                }
                                if(pay < 0){
                                    System.out.println("No negative values allowed please enter a positive value");
                                    continue;
                                }
                                break;
                            }
                            System.out.println("Enter the following information about the user you would like to pay");
                            System.out.print("First Name: ");
                            String userToPayFirstName = super.getUserInput().nextLine();
                            System.out.print("Last Name: ");
                            String userToPayLastName = super.getUserInput().nextLine();
                            int userToPayID;
                            System.out.print("ID: ");
                            //ensure a proper id is entered
                            while(true){
                                try{
                                    userToPayID = Integer.parseInt(super.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Please enter an integer");
                                    continue;
                                }
                                if(userToPayID < 0 || userToPayID > super.getCustomers().size()){
                                    System.out.println("Not a valid ID");
                                }
                                break;
                            }
                            //check to see credentials are not to the same user
                            if(credentialValid(userAccount,userToPayFirstName,userToPayLastName,userToPayID)){
                                System.out.println("Error: cannot pay yourself");
                                System.out.println("returning to menu.....");
                                System.out.println("################################################################################");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            System.out.println("Please enter the name of the account you want to pay to");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            to = super.getUserInput().nextLine();
                            //ensure a proper option
                            while(!to.equalsIgnoreCase("Checking") && !to.equalsIgnoreCase("Savings") && !to.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                to = super.getUserInput().nextLine();

                            }
                            //ensure a customer with the info exists
                            if(credentialValid(userToPayFirstName,userToPayLastName,userToPayID)){
                                key = super.getCustomers().generateKey(userToPayFirstName,userToPayLastName);
                                Customer userToPay = super.getCustomers().get(key);
                                try{
                                    super.getTransactionHandler().transactionToOther(userAccount,userToPay,from,to,pay);
                                }
                                catch(TransactionException eTransfer){
                                    //there was transaction failure
                                    System.out.println(eTransfer.getMessage());
                                    System.out.println("Returning to menu");
                                    System.out.println("################################################################################");
                                    System.out.println("Please choose an option");
                                    continue;
                                }
                                String payStr = df.format(pay);
                                //if success then we tell user and log it
                                System.out.printf("payment of $%s from %s account to %s %s into their %s account was a success\n",payStr,from,userToPay.getFirstName(),userToPay.getLastName(),to);
                                transactionLog = String.format("%s %s paid $%s from their %s account to %s %s into their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),payStr,from,userToPay.getFirstName(),userToPay.getLastName(),to,super.getTime().format(LocalDateTime.now()));
                                userAccount.addTransaction(transactionLog);
                                super.logToFile(transactionLog);
                            }
                            else{
                                System.out.println("Error: no user found with provided information");
                            }
                            break;
                        case 6://buy
                            //control variable used for outer loop
                            boolean outerMenu = true;
                            //control variable used for outer loop
                            boolean malMenu;
                            //option control
                            int mallOpt;
                            //control used to ensure user views menu before purchasing item
                            boolean viewedMenu = false;
                            System.out.println("################################################################################");
                            //print all items
                            System.out.println("Hello Welcome to Miners Mall");
                            while(outerMenu){
                                System.out.println("What would you like to do?(1-3)");
                                System.out.println("1.View Items Menu");
                                System.out.println("2.Buy Items");
                                System.out.println("3.Exit mall");
                                //get the option and ensure it is appropriate
                                while (true) {
                                    String buyOption = super.getUserInput().nextLine();
                                    try {
                                        mallOpt = Integer.parseInt(buyOption);
                                    } catch (Exception e) {
                                        System.out.println("Please choose an appropriate option(1-3)");
                                        continue;
                                    }
                                    if (mallOpt < 1 || mallOpt > 3) {
                                        System.out.println("Please choose an appropriate option(1-3)");
                                        continue;
                                    }
                                    else if(mallOpt == 2 && !viewedMenu){
                                        System.out.println("Please view the menu at least once first before purchasing an Item");
                                        continue;
                                    }
                                    break;
                                }
                                switch (mallOpt){
                                    //print the menu
                                    case 1:
                                        viewedMenu = true;
                                        printItemMenu();
                                        break;
                                    //buy item
                                    case 2:
                                        ArrayList<Item> cart = new ArrayList<>();
                                        malMenu = true;
                                        System.out.println("################################################################################");
                                        String line = null;
                                        //keep track of how many items are available
                                        HashMap<Integer,Integer> maxCount = new HashMap<>();
                                        ItemCollectionIterator iter = this.getItems().createIterator();
                                        while(iter.hasNext()){
                                            Item it = iter.next();
                                            maxCount.put(it.getID(),it.getMax());
                                        }
                                        //the total money spent
                                        double total = 0;
                                        while(malMenu) {
                                            //get item user wants
                                            System.out.println("Enter the ID of the item you would like to add to the cart\n(Enter \"C\" to checkout or \"V\" to view cart or \"R\" to remove an Item from the cart or \"E\" to stop purchase) ");
                                            int mallID = -1;
                                            line = super.getUserInput().nextLine();
                                            try{
                                                mallID = Integer.parseInt(line);
                                            } catch (Exception me){
                                                //check if the option is e or c
                                                if(line.equalsIgnoreCase("c") || line.equalsIgnoreCase("e")) {
                                                    break;
                                                } else if(line.equalsIgnoreCase("R")){//remove the item from the cart
                                                    System.out.println("################################################################################");
                                                    System.out.println("What item do you want to remove?(Enter the ID)");
                                                    line = super.getUserInput().nextLine();
                                                    int rid = -1;
                                                    try{
                                                        rid = Integer.parseInt(line);
                                                    }catch (Exception q){
                                                        System.out.println("Error: not a proper id");
                                                        System.out.println("################################################################################");
                                                        continue;
                                                    }
                                                    if(rid < 0 || rid > super.getItems().size()){
                                                        System.out.println("Error: ID out of range");
                                                        System.out.println("################################################################################");
                                                        continue;
                                                    }
                                                    boolean didRemove = false;
                                                    for(int j = 0;j < cart.size();j++){
                                                        if(cart.get(j).getID() == rid){
                                                            didRemove = true;
                                                            total -= cart.get(j).getPrice();
                                                            cart.remove(j);
                                                            break;
                                                        }
                                                    }
                                                    if(!didRemove){
                                                        System.out.println("Item was not found in the cart");
                                                    }else {
                                                        maxCount.put(rid,maxCount.get(rid) + 1);
                                                        System.out.printf("Item was removed was remover from cart\n");
                                                    }
                                                    System.out.println("################################################################################");
                                                    continue;
                                                }else if(line.equalsIgnoreCase("V")){//print the contents of the cart
                                                    System.out.println("################################################################################");
                                                    if(cart.size() <= 0){
                                                        System.out.println("Your cart is empty");
                                                        System.out.println("################################################################################");
                                                        continue;
                                                    }
                                                    System.out.println("Your cart currently has");
                                                    for(int j = 0;j < cart.size() ;j++){
                                                        System.out.printf("Name: %s Price: %.2f$\n",cart.get(j).getName(),cart.get(j).getPrice());
                                                    }
                                                    System.out.printf("The current total is %.2f$\n",total);
                                                    System.out.println("################################################################################");
                                                    continue;
                                                }
                                                //else it is not valid
                                                System.out.println("Error: not a valid ID");
                                                System.out.println("################################################################################");
                                                continue;
                                            }
                                            //ensure the id is within range
                                            //check id is valid
                                            if((mallID  < 0) || (mallID > super.getItems().size())){
                                                System.out.println("Error: not a valid ID");
                                                System.out.println("################################################################################");
                                                continue;
                                            }
                                            //check if the item is available
                                            Item itemAdded = super.getItems().get(mallID);
                                            if(maxCount.get(itemAdded.getID()) < 1){
                                                System.out.println("Error: Item is no longer available");
                                                System.out.println("################################################################################");
                                                continue;
                                            }
                                            System.out.printf("How many %s would you like to add to you cart?(Enter 0 to not add any)\n",itemAdded.getName());
                                            int numIt = -1;
                                            while(true){
                                                try {
                                                    numIt = Integer.parseInt(super.getUserInput().nextLine());
                                                }catch (Exception eit){
                                                    System.out.println("Please enter a correct value");
                                                    continue;
                                                }
                                                if(numIt < 0 || numIt > maxCount.get(itemAdded.getID())){
                                                    System.out.printf("Error: Not within the amount range, there is only %d %s in stock\n",maxCount.get(itemAdded.getID()),itemAdded.getName());
                                                    System.out.println("################################################################################");
                                                    System.out.println("Enter an amount or 0 to not add any");
                                                    continue;
                                                }

                                                break;
                                            }
                                            double totalPerItem = 0;
                                            //add to cart and update the availability
                                            for(int j = 0;j < numIt;j++) {
                                                totalPerItem += itemAdded.getPrice();
                                                cart.add(itemAdded);
                                                maxCount.put(itemAdded.getID(), maxCount.get(itemAdded.getID()) - 1);
                                            }
                                            if(numIt == 0){
                                                System.out.println("No items added to cart");
                                                System.out.println("################################################################################");
                                            }else {
                                                total += totalPerItem;
                                                System.out.printf("Added %d %s to cart for a total of %.2f$\n",numIt,itemAdded.getName(),totalPerItem);
                                                System.out.printf("Current Total is: %.2f$\n",total);
                                                System.out.printf("There are currently %d %s left in stock\n",maxCount.get(itemAdded.getID()),itemAdded.getName());
                                                System.out.println("################################################################################");
                                            }

                                        }
                                        if(line.equalsIgnoreCase("c")){
                                            //ask with what account they want to pay with
                                            System.out.println("################################################################################");
                                            System.out.println("With which account would you like to pay?(Enter the name)");
                                            System.out.println("1.Checking");
                                            System.out.println("2.Credit");
                                            String accountType = super.getUserInput().nextLine();
                                            while (!accountType.equalsIgnoreCase("Checking") && !accountType.equalsIgnoreCase("Credit")){
                                                System.out.println("Please enter Checking or Credit");
                                             accountType = super.getUserInput().nextLine();
                                            }
                                            //if the user did not abort the payment

                                            //check if they can buy all the stuff in the cart
                                            try {
                                                super.getTransactionHandler().buyFromMinerMall(userAccount, accountType, total);
                                            } catch (Exception me) {
                                                System.out.println(me.getMessage());
                                                break;
                                            }
                                            //update the users info and tell them they succeeded in making the purchase
                                            String totalStr = df.format(total);
                                            System.out.printf("Your purchase of $%s at Miners mall was a success!\n", totalStr);
                                            System.out.println("Thank you!");
                                            for (int i = 0; i < cart.size(); i++) {
                                                Item t = cart.get(i);
                                                //update the limit on the items
                                                super.getItems().get(t.getID()).setMax(maxCount.get(t.getID()));
                                                //log everything they bought if successful
                                                transactionLog = String.format("%s %s bought %s for $%.2f using %s account at %s\n", userAccount.getFirstName(), userAccount.getLastName(), t.getName(), t.getPrice(), accountType,super.getTime().format(LocalDateTime.now()));
                                                super.logToFile(transactionLog);
                                                userAccount.addTransaction(transactionLog);
                                                userAccount.setTotalMoneySpent(userAccount.getTotalMoneySpent() + t.getPrice());
                                                userAccount.addItemBought(t.getName());
                                            }
                                        }else{
                                            //if they do not make a purchase
                                            System.out.println("No items purchased");
                                        }
                                        System.out.println("################################################################################");
                                        break;
                                    case 3:
                                        outerMenu = false;
                                        break;
                                    default:
                                        System.out.println("Please enter (1-3)");
                                        break;
                                }
                            }
                            break;
                        case 7:
                            //user logs out
                            System.out.println("################################################################################");
                            System.out.println("Thank you " + userAccount.getFirstName() + " " + userAccount.getLastName() + ", have a great day!");
                            System.out.println("Logging out in");
                            for(int i = 3;i > 0;i--){
                                System.out.println(i);
                                try {
                                    Thread.sleep(1000);
                                }catch (InterruptedException e){
                                    //nothing crazy should happen here
                                    e.printStackTrace();
                                }
                            }
                            //break does not work within the switch cases, so I used boolean to exit loop
                            onOff = false;
                            menuon = false;
                            //sets the ending session for the user and the balance for the session
                            //the balance can change if another user pays a customer
                            //thus we want to set the ending balance for this session
                            userAccount.setSesstionEnd(super.getTime().format(LocalDateTime.now()));
                            userAccount.setEndCheckBal(userAccount.getCheck().getBalance());
                            userAccount.setEndSaveBal(userAccount.getSave().getBalance());
                            userAccount.setEndCreditBal(userAccount.getCredit().getBalance());
                            continue;
                        default:
                            //ensure user enters one of the right options
                            System.out.println("################################################################################");
                            System.out.println("Please look at the options again and choose an appropriate one");
                            continue;
                    }
                    System.out.println("################################################################################");
                    //only done if the user chooses to continue doing stuff
                    if(onOff){
                        System.out.println("What else would you like to do today?");
                    }
                }
        }
    }
    /**
     * deals with the user creation
     * has code for Jaehyeon Park/ Arturo Olmos
     */
    public void userCreation(){
        //create hashmaps to check all the pins
        CustomerCollectionIterator iter = this.getCustomers().createIterator();
        Set<String> pins = new HashSet<>();
        while(iter.hasNext()){
            pins.add(iter.next().getPin());
        }
        //get the user information needed to create account
        System.out.println("Please enter the following information");
        System.out.println("First Name: ");
        String fName = super.getUserInput().nextLine();
        System.out.println("Last Name");
        String lName = super.getUserInput().nextLine();
        System.out.println("Date of Birth MM/DD/YYYY");
        String dob = "";
        boolean isCorrectDOB = false;
        //check it is a valid date of birth with a format
        while((dob.length() != ("MM/DD/YYYY").length()) || !isCorrectDOB){

            dob = super.getUserInput().nextLine();
            String[] dates = dob.split("/");
            if(dates.length != 3 || ((dates[0].length() != 2) || (dates[1].length() != 2) || (dates[2].length() != 4))){
                System.out.println("Enter the date of birth in the following format MM/DD/YYYY add the \"/\" and make sure you use integers only");
                continue;
            }
            isCorrectDOB = true;
            //check they are integers
            for(String date : dates) {
                try {
                    Integer.parseInt(date);
                } catch (Exception de) {
                    isCorrectDOB = false;
                    break;
                }
            }
            if(!isCorrectDOB || (dob.length() != ("MM/DD/YYYY").length())){
                System.out.println("Enter the date of birth in the following format MM/DD/YYYY add the \"/\" and make sure you use integers only");
            }

        }
        System.out.println("Address");
        String add = super.getUserInput().nextLine();
        System.out.println("City");
        String city = super.getUserInput().nextLine();
        System.out.println("State");
        String state = super.getUserInput().nextLine();
        System.out.println("Zip");
        String zip = "";
        boolean isZip = false;
        //ensure zip is something valid
        while(zip.length() != 5 || !isZip){
            isZip = true;
            zip = super.getUserInput().nextLine();
            //check it is an integer
            try{
                Integer.parseInt(zip);
            }catch (Exception ze){
                isZip = false;
            }
            if(!isZip || zip.length() != 5){
                System.out.println("Zip must be an integer of 5 digits e.g 79930");
            }
        }
        System.out.println("Phone Number");
        String phoneNum = "";
        boolean isCorrectPhone = false;
        //ensure the phone is the right
        while((phoneNum.length() != ("555-555-5555").length()) || !isCorrectPhone){
            phoneNum = super.getUserInput().nextLine();
            String[] nums = phoneNum.split("-");
            if(nums.length != 3 || ((nums[0].length() != 3) || (nums[1].length() != 3) || (nums[2].length() != 4))){
                System.out.println("Phone number must be entered in the following pattern 555-555-5555");
                continue;
            }
            isCorrectPhone = true;
            //check for integers
            for(String num : nums){
                try {
                    Integer.parseInt(num);
                }catch (Exception pe){
                    isCorrectPhone = false;
                    break;
                }
            }
            if(!isCorrectPhone || (phoneNum.length() != ("555-555-5555").length())){
                System.out.println("Phone number must be entered in the following pattern 555-555-5555");
            }
        }
        System.out.println("Checking deposit");
        double chDeposit;
        //ensure a proper deposit for all customer
        while(true){
            try{
                chDeposit = Double.parseDouble(super.getUserInput().nextLine());
            }
            catch (Exception e){
                System.out.println("Please enter a proper balance to deposit");
                continue;
            }
            if(chDeposit < 0){
                System.out.println("Please enter a proper balance to deposit");
                continue;
            }
            break;
        }
        System.out.println("Savings Deposit");
        double saveDeposit;
        //ensure the deposit a a correct value
        while(true){
            try{
                saveDeposit = Double.parseDouble(super.getUserInput().nextLine());
            }
            catch (Exception e){
                System.out.println("Please enter a proper balance to deposit");
                continue;
            }
            if(saveDeposit < 0){
                System.out.println("Please enter a proper balance to deposit");
                continue;
            }
            break;
        }
        System.out.println("Credit score");
        int score;
        //ensure the score is something valid
        while(true){
            try{
                score = Integer.parseInt(super.getUserInput().nextLine());
            }
            catch (Exception e){
                System.out.println("Please enter a proper integer");
                continue;
            }
            if(score < 0){
                System.out.println("Error: Not possible");
                continue;
            }
            break;
        }
        //gets the next biggest id
        int id = super.getCustomers().getMaxCustomerIDX();
        int checkNum = id + 1000000;
        int saveNum = id + 2000000;
        int creditNum = id + 3000000;
        //generate accounts
        Checking ch = new Checking("" + checkNum,chDeposit);
        Savings save = new Savings("" + saveNum,saveDeposit);
        Credit cr = new Credit();
        cr.setAccNum("" + creditNum);
        cr.setBalance(0);
        cr.setScore(score);
        cr.generateCredit(score);
        //generate key for user
        String key = super.getCustomers().generateKey(fName,lName);
        if(super.getCustomers().hasKey(key)){
            System.out.println("Error: customer with the same name exists");
            return;
        }
        //generate random pin
        String pin = generatePin(pins);
        Customer cus = new Customer(fName,lName,add,city,state,zip,phoneNum,dob,id,ch,save,cr,pin);

        super.getCustomers().add(key,cus);
        //add 1 to last max id for next user created
        super.getCustomers().incrementMaxCustomerIDX();
        //let the user know of their credentials
        System.out.println("################################################################################");
        System.out.println("The account was successfully created!");
        System.out.println("Note: The following credential are important to you login keep safe");
        System.out.println("Your ID is: " + id);
        System.out.println("Your Pin is: " + pin);
        System.out.println("Your Checking Number is: " + checkNum);
        System.out.println("Your Savings Number is: " + saveNum);
        System.out.println("Your Credit Number is: " + creditNum);
    }
    /**
     * generates a pin not found in the csv file
     * @param pins all the pins found in the customers collection
     * @return returns a generated pin
     */
    private String generatePin(Set<String> pins){
        Random rand = new Random();
        int pin = rand.nextInt(9000);
        String pinStr = String.format("%04d",pin);
        //ensure pin is unique
        while(pins.contains(pinStr)){
            pin = rand.nextInt(9000);
            pinStr = String.format("%04d",pin);
        }
        return pinStr;
    }
    /**
     * @param fName first name of user
     * @param lName last name of user
     * @param id id of user
     * @return returns true if user exits
     * ensures customer exists
     */
    private boolean credentialValid(String fName,String lName,int id){
        String key = super.getCustomers().generateKey(fName,lName);
        //check the customer exists
        if(id <= 0 || id > super.getCustomers().size() || !super.getCustomers().hasKey(key)){
            return false;
        }
        //get customer
        Customer user = super.getCustomers().get(key);
        if(user.getFirstName().split("\\s+").length != fName.split("\\s+").length || user.getLastName().split("\\s+").length != lName.split("\\s+").length){
            return false;
        }
        //compare information
        return  user.getID() == id && super.strNWS(user.getFirstName()).equals(super.strNWS(fName)) && super.strNWS(user.getLastName()).equals(super.strNWS(lName));
    }
    /**
     * @param userAccount users account
     * @param userToPayFirstName user that will be paid
     * @param userToPayLastName user to be paid last name
     * @param userToPayID user to be paid id
     * @return returns true if user exists
     * ensures customer cannot pay themselves
     */
    private boolean credentialValid(Customer userAccount,String userToPayFirstName,String userToPayLastName,int userToPayID){
        if(userToPayID <= 0 || userToPayID > super.getCustomers().size()){
            return false;
        }
        //compare information
        if(userAccount.getFirstName().split("\\s+").length != userToPayFirstName.split("\\s+").length || userAccount.getLastName().split("\\s+").length != userToPayLastName.split("\\s+").length){
            return false;
        }
        return super.strNWS(userAccount.getFirstName()).equals(super.strNWS(userToPayFirstName)) && super.strNWS(userAccount.getLastName()).equals(super.strNWS(userToPayLastName)) && userAccount.getID() == userToPayID;
    }
    /**
     * prints all the items found in miner mall
     */
    private void printItemMenu(){
        System.out.println("################################################################################");
        ItemCollectionIterator iter = this.getItems().createIterator();
        while (iter.hasNext()){
            try {
                System.out.println(iter.next());
            }catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("################################################################################");
    }
}
