import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserMenu extends BankMenu{
    //string pointer user to log the transaction
    private String transactionLog = "";
    //format to the date
    private DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public UserMenu(Scanner scnr,CustomerCollection customers,HashMap<Integer,Item> items){
        super(scnr,customers,items);
    }
    /**
     * this helps handle the customer interface
     */
    public void display(){
        System.out.println("Welcome customer, please enter the following information to properly help you.");
        //pointer to keys which is used to index into the hashmap
        String key = "";
        //ask user for their information which is how they will be identified
        boolean menuon = true;
        while(menuon){
            System.out.print("First Name: ");
            String userFirstName = this.getUserInput().nextLine();
            System.out.print("Last Name: ");
            String userLastName = this.getUserInput().nextLine();
            //getting their id helps since we stored  with indexing
            int userID = -1;
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
                Customer userAccount = (Customer) this.getCustomers().get(key);
                //sets the time of loggin for that user
                //also set the starting balances
                if(userAccount.getSessionStart() == null){
                    userAccount.setSessionStart(time.format(LocalDateTime.now()));
                    userAccount.setStartCheckBal(userAccount.getCheck().getBalance());
                    userAccount.setStartSaveBal(userAccount.getSave().getBalance());
                    userAccount.setStartCreditBal(userAccount.getCredit().getBalance());
                }
                System.out.println("-------------------------------------------------");
                System.out.println("Hello " + userAccount.getFName() + " " + userAccount.getLName() + " what would you like to do today?(Enter 1-7)");
                //stirng ponters is used later on to point to account chosen by user
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
                    int option = 0;
                    //ensure the user chooses and appropiate option
                    while(true){
                        try{
                            option = Integer.parseInt(this.getUserInput().nextLine());
                        }
                        catch(Exception e){
                            System.out.println("-------------------------------------------------");
                            System.out.println("Please choose an appropiate option(1-7)");
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
                            //ensure porper account is chosen
                            while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = this.getUserInput().nextLine();
                            }
                            //let user know of success
                            System.out.printf("%s currently has %.2f$\n", accType,this.getTransactionHandler().checkBalance(userAccount,accType));
                            //log what happened
                            transactionLog = String.format("%s %s inquired their %s account at %s\n",userAccount.getFName(),userAccount.getLName(),accType,time.format(LocalDateTime.now()));
                            this.getMyHandler().logToFile(transactionLog);

                            System.out.println("-------------------------------------------------");
                            break;
                        case 2://deposit procedure
                            System.out.println("-------------------------------------------------");
                            System.out.println("Enter the name of the account would you like to deposit to?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = this.getUserInput().nextLine();
                            //ensure proper account is chosen
                            while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = this.getUserInput().nextLine();
                            }
                            System.out.println("Please enter the amount you would like to Deposit:(DO NOT USE COMMA)");
                            double deposit = 0;
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
                            transactionLog = String.format("%s %s deposited %.2f$ from their %s account at %s\n",userAccount.getFName(),userAccount.getLName(),deposit,accType,time.format(LocalDateTime.now()));
                            userAccount.addTransaction(String.format(transactionLog));
                            this.getMyHandler().logToFile(transactionLog);


                            System.out.printf("The deposit of %.2f$ into %s was a sucess!\n",deposit,accType);
                            System.out.println("-------------------------------------------------");
                            break;
                        case 3://transaction between two customer of the same customer
                            System.out.println("-------------------------------------------------");
                            System.out.println("Please enter the name of the account you want to tranfer from");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            from = this.getUserInput().nextLine();
                            while(!from.equals("Checking") && !from.equals("Savings") && !from.equals("Credit")){
                                System.out.println("Please enter a correct option");
                                from = this.getUserInput().nextLine();

                            }
                            System.out.println("Please enter the name of the account you want to tranfer to");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            to = this.getUserInput().nextLine();
                            while(!to.equals("Checking") && !to.equals("Savings") && !to.equals("Credit")){
                                System.out.println("Please enter a correct option");
                                to = this.getUserInput().nextLine();

                            }
                            System.out.println("How much would you like to tranfer?(Do not include comma)");
                            double transfer = -1;
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
                            transactionLog = String.format("%s %s tranfered %.2f$ from %s to %s at %s\n",userAccount.getFName(),userAccount.getLName(),transfer,from,to,time.format(LocalDateTime.now()));
                            userAccount.addTransaction(String.format(transactionLog));
                            this.getMyHandler().logToFile(transactionLog);
                            break;
                        case 4://withdraw procedure
                            System.out.println("-------------------------------------------------");
                            System.out.println("Enter the name of the account would you like to withdraw from?");
                            System.out.println("1.Checking");
                            System.out.println("2.Savings");
                            System.out.println("3.Credit");
                            accType = this.getUserInput().nextLine();
                            while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
                                System.out.println("Please enter a correct option");
                                accType = this.getUserInput().nextLine();

                            }
                            System.out.println("Please enter the amount you would like to withdraw:(DO NOT USE COMMA)");
                            double withdrawl = 0;
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
                            transactionLog = String.format("%s %s withdrew %.2f$ from the %s account at %s\n",userAccount.getFName(),userAccount.getLName(),withdrawl,accType,time.format(LocalDateTime.now()));
                            System.out.printf("The withdrawl of %.2f$ was a success\n",withdrawl);
                            userAccount.addTransaction(String.format(transactionLog));
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
                            while(!from.equals("Checking") && !from.equals("Savings") && !from.equals("Credit")){
                                System.out.println("Please enter a correct option");
                                from = this.getUserInput().nextLine();

                            }
                            System.out.println("How much would you like to pay?(Do not include comma)");
                            double pay = -1;
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
                            int userToPayID = -1;
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
                            while(!to.equals("Checking") && !to.equals("Savings") && !to.equals("Credit")){
                                System.out.println("Please enter a correct option");
                                to = this.getUserInput().nextLine();

                            }
                            //ensure a customer with the info exists
                            if(credentialValid(userToPayFirstName,userToPayLastName,userToPayID)){
                                key = this.getMyHandler().generateKey(userToPayFirstName,userToPayLastName);
                                Customer userToPay = (Customer) this.getCustomers().get(key);
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
                                System.out.printf("payment of %.2f$ from %s account to %s %s into their %s account was a success\n",pay,from,userToPay.getFName(),userToPay.getLName(),to);

                                transactionLog = String.format("%s %s paid %.2f$ from their %s account to %s %s into their %s account at %s\n",userAccount.getFName(),userAccount.getLName(),pay,from,userToPay.getFName(),userToPay.getLName(),to,time.format(LocalDateTime.now()));
                                userAccount.addTransaction(String.format(transactionLog));
                                this.getMyHandler().logToFile(transactionLog);
                            }
                            else{
                                System.out.println("Error: no user found with provided infromation");
                            }
                            break;
                        case 6://buy
                            //control variable used for outer loop
                            boolean outerMenu = true;
                            //control variable used for outer loop
                            boolean malMenu;
                            //option control
                            int mallOpt = -1;
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
                                //get the option and ensure it is appropiate
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
                                        printItemMenu(this.getItems());
                                        break;
                                    //buy item
                                    case 2:
                                        malMenu = true;
                                        System.out.println("-------------------------------------------------");
                                        while(malMenu) {
                                            //get item user wants
                                            System.out.println("Enter the ID of the item you would like to buy(Enter \"E\" to stop buying) ");
                                            int itemID = -1;
                                            //ensure a proper item is entered
                                            while (true) {
                                                String buyOption = this.getUserInput().nextLine();
                                                try {
                                                    itemID = Integer.parseInt(buyOption);
                                                } catch (Exception e) {
                                                    if(buyOption.equalsIgnoreCase("E")){
                                                        malMenu = false;
                                                    }
                                                    if(malMenu){
                                                        System.out.println("Please choose an appropriate option");
                                                        continue;
                                                    }
                                                }
                                                //ensure a proper id is used
                                                if ((itemID < 1 || itemID > this.getItems().size()) && malMenu) {
                                                    System.out.println("Please choose an appropriate option");
                                                    continue;
                                                }
                                                break;
                                            }
                                            //turn of the purchase feature when user wants to stop buying
                                            if(!malMenu){
                                                System.out.println("Thank you for your purchase");
                                                break;
                                            }
                                            //get item
                                            Item itemToBuy = this.getItems().get(itemID);
                                            //ask the user with which account they want to pay with
                                            System.out.println("-------------------------------------------------");
                                            System.out.println("Please enter the name of the account you want to pay with");
                                            System.out.println("1.Checking");
                                            System.out.println("2.Credit");
                                            accType = this.getUserInput().nextLine();
                                            //ensure a proper option is entered
                                            while(!accType.equals("Checking") && !accType.equals("Savings") && !accType.equals("Credit")){
                                                System.out.println("Please enter a correct option");
                                                accType = this.getUserInput().nextLine();
                                            }
                                            //do the transactions and check if successful or not

                                            try{
                                                this.getTransactionHandler().buyFromMinerMall(userAccount, accType, itemToBuy.getPrice());
                                            }catch (TransactionException eBuy){
                                                System.out.printf("unable to buy %s using your %s account\n",itemToBuy.getName(),accType);
                                                continue;
                                            }
                                            //keeps track of items bought plus money spent
                                            userAccount.addItemBought(itemToBuy.getName());
                                            userAccount.setTotalMoneySpent(userAccount.getTotalMoneySpent() + itemToBuy.getPrice());
                                            //log transaction info
                                            System.out.printf("your purchase of %s for %.2f$ was a success\n", itemToBuy.getName(), itemToBuy.getPrice());
                                            transactionLog = String.format("%s %s purchased a %s for %.2f$ from miners bank using their %s account at %s\n", userAccount.getFName(), userAccount.getLName(), itemToBuy.getName(), itemToBuy.getPrice(), accType, time.format(LocalDateTime.now()));
                                            userAccount.addTransaction(String.format(transactionLog));
                                            this.getMyHandler().logToFile(transactionLog);
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
                            System.out.println("Thank you " + userAccount.getFName() + " " + userAccount.getLName() + ", have a great day!");
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
                            System.out.println("-------------------------------------------------");
                            //break does not work within the switch cases so I used boolean to exit loop
                            onOff = false;
                            menuon = false;
                            //sets the ending session for the user and the balance for the session
                            //the balance can change if another user pays a customer
                            //thus we want to set the edning balance for this session
                            userAccount.setSesstionEnd(time.format(LocalDateTime.now()));
                            userAccount.setEndCheckBal(userAccount.getCheck().getBalance());
                            userAccount.setEndSaveBal(userAccount.getSave().getBalance());
                            userAccount.setEndCreditBal(userAccount.getCredit().getBalance());
                            break;
                        default:
                            //ensure user enters one of the right oprtions
                            System.out.println("-------------------------------------------------");
                            System.out.println("Please look at the options again and choose an appropiate one");
                            continue;
                    }
                    System.out.println("-------------------------------------------------");
                    //only done if the user chooses to continue doing stuff
                    if(onOff){
                        System.out.println("What else would you like to do today?");
                    }
                }
            }
            else{
                System.out.println("-------------------------------------------");
                // user did not enter matching credential, we then ask if they would like to retry of just exit
                System.out.println("Look like something went wrong, would you like to try again(Enter Y to continue or n to exit)");
                String decision = this.getUserInput().nextLine();
                while(!decision.equals("Y") && !decision.equals("n")){
                    System.out.println("Please enter Y or n");
                    decision = this.getUserInput().nextLine();
                }
                if(decision.equals("Y")){
                    System.out.println("-------------------------------------------");
                    continue;
                }
                else{
                    System.out.println("-------------------------------------------");
                    break;
                }

            }
        }
    }
    /**
     * deals with the user creation
     */
    public void userCreation(){
        //create hashmaps to check all the pins
        Set<Integer> pins = new HashSet<Integer>();
        while(this.getCustomerIterator().hasNext()){
            pins.add(this.getCustomerIterator().next().getPin());
        }
        
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
        double chDepsoit = 0;
        //ensure a proper deposit for all customer
        while(true){
            try{
                chDepsoit = Double.parseDouble(this.getUserInput().nextLine());
            }
            catch (Exception e){
                System.out.println("Please enter a proper balance to deposit");
                continue;
            }
            if(chDepsoit < 0){
                System.out.println("Please enter a proper balance to deposit");
                continue;
            }
            break;
        }
        System.out.println("Savings Deposit");
        double saveDeposit = 0;
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
        int score = 0;
        while(true){
            try{
                score = Integer.parseInt(this.getUserInput().nextLine());
            }
            catch (Exception e){
                System.out.println("Please enter a proper integer");
                continue;
            }
            break;
        }

        int id = this.getMyHandler().getMaxCustomerIDX();
        int checkNum = id + 1000000;
        int saveNum = id + 2000000;
        int creditNum = id + 3000000;
        int generatedLimit = getCreditLimit(score);
        Checking ch = new Checking("" + checkNum,chDepsoit);
        Savings save = new Savings("" + saveNum,saveDeposit);
        Credit cr = new Credit("" + creditNum,0,generatedLimit,score);
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
     * @param pins
     * @return
     */
    private int generatePin(Set<Integer> pins){
        Random rand = new Random();
        int pin = rand.nextInt(9000) + 1000;
        //ensure pin is unique
        while(pins.contains(pin)){
            pin = rand.nextInt(9000) + 1000;;
        }
        return pin;
    }
    /**
     *
     * @param score
     * @return return limit
     * returns the limit based on the score
     */
    private int getCreditLimit(int score){
        Random rand = new Random();
        //generate the limit based on their score
        if(score <= 580){
            int min = 100;
            int max = 699;

            int randomLimit = rand.nextInt((max - min) + 1) + min;
            return randomLimit;
        }
        else if(score >= 581 && score <= 669){
            int min = 700;
            int max = 4999;

            int randomLimit = rand.nextInt((max - min) + 1) + min;
            return randomLimit;
        }
        else if(score >= 670 && score <= 739){
            int min = 5000;
            int max = 7499;

            int randomLimit = rand.nextInt((max - min) + 1) + min;
            return randomLimit;
        }
        else if(score >= 740 && score <= 799){
            int min = 7500;
            int max = 15999;

            int randomLimit = rand.nextInt((max - min) + 1) + min;
            return randomLimit;
        }
        else if(score >= 800){
            int min = 16000;
            int max = 25000;

            int randomLimit = rand.nextInt((max - min) + 1) + min;
            return randomLimit;
        }
        //at this point something went wrong
        return 0;
    }
    /**
     *
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
        Customer user = (Customer) this.getCustomers().get(key);
        if(user.getFName().split("\\s+").length != fName.split("\\s+").length || user.getLName().split("\\s+").length != lName.split("\\s+").length){
            return false;
        }
        //compare information
        return  user.getID() == id && this.getMyHandler().strNWS(user.getFName()).equals(this.getMyHandler().strNWS(fName)) && this.getMyHandler().strNWS(user.getLName()).equals(this.getMyHandler().strNWS(lName));
    }
    /**
     *
     *
     * @param userAccount users account
     * @param userToPayFirstName user that will be paid
     * @param userToPayLastName user to be paid last name
     * @param userToPayID user to be paid id
     * @param userToPayID user to be paid id
     * @return returns true if user exists
     * ensures customer cannot pay themselves
     */
    private boolean credentialValid(Customer userAccount,String userToPayFirstName,String userToPayLastName,int userToPayID){
        if(userToPayID <= 0 || userToPayID > this.getCustomers().size()){
            return false;
        }
        //compare infromation
        if(userAccount.getFName().split("\\s+").length != userToPayFirstName.split("\\s+").length || userAccount.getLName().split("\\s+").length != userToPayLastName.split("\\s+").length){
            return false;
        }
        return this.getMyHandler().strNWS(userAccount.getFName()).equals(this.getMyHandler().strNWS(userToPayFirstName)) && this.getMyHandler().strNWS(userAccount.getLName()).equals(this.getMyHandler().strNWS(userToPayLastName)) && userAccount.getID() == userToPayID;
    }
    /**
     *
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
            Customer user = (Customer) this.getCustomers().get(key);
            //ensure that there are no wired strings the are split apart
            if(user.getFName().split("\\s+").length != fName.split("\\s+").length || user.getLName().split("\\s+").length != lName.split("\\s+").length || user.getSave().getAccNum().split("\\s+").length != userSaveNum.split("\\s+").length || user.getCredit().getAccNum().split("\\s+").length != userCreditNum.split("\\s+").length || user.getCheck().getAccNum().split("\\s+").length != userCheckNum.split("\\s+").length){
                System.out.println("no");
                return false;
            }
            //return true if info matches
            return  user.getID() == id && user.getCheck().getAccNum().equals(this.getMyHandler().strNWS(userCheckNum)) && user.getSave().getAccNum().equals(this.getMyHandler().strNWS(userSaveNum)) && user.getCredit().getAccNum().equals(this.getMyHandler().strNWS(userCreditNum));
        }
    }
    /**
     * prints all the items found in miner mall
     * @param items
     */
    private void printItemMenu(HashMap<Integer, Item> items){
        System.out.println("-------------------------------------------------");
        for(int i = 1;i < items.size() + 1;i++){
            System.out.println(items.get(i));
        }
        System.out.println("-------------------------------------------------");
    }
}
