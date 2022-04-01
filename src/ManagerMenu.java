import java.util.Scanner;

/**
 * @author Arturo Olmos
 * @version 1.0
 * this class handles the manager menu
 */
public class ManagerMenu extends BankMenu{
    public ManagerMenu(Scanner scnr,CustomerCollection customers,ItemCollection items){
        super(scnr,customers,items);
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
                System.out.println("error enter an appropriate integer(1-3)");
                continue;
            }
            switch(moption){
                //search by name
                case 1:
                    System.out.println("-------------------------------------------------");
                    System.out.println("Enter a name: ");

                    this.getManHandler().managerInquire(this.getUserInput().nextLine(),this.getCustomers());
                    System.out.println("-------------------------------------------------");
                    break;
                //search by account
                case 2:
                    System.out.println("-------------------------------------------------");
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
                    this.getManHandler().managerInquire(op,num,this.getCustomers());
                    System.out.println("-------------------------------------------------");
                    break;
                //execute actions.csv file
                case 3:
                    System.out.println("-------------------------------------------");
                    this.getManHandler().execTransactions(this.getCustomers(),this.getItems());
                    System.out.println("-------------------------------------------");
                    break;
                //create bank statement for a user
                case 4:
                    System.out.println("-------------------------------------------");
                    System.out.println("How would you like to generate the bank statement by?");
                    System.out.println("1.Name");
                    System.out.println("2.ID");
                    int option;
                    while(true){
                        try {
                            option = Integer.parseInt(this.getUserInput().nextLine());
                        }
                        catch (Exception e){
                            System.out.println("Please choose an appropriate option");
                            continue;
                        }
                        if(option != 1 && option != 2){
                            System.out.println("Please choose an appropriate option");
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
                                continue;
                            }
                            //generate key by name
                            cus = this.getCustomers().get(key);
                            if(name.split("\\s+").length != (cus.getFName() + " " + cus.getLName()).split("\\s+").length){
                                System.out.println("no users found");
                                continue;
                            }
                            //generate bank statement
                            statement = this.getManHandler().generateBankStatement(cus);
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
                                statement = this.getManHandler().generateBankStatement(cus);
                                if( statement != null){//check the statement is not null
                                    this.getMyHandler().writeBankStatement(cus,statement);
                                }
                            }
                            else {
                                System.out.println("No user found, cannot generate bank statement");
                            }
                            break;
                        default:
                            //at this point something went gone wrong
                            System.out.println("Something went wrong");
                            break;
                    }
                    System.out.println("-------------------------------------------");
                    break;
                //exit
                case 5:
                    System.out.println("-------------------------------------------");
                    System.out.println("Thank you manager have a great day!");
                    return;
                //not a proper option chosen
                default:
                    System.out.println("Error: enter an appropriate integer(1-3)");
                    continue;
            }
            //ask what the manager wants to do next
            System.out.println("What else would you like to do today?");
        }
    }
}
