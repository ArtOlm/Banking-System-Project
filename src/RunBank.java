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

	public static void main(String[] args){
		//populates the main data structures
		CustomerCollection customers = new CustomerCollection(Utilities.getInstance().populateCustomers());
		ItemCollection items = new ItemCollection(Utilities.getInstance().populateItems());
		//object that handles the user input
		Scanner userInput = new Scanner(System.in);
		//handles the menus depending on the user
		ManagerMenu managerMenu = new ManagerMenu(userInput,customers,items);
		UserMenu userMenu = new UserMenu(userInput,customers,items);
		//handles the user option
		int option1;
		//handles the exit of the program
		boolean exit = false;
		//ensures ide does not mishandle double execution at the start which may still happen
		boolean alreadyExec;
		//begining menu asks to see what type of user
		while(!exit){
			alreadyExec = false;
			System.out.println("Hello Welcome to Miners Bank, who are you?(press 3 to Create Account 4 to exit)");
			System.out.println("(Note: do not add unnecessary spaces, it may cause problems)");
			System.out.println("1.Customer");
			System.out.println("2.Manager");
			System.out.println("3.Create account");
			System.out.println("4.Exit");
			//ensure proper option is entered
			try{
				option1 = Integer.parseInt(userInput.nextLine());
			}
			catch(Exception e){
				//handling issues with intellij, it sometimes runs the code twice
				if(alreadyExec){
					continue;
				}
				System.out.println("ERROR: Please enter 1-4");
				System.out.println("Restarting in");
				for(int i = 3;i > 0;i--){
					System.out.println(i);
					try {
						Thread.sleep(1000);
					}catch (InterruptedException e2){
						//nothing crazy should happen here
						e2.printStackTrace();
					}
				}
				System.out.println("-------------------------------------------");
				alreadyExec = true;
				continue;
			}
			switch(option1){
			case 1://go to the user menu
					System.out.println("-------------------------------------------");
					userMenu.display();
					System.out.println("-------------------------------------------");
				break;
			case 2://go to the managed menu
					System.out.println("-------------------------------------------");
					managerMenu.display();
					System.out.println("-------------------------------------------");
			    break;
			case 3://menu for creating user
					System.out.println("-------------------------------------------");
					userMenu.userCreation();
					System.out.println("-------------------------------------------");
				break;
			case 4://exit
					System.out.println("Thank you,have a great day");
					exit = true;
				break;
			default:
				//handling issues with intellij, it sometimes runs the code twice
				if(alreadyExec){
					continue;
				}
				System.out.println("ERROR: Please enter 1-4");
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
				System.out.println("-------------------------------------------");
				alreadyExec = true;
				continue;
			}
		}
		// this line updates and generates the new file with updated information when user exits the system
		Utilities.getInstance().updateCustomerInfo(customers);
	}
}