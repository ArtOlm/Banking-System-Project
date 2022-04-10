import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Arturo Olmos/Jaehyeon Park
 * @version 1.0
 * Class is used to handle the user menu
 */
public class UserMenu extends BankMenu{
    //item iterator only for this class since it is the only that iterates over hash map
    private ItemCollectionIterator itemCollectionIterator;
    //string pointer user to log the transaction
    private String transactionLog = "";
    //format to the date
    final private DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * crates a complete user menu
     * @param scnr user input
     * @param customers customer collection
     * @param items items collection
     */
    public UserMenu(Scanner scnr,CustomerCollection customers,ItemCollection items){
        super(scnr,customers,items);
        this.itemCollectionIterator = this.getItems().createIterator();
    }
    /**
     * this helps handle the customer interface
     */
    public void display(){
        System.out.println("Welcome customer, please enter the following information to properly help you.");
        //pointer to keys which is used to index into the hashmap
        String key;
        //ask user for their information which is how they will be identified
        boolean menuon = true;
        while(menuon){
            System.out.print("First Name: ");
            String userFirstName = this.getUserInput().nextLine();
            System.out.print("Last Name: ");
            String userLastName = this.getUserInput().nextLine();
            //getting their id helps since we stored  with indexing
            int userID;
            System.out.print("ID: ");
            //ensure the user inputs an integer
            //user is able to type random string
            while(true){
                try{
                    userID = Integer.parseInt(this.getUserInput().nextLine());
                }
                catch(Exception e){
                    //tell user what they need to fix
                    System.out.println("ID must be integer");
                    continue;
                }
                break;
            }
            System.out.print("Checking Account Number: ");
            String userCheckNum = this.getUserInput().nextLine();
            System.out.print("Savings Account Number: ");
            String userSaveNum = this.getUserInput().nextLine();
            System.out.print("Credit Account Number: ");
            String userCreditNum = this.getUserInput().nextLine();
            // check that the user exists
            if(credentialValid(userID,userFirstName,userLastName,userCheckNum,userSaveNum,userCreditNum)){
                key = this.getMyHandler().generateKey(userFirstName,userLastName);
                Customer userAccount = this.getCustomers().get(key);
                //sets the time of login for that user
                //also set the starting balances
                if(userAccount.getSessionStart() == null){
                    userAccount.setSessionStart(time.format(LocalDateTime.now()));
                    userAccount.setStartCheckBal(userAccount.getCheck().getBalance());
                    userAccount.setStartSaveBal(userAccount.getSave().getBalance());
                    userAccount.setStartCreditBal(userAccount.getCredit().getBalance());
                }
                System.out.println("-------------------------------------------------");
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
                            option = Integer.parseInt(this.getUserInput().nextLine());
                        }
                        catch(Exception e){
                            System.out.println("-------------------------------------------------");
                            System.out.println("Please choose an appropriate option(1-7)");
                            continue;
                        }
                        break;
                    }
                    //switch statement to do something based on decision
                    switch(option){
                        case 1://inquire procedure
                            System.out.println("-------------------------------------------------");
                            System.out.println("Enter the name of the account you would like to inquire?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = this.getUserInput().nextLine();
                            //ensure proper account is chosen
                            while(!accType.equalsIgnoreCase("Checking") && !accType.equalsIgnoreCase("Savings") && !accType.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = this.getUserInput().nextLine();
                            }
                            //let user know of success
                            System.out.printf("%s currently has %.2f$\n", accType,this.getTransactionHandler().checkBalance(userAccount,accType));
                            //log what happened
                            transactionLog = String.format("%s %s inquired their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),accType,time.format(LocalDateTime.now()));
                            this.getMyHandler().logToFile(transactionLog);

                            break;
                        case 2://deposit procedure
                            System.out.println("-------------------------------------------------");
                            System.out.println("Enter the name of the account would you like to deposit to?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = this.getUserInput().nextLine();
                            //ensure proper account is chosen
                            while(!accType.equalsIgnoreCase("Checking") && !accType.equalsIgnoreCase("Savings") && !accType.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = this.getUserInput().nextLine();
                            }
                            System.out.println("Please enter the amount you would like to Deposit:(DO NOT USE COMMA)");
                            double deposit;
                            //ensure that entered value is acceptable
                            while(true){
                                try{
                                    deposit = Double.parseDouble(this.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Error: not a proper value");
                                    continue;
                                }
                                break;
                            }
                            try {
                                this.getTransactionHandler().userDeposit(userAccount,accType,deposit);
                            }
                            catch(TransactionException eDep){
                                //Transaction was a failure
                                System.out.println(eDep.getMessage());
                                System.out.println("Returning to menu");
                                System.out.println("-------------------------------------------------");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            //if success then we tell user and log it
                            transactionLog = String.format("%s %s deposited %.2f$ from their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),deposit,accType,time.format(LocalDateTime.now()));
                            userAccount.addTransaction(transactionLog);
                            this.getMyHandler().logToFile(transactionLog);
                            System.out.printf("The deposit of %.2f$ into %s was a success!\n",deposit,accType);
                            break;
                        case 3://transaction between two accounts of the same customer
                            System.out.println("-------------------------------------------------");
                            System.out.println("Please enter the name of the account you want to transfer from");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            from = this.getUserInput().nextLine();
                            while(!from.equalsIgnoreCase("Checking") && !from.equalsIgnoreCase("Savings") && !from.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                from = this.getUserInput().nextLine();
                            }
                            System.out.println("Please enter the name of the account you want to transfer to");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            to = this.getUserInput().nextLine();
                            while(!to.equalsIgnoreCase("Checking") && !to.equalsIgnoreCase("Savings") && !to.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                to = this.getUserInput().nextLine();
                            }
                            System.out.println("How much would you like to transfer?(Do not include comma)");
                            double transfer;
                            while(true){
                                try{
                                    transfer = Double.parseDouble(this.getUserInput().nextLine());
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
                                this.getTransactionHandler().accountTransfer(userAccount,from,to,transfer);
                            }
                            catch (TransactionException eTransfer){
                                System.out.println(eTransfer.getMessage());
                                System.out.println("Returning to menu");
                                System.out.println("-------------------------------------------------");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            //if success then we tell user and log it
                            System.out.printf("The transfer was a success, %.2f$ was transferred from %s to %s\n",transfer,from,to);
                            transactionLog = String.format("%s %s transferred %.2f$ from %s to %s at %s\n",userAccount.getFirstName(),userAccount.getLastName(),transfer,from,to,time.format(LocalDateTime.now()));
                            userAccount.addTransaction(transactionLog);
                            this.getMyHandler().logToFile(transactionLog);
                            break;
                        case 4://withdraw procedure
                            System.out.println("-------------------------------------------------");
                            System.out.println("Enter the name of the account would you like to withdraw from?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = this.getUserInput().nextLine();
                            while(!accType.equalsIgnoreCase("Checking") && !accType.equalsIgnoreCase("Savings") && !accType.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = this.getUserInput().nextLine();
                            }
                            System.out.println("Please enter the amount you would like to withdraw:(DO NOT USE COMMA)");
                            double withdrawl;
                            //ensure that entered value is acceptable
                            while(true){
                                try{
                                    withdrawl = Double.parseDouble(this.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Error: not a proper value");
                                    continue;
                                }
                                break;
                            }
                            try{
                                this.getTransactionHandler().userWithdraw(userAccount,accType,withdrawl);
                            }
                            catch(TransactionException eWith){
                                //there was transaction failure
                                System.out.println(eWith.getMessage());
                                System.out.println("Returning to menu");
                                System.out.println("-------------------------------------------------");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            //if success then we tell user and log it
                            transactionLog = String.format("%s %s withdrew %.2f$ from the %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),withdrawl,accType,time.format(LocalDateTime.now()));
                            System.out.printf("The withdrawl of %.2f$ was a success\n",withdrawl);
                            userAccount.addTransaction(transactionLog);
                            this.getMyHandler().logToFile(transactionLog);
                            break;
                        case 5://pay another user procedure
                            System.out.println("-------------------------------------------------");
                            System.out.println("Please enter the name of the account you want to pay from");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            from = this.getUserInput().nextLine();
                            //ensure a proper account is entered
                            while(!from.equalsIgnoreCase("Checking") && !from.equalsIgnoreCase("Savings") && !from.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                from = this.getUserInput().nextLine();
                            }
                            System.out.println("How much would you like to pay?(Do not include comma)");
                            double pay;
                            //ensure proper pay is entered
                            while(true){
                                try{
                                    pay = Double.parseDouble(this.getUserInput().nextLine());
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
                            String userToPayFirstName = this.getUserInput().nextLine();
                            System.out.print("Last Name: ");
                            String userToPayLastName = this.getUserInput().nextLine();
                            int userToPayID;
                            System.out.print("ID: ");
                            //ensure a proper id is entered
                            while(true){
                                try{
                                    userToPayID = Integer.parseInt(this.getUserInput().nextLine());
                                }
                                catch(Exception e){
                                    System.out.println("Please enter an integer");
                                    continue;
                                }
                                if(userToPayID < 0 || userToPayID > this.getCustomers().size()){
                                    System.out.println("Not a valid ID");
                                }
                                break;
                            }
                            //check to see credentials are not to the same user
                            if(credentialValid(userAccount,userToPayFirstName,userToPayLastName,userToPayID)){
                                System.out.println("Error: cannot pay yourself");
                                System.out.println("returning to menu.....");
                                System.out.println("-------------------------------------------------");
                                System.out.println("Please choose an option");
                                continue;
                            }
                            System.out.println("Please enter the name of the account you want to pay to");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            to = this.getUserInput().nextLine();
                            //ensure a proper option
                            while(!to.equalsIgnoreCase("Checking") && !to.equalsIgnoreCase("Savings") && !to.equalsIgnoreCase("Credit")){
                                System.out.println("Please enter a correct option");
                                to = this.getUserInput().nextLine();

                            }
                            //ensure a customer with the info exists
                            if(credentialValid(userToPayFirstName,userToPayLastName,userToPayID)){
                                key = this.getMyHandler().generateKey(userToPayFirstName,userToPayLastName);
                                Customer userToPay = this.getCustomers().get(key);
                                try{
                                    this.getTransactionHandler().transactionToOther(userAccount,userToPay,from,to,pay);
                                }
                                catch(TransactionException eTransfer){
                                    //there was transaction failure
                                    System.out.println(eTransfer.getMessage());
                                    System.out.println("Returning to menu");
                                    System.out.println("-------------------------------------------------");
                                    System.out.println("Please choose an option");
                                    continue;
                                }
                                //if success then we tell user and log it
                                System.out.printf("payment of %.2f$ from %s account to %s %s into their %s account was a success\n",pay,from,userToPay.getFirstName(),userToPay.getLastName(),to);
                                transactionLog = String.format("%s %s paid %.2f$ from their %s account to %s %s into their %s account at %s\n",userAccount.getFirstName(),userAccount.getLastName(),pay,from,userToPay.getFirstName(),userToPay.getLastName(),to,time.format(LocalDateTime.now()));
                                userAccount.addTransaction(transactionLog);
                                this.getMyHandler().logToFile(transactionLog);
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
                            System.out.println("-------------------------------------------------");
                            //print all items
                            System.out.println("Hello Welcome to Miners Mall");
                            while(outerMenu){
                                System.out.println("What would you like to do?(1-3)");
                                System.out.println("1.View Items Menu");
                                System.out.println("2.Buy Items");
                                System.out.println("3.Exit mall");
                                //get the option and ensure it is appropriate
                                while (true) {
                                    String buyOption = this.getUserInput().nextLine();
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
                                        System.out.println("-------------------------------------------------");
                                        String line = null;
                                        //keep track of how many items are available
                                        HashMap<String,Integer> maxCount = new HashMap<>();
                                        while(this.itemCollectionIterator.hasNext()){
                                            Item it = this.itemCollectionIterator.next();
                                            maxCount.put(it.getName(),it.getMax());
                                        }
                                        //reset to reuse
                                        this.itemCollectionIterator.reset();
                                        while(malMenu) {
                                            //get item user wants
                                            System.out.println("Enter the ID of the item you would like to add to the cart(Enter \"C\" to checkout or \"E\" to exit the mall) ");
                                            int mallID = -1;
                                            line = this.getUserInput().nextLine();
                                            try{
                                                mallID = Integer.parseInt(line);
                                            } catch (Exception me){
                                                //check if the option is e or c
                                                if(line.equalsIgnoreCase("c") || line.equalsIgnoreCase("e")){
                                                    break;
                                                }
                                                //else it is not valid
                                                System.out.println("Error: not a valid ID");
                                                continue;
                                            }
                                            //ensure the id is within range
                                            //check id is valid
                                            if((mallID  < 0) || (mallID > this.getItems().size())){
                                                System.out.println("Error: not a valid ID");
                                                continue;
                                            }
                                            //check if the item is available
                                            Item itemAdded = this.getItems().get(mallID);
                                            if(maxCount.get(itemAdded.getName()) < 1){
                                                System.out.println("Error: Item is no longer available");
                                                continue;
                                            }
                                            //add to cart and update the availability
                                            cart.add(itemAdded);
                                            maxCount.put(itemAdded.getName(),maxCount.get(itemAdded.getName()) - 1);
                                        }
                                        if(line.equalsIgnoreCase("c") && (cart.size() > 0)){
                                            double total = 0;
                                            //ask with what account they want to pay with
                                            System.out.println("With which account would you like to pay?(Enter the name)");
                                            System.out.println("1.Checking");
                                            System.out.println("2.Credit");
                                            String accountType = this.getUserInput().nextLine();
                                            while (!accountType.equalsIgnoreCase("Checking") && !accountType.equalsIgnoreCase("Credit")){
                                                System.out.println("Please enter Checking or Credit");
                                             accountType = this.getUserInput().nextLine();
                                            }
                                            //pinter to check the input
                                            String pinStr = null;
                                            //make them enter their pin
                                            if(accountType.equalsIgnoreCase("Checking")){
                                                System.out.println("Please enter your pin");

                                                int pin;
                                                while (true){
                                                    try {
                                                        pinStr = this.getUserInput().nextLine();
                                                        pin = Integer.parseInt(pinStr);
                                                    }   catch (Exception ek){
                                                        if(pinStr.equalsIgnoreCase("a")){
                                                            break;
                                                        }
                                                        System.out.println("Please enter the correct pin or enter \"a\" to abort the checkout");
                                                        continue;
                                                    }
                                                    if(pin != userAccount.getPin()){
                                                        System.out.println("Please enter the correct pin or enter \"a\" to abort the checkout");
                                                        continue;
                                                    }
                                                    break;
                                                }

                                            }else if(accountType.equalsIgnoreCase("Credit")){
                                                //ensure pinStr is not null if they chose credit
                                                pinStr = "c";
                                            }
                                            //if the user did not abort the payment
                                            if(!pinStr.equalsIgnoreCase("a")) {
                                                //get the total of all the items in their cart
                                                for (int i = 0; i < cart.size(); i++) {
                                                    total += cart.get(i).getPrice();
                                                }
                                                try {
                                                    this.getTransactionHandler().buyFromMinerMall(userAccount, accountType, total);
                                                } catch (Exception me) {
                                                    System.out.println(me.getMessage());
                                                    break;
                                                }
                                                //update the users info and tell them they succeeded in making the purchase
                                                System.out.printf("Your purchase of %.2f$ at Miners mall was a success!\n", total);
                                                System.out.println("Thank you!");
                                                for (int i = 0; i < cart.size(); i++) {
                                                    Item t = cart.get(i);
                                                    //update the limit on the items
                                                    this.getItems().get(t.getID()).setMax(maxCount.get(t.getName()));
                                                    //log everything they bought if successful
                                                    transactionLog = String.format("%s %s bought %s for %.2f$ using %s account at %s\n", userAccount.getFirstName(), userAccount.getLastName(), t.getName(), t.getPrice(), accountType,time.format(LocalDateTime.now()));
                                                    this.getMyHandler().logToFile(transactionLog);
                                                    userAccount.addTransaction(transactionLog);
                                                    userAccount.setTotalMoneySpent(userAccount.getTotalMoneySpent() + t.getPrice());
                                                    userAccount.addItemBought(t.getName());
                                                }

                                            }else {
                                                System.out.println("Checkout was aborted, no items were purchased");
                                            }
                                        }
                                        if(cart.size() == 0 || line.equalsIgnoreCase("e")){
                                            System.out.println("No items purchased");
                                        }
                                        System.out.println("-------------------------------------------------");
                                        break;
                                    case 3:
                                        outerMenu = false;
                                        break;
                                    default:
                                        System.out.println("Something went wrong");
                                        break;
                                }
                            }
                            System.out.println("-------------------------------------------");
                            break;
                        case 7:
                            //user logs out
                            System.out.println("-------------------------------------------------");
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
                            userAccount.setSesstionEnd(time.format(LocalDateTime.now()));
                            userAccount.setEndCheckBal(userAccount.getCheck().getBalance());
                            userAccount.setEndSaveBal(userAccount.getSave().getBalance());
                            userAccount.setEndCreditBal(userAccount.getCredit().getBalance());
                            continue;
                        default:
                            //ensure user enters one of the right options
                            System.out.println("-------------------------------------------------");
                            System.out.println("Please look at the options again and choose an appropriate one");
                            continue;
                    }
                    System.out.println("-------------------------------------------------");
                    //only done if the user chooses to continue doing stuff
                    if(onOff){
                        System.out.println("What else would you like to do today?");
                    }
                }
            } else{
                System.out.println("-------------------------------------------");
                // user did not enter matching credential, we then ask if they would like to retry of just exit
                System.out.println("Look like something went wrong, would you like to try again(Enter Y to continue or n to exit)");
                String decision = this.getUserInput().nextLine();
                while(!decision.equalsIgnoreCase("Y") && !decision.equalsIgnoreCase("n")){
                    System.out.println("Please enter Y or n");
                    decision = this.getUserInput().nextLine();
                }
                if(decision.equalsIgnoreCase("n")){
                    break;
                }
                else {
                    System.out.println("Please enter your information");
                    System.out.println("-------------------------------------------");
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
        Set<Integer> pins = new HashSet<>();
        while(this.getCustomerIterator().hasNext()){
            pins.add(this.getCustomerIterator().next().getPin());
        }
        //ensure object starts at 0 again for future calls
        this.getCustomerIterator().reset();
        //get the user information needed to create account
        System.out.println("Please enter the following information");
        System.out.println("First Name: ");
        String fName = this.getUserInput().nextLine();
        System.out.println("Last Name");
        String lName = this.getUserInput().nextLine();
        System.out.println("Date of Birth");
        String dob = this.getUserInput().nextLine();
        System.out.println("Address");
        String add = this.getUserInput().nextLine();
        System.out.println("City");
        String city = this.getUserInput().nextLine();
        System.out.println("State");
        String state = this.getUserInput().nextLine();
        System.out.println("Zip");
        String zip = this.getUserInput().nextLine();
        System.out.println("Phone Number");
        String phoneNum = this.getUserInput().nextLine();
        System.out.println("Checking deposit");
        double chDeposit;
        //ensure a proper deposit for all customer
        while(true){
            try{
                chDeposit = Double.parseDouble(this.getUserInput().nextLine());
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
        while(true){
            try{
                saveDeposit = Double.parseDouble(this.getUserInput().nextLine());
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
        while(true){
            try{
                score = Integer.parseInt(this.getUserInput().nextLine());
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
        int id = this.getMyHandler().getMaxCustomerIDX();
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
        String key = this.getMyHandler().generateKey(fName,lName);
        //generate random pin
        int pin = generatePin(pins);
        Customer cus = new Customer(fName,lName,add,city,state,zip,phoneNum,dob,id,ch,save,cr,pin);
        this.getCustomers().add(key,cus);
        //add 1 to last max id for next user created
        this.getMyHandler().incrementMaxCustomerIDX();
        //let the user know of their credentials
        System.out.println("-------------------------------------------------");
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
    private int generatePin(Set<Integer> pins){
        Random rand = new Random();
        int pin = rand.nextInt(9000) + 1000;
        //ensure pin is unique
        while(pins.contains(pin)){
            pin = rand.nextInt(9000) + 1000;
        }
        return pin;
    }
    /**
     * @param fName first name of user
     * @param lName last name of user
     * @param id id of user
     * @return returns true if user exits
     * ensures customer exists
     */
    private boolean credentialValid(String fName,String lName,int id){
        String key = this.getMyHandler().generateKey(fName,lName);
        //check the customer exists
        if(id <= 0 || id > this.getCustomers().size() || !this.getCustomers().hasKey(key)){
            return false;
        }
        //get customer
        Customer user = this.getCustomers().get(key);
        if(user.getFirstName().split("\\s+").length != fName.split("\\s+").length || user.getLastName().split("\\s+").length != lName.split("\\s+").length){
            return false;
        }
        //compare information
        return  user.getID() == id && this.getMyHandler().strNWS(user.getFirstName()).equals(this.getMyHandler().strNWS(fName)) && this.getMyHandler().strNWS(user.getLastName()).equals(this.getMyHandler().strNWS(lName));
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
        if(userToPayID <= 0 || userToPayID > this.getCustomers().size()){
            return false;
        }
        //compare information
        if(userAccount.getFirstName().split("\\s+").length != userToPayFirstName.split("\\s+").length || userAccount.getLastName().split("\\s+").length != userToPayLastName.split("\\s+").length){
            return false;
        }
        return this.getMyHandler().strNWS(userAccount.getFirstName()).equals(this.getMyHandler().strNWS(userToPayFirstName)) && this.getMyHandler().strNWS(userAccount.getLastName()).equals(this.getMyHandler().strNWS(userToPayLastName)) && userAccount.getID() == userToPayID;
    }
    /**
     * @param id user id
     * @param fName first name of user
     * @param lName last name of user
     * @param userCheckNum user checking account number
     * @param userSaveNum user savings account number
     * @param userCreditNum user credit account number
     * @return returns true if matching
     * ensures use logging in exists
     */
    private boolean credentialValid(int id, String fName, String lName, String userCheckNum, String userSaveNum, String userCreditNum){
        String key = this.getMyHandler().generateKey(fName,lName);
        //check the customer exists
        if(id <= 0 || id > this.getCustomers().size() || !this.getCustomers().hasKey(key)){
            return false;
        }
        else{
            //get the customer
            Customer user = this.getCustomers().get(key);
            //ensure that there are no wired strings the are split apart
            if(user.getFirstName().split("\\s+").length != fName.split("\\s+").length || user.getLastName().split("\\s+").length != lName.split("\\s+").length || user.getSave().getAccNum().split("\\s+").length != userSaveNum.split("\\s+").length || user.getCredit().getAccNum().split("\\s+").length != userCreditNum.split("\\s+").length || user.getCheck().getAccNum().split("\\s+").length != userCheckNum.split("\\s+").length){
                System.out.println("no");
                return false;
            }
            //return true if info matches
            return  user.getID() == id && user.getCheck().getAccNum().equals(this.getMyHandler().strNWS(userCheckNum)) && user.getSave().getAccNum().equals(this.getMyHandler().strNWS(userSaveNum)) && user.getCredit().getAccNum().equals(this.getMyHandler().strNWS(userCreditNum));
        }
    }
    /**
     * prints all the items found in miner mall
     */
    private void printItemMenu(){
        System.out.println("-------------------------------------------------");
        while (this.itemCollectionIterator.hasNext()){
            try {
                System.out.println(this.itemCollectionIterator.next());
            }catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        }
        //reset so that iterator can be reused
        this.itemCollectionIterator.reset();
        System.out.println("-------------------------------------------------");
    }
}
