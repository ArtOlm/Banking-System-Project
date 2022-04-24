/**
 * Names:Arturo Olmos, Jaehyeon Park, Miguel Ortiz
 * Instructor:Daniel Mejia
 * Assignment: Programming Assignment 4
 * Course: CS3331
 * Date: 3/13/2022
 * Honesty statement: I did not receive any help from anyone.
 */
import java.util.Scanner;
/**
 * @author Arturo Olmos
 *this is the class where the main method runs, it only handles a little of the user interface
 */
public class RunBank{
	static final String MANAGERPIN = "0000";

	/**
	 * Where everything runs, it has some code which handles mainly the starting meni
	 * @param args arguments
	 */
	public static void main(String[] args){
		//populates the main data structures
		CustomerCollection customers = new CustomerCollection();
		ItemCollection items = new ItemCollection();
		//object that handles the user input
		Scanner userInput = new Scanner(System.in);
		//menus for each user, manager/customer
		ManagerMenu managerMenu = new ManagerMenu(userInput,customers,items);
		CustomerMenu customerMenu = new CustomerMenu(userInput,customers,items);
		//handles the user option
		int option1;
		String optionInString = null;
		//handles the exit of the program
		boolean exit = false;
		//ensures ide does not mishandle double execution at the start which may still happen
		boolean alreadyExec;
		//begining menu asks to see what type of user
		while(!exit){
			alreadyExec = false;
			System.out.println("Hello Welcome to Miners Bank!");
			System.out.println("What would you like to do today?");
			System.out.println("(Note: do not add unnecessary spaces, results may be affected)");
			System.out.println("1.Log In");
			System.out.println("2.Create New Account");
			System.out.println("Enter \"EXIT\" to end the session");
			//ensure proper option is entered
			try{
				optionInString = userInput.nextLine();
				option1 = Integer.parseInt(optionInString);
			}
			catch(Exception e){
				//handling issues with intellij, it sometimes runs the code twice
				if(alreadyExec){
					continue;
				}
				if(optionInString.equalsIgnoreCase("EXIT")){
					exit = true;
					System.out.println("Thank you have a nice day!");
				}
				if(!exit){
					System.out.println("ERROR: Please enter 1-2 or \"EXIT\"");
					System.out.println("Restarting in");
					for(int i = 3;i > 0;i--){
						System.out.println(i);
						try {
							Thread.sleep(1000);
						}catch (InterruptedException et){
							//nothing crazy should happen here
							et.printStackTrace();
						}
					}
				}
				System.out.println("################################################################################");
				alreadyExec = true;
				continue;
			}
			switch(option1){
			case 1://go to the user menu
				int attempts = 3;
				while (attempts > 0) {
					System.out.println("################################################################################");
					System.out.print("Please enter your pin: ");
					String pinStr = userInput.nextLine();
					if(pinStr.length() != 4){
						System.out.println("Error: Pin is entered in the following format ####");
						attempts--;
						continue;
					}
					//ensure the pin is correct
					try {
						Integer.parseInt(pinStr);
					} catch (Exception ex) {
						System.out.println("Error: Pin is entered in the following format ####");
						attempts--;
						continue;
					}
					if ((pinStr.length() != 4)) {
						System.out.println("Error: Pin is entered in the following format ####");
						attempts--;
						continue;
					}
					//at this point a valid pin has been entered
					System.out.println("################################################################################");
					if(pinStr.equals(MANAGERPIN)){
						//manager pin was entered
						managerMenu.display(null);
					}else {
						//search for the customer if anything else
						CustomerCollectionIterator cusIter = customers.createIterator();
						boolean userFound = false;
						while(cusIter.hasNext()) {
							Customer cus = cusIter.next();
							if (cus.getPin().equals(pinStr)) {
								userFound = true;
								customerMenu.display(cus);
								break;
							}
						}
						//let the user know no user was found
						if(!userFound) {
							System.out.println("No user found");
							attempts--;
							continue;
						}
					}
						//break out of the loop to go back to the main menu
						break;
				}
				System.out.println("################################################################################");
				if(attempts <= 0){
					System.out.println("ERROR: Attempts limit reached");
					System.out.println("Returning to main menu in");
					for(int i = 3;i > 0;i--){
						System.out.println(i);
						try {
							Thread.sleep(1000);
						}catch (InterruptedException e){
							//nothing crazy should happen here
							e.printStackTrace();
						}
					}
					System.out.println("################################################################################");
				}
				break;
			case 2://menu for creating user
					System.out.println("################################################################################");
					customerMenu.userCreation();
					System.out.println("################################################################################");
				break;
			default:
				//handling issues with intellij, it sometimes runs the code twice
				if(alreadyExec){
					continue;
				}
				if(optionInString.equalsIgnoreCase("EXIT")){
					exit = true;
					System.out.println("Thank you have a nice day!");
				}
				if(!exit){
					System.out.println("ERROR: Please enter 1-2 or \"EXIT\"");
					System.out.println("Restarting in");
					for(int i = 3;i > 0;i--){
						System.out.println(i);
						try {
							Thread.sleep(1000);
						}catch (InterruptedException e){
							//nothing crazy should happen here
							e.printStackTrace();
						}
					}
				}
				System.out.println("################################################################################");
				alreadyExec = true;
			}
		}
		// this line updates and generates the new file with updated information when user exits the system
		customers.updateCSVFile();
	}
}