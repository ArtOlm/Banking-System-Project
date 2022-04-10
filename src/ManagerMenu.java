import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Arturo Olmos
 * @version 1.0
 * this class handles the manager menu
 */
public class ManagerMenu extends BankMenu{
    //manager action handler
    private ManagerActions manHandler;

    /**
     * creates a manager menu
     * @param scnr Scanner object for user input
     * @param customers customer collection
     * @param items item collection
     */
    public ManagerMenu(Scanner scnr,CustomerCollection customers,ItemCollection items){
        super(scnr,customers,items);
        this.manHandler = ManagerActions.getInstance();
    }
    /**
     * this helps handle the manager interface
     */
    public void display(){
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
                moption = Integer.parseInt(this.getUserInput().nextLine());
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

                    this.manHandler.managerInquire(this.getUserInput().nextLine(),this.getCustomers());
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
                                op = Integer.parseInt(this.getUserInput().nextLine());
                            }
                            catch(Exception e){
                                System.out.println("Choose appropriate option(1-3)");
                                continue;
                            }
                            break;
                        }
                        System.out.println("Choose appropriate option(1-3)");
                    }
                    System.out.println("Provide the number of the account");
                    String num = this.getUserInput().nextLine();
                    this.manHandler.managerInquire(op,num,this.getCustomers());
                    System.out.println("################################################################################");
                    break;
                //execute actions.csv file
                case 3:

                    System.out.println("################################################################################");
                    System.out.println("Are you sure you want to execute transactions?(Y/N)");
                    System.out.println("Action can take a while due to the log of every transaction to log.txt");
                    String cont = this.getUserInput().nextLine();
                    while(!cont.equalsIgnoreCase("N") && !cont.equalsIgnoreCase("y")){
                        System.out.println("Please enter Y or N");
                        cont = this.getUserInput().nextLine();
                    }
                    if(cont.equalsIgnoreCase("n")){
                        System.out.println("Execution aborted");
                        System.out.println("################################################################################");
                        continue;
                    }
                    System.out.println("################################################################################");
                    System.out.println("YOU HAVE BEEN WARNED");
                    ArrayList <String[]> transactions = this.getMyHandler().loadTransactions();
                    //execute the first half in parallel to execute faster
                    ExecutionHelper execHelper = new ExecutionHelper((transactions.size() / 2),transactions.size(),transactions,this.getCustomers(),this.getItems());
                    execHelper.run();
                    this.manHandler.execTransactions(this.getCustomers(),this.getItems(),0, (transactions.size() / 2),transactions);
                    //execute the second half in parallel to execute faster
                    try{
                        execHelper.join();
                    }catch (InterruptedException ei){
                        ei.printStackTrace();
                    }
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
                            option = Integer.parseInt(this.getUserInput().nextLine());
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
                            String name = this.getUserInput().nextLine();
                            //create a key
                            String key = this.getMyHandler().generateKey("",name);
                            //check customer exists
                            if(!this.getCustomers().hasKey(key)){
                                System.out.println("No user found, cannot generate bank statement");
                                System.out.println("################################################################################");
                                continue;
                            }
                            //generate key by name
                            cus = this.getCustomers().get(key);
                            if(name.split("\\s+").length != (cus.getFirstName() + " " + cus.getLastName()).split("\\s+").length){
                                System.out.println("no users found");
                                System.out.println("################################################################################");
                                continue;
                            }
                            //generate bank statement
                            statement = this.manHandler.generateBankStatement(cus);
                            if( statement != null){
                                this.getMyHandler().writeBankStatement(cus,statement);
                            }
                            break;
                        //by id
                        case 2:
                            System.out.println("Please enter an ID");
                            int id;
                            //ensure id is an integer
                            while(true){
                                try {
                                    id = Integer.parseInt(this.getUserInput().nextLine());
                                }
                                catch (Exception e){
                                    System.out.println("Please choose an appropriate option");
                                    continue;
                                }
                                break;
                            }
                            //ensure id is in range
                            if(id < 1 || id > this.getCustomers().size()){
                                System.out.println("ID is out of range");
                                System.out.println("################################################################################");

                                continue;
                            }
                            //search for customer by id
                            cus = null;
                            while(this.getCustomerIterator().hasNext()){
                                Customer temp = null;
                                try {
                                    temp = this.getCustomerIterator().next();
                                }catch (IndexOutOfBoundsException e){
                                    System.out.println(e.getMessage());
                                }
                                if(id == temp.getID()){
                                    cus = temp;
                                    break;
                                }
                            }
                            //reset to ensure iteration starts at 0 again to reuse obj
                            this.getCustomerIterator().reset();
                            //if the customer is not null it was found
                            if(cus != null){
                                statement = this.manHandler.generateBankStatement(cus);
                                if( statement != null){//check the statement is not null
                                    this.getMyHandler().writeBankStatement(cus,statement);
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
}
