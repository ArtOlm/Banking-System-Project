import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;

public class RunBank{

    //This method asks the user which account does it wants to use
    public static String whichAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.println("To which of your accounts would you like to perform your transaction?");
        System.out.println("1) Checking");
        System.out.println("2) Savings");
        System.out.println("3) Credit");
        String path = sc.nextLine();
        boolean rightAns = true;
        boolean keepUse = false;
        while (rightAns) {
            if (path.equals("1") || path.equals("2") || path.equals("3")) {
                System.out.println("Ok");
                rightAns = false;
                keepUse = true;
            } else {
                System.out.println("C'mon bruh, just pick one of the letters.");
            }
        }
        return path;
    }

    //This mehtods receives the action.csv readings and determines which accounts to manage


    //This method asks the user if it wants to perform another transaction
    public static boolean anodaTrans() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to perform another transaction?");
        System.out.println("Yes(Y)    No(N)");
        String path = sc.nextLine();
        boolean rightAns = true;
        boolean keepUse = false;
        while (rightAns) {
            if (path.equals("Y")) {
                System.out.println("Ok");
                rightAns = false;
                keepUse = true;
            } else if (path.equals("N")) {
                rightAns = false;
            } else {
                System.out.println("C'mon bruh, just pick one of the letters.");
            }
        }
        return keepUse;
    }

    public static String searchUserTrans(HashMap DBIn) {
        Scanner sc = new Scanner(System.in);
        String fname = "owo";
        boolean userExists = true;
        while (userExists) {
            System.out.println("Welcome, please enter your name");
            fname = sc.nextLine();
            if (DBIn.get(fname) == null) {
                System.out.println("Sorry, it there is no user registered with that name");
            } else {
                userExists = false;
            }
        }
        return fname;
    }

    public static String searchUser(HashMap DBIn) {
        Scanner sc = new Scanner(System.in);
        String fname = "owo";
        boolean userExists = true;
        while (userExists) {
            System.out.println("Welcome, please enter your name");
            fname = sc.nextLine();
            if (DBIn.get(fname) == null) {
                System.out.println("Sorry, it there is no user registered with that name");
                System.out.println("Would you like to try again?");
                System.out.println("Yes(Y)    No(N)");
                boolean rightAns = true;
                while (rightAns) {
                    String path = sc.nextLine();
                    if (path.equals("Y")) {
                        System.out.println("Ok");
                        rightAns = false;
                    } else if (path.equals("N")) {
                        System.out.println("Thank you for using our service");

                        System.exit(0);
                    } else {
                        System.out.println("C'mon bruh, just pick one of the letters.");
                    }
                }
            } else {
                userExists = false;
            }
        }
        return fname;
    }

    public static void main(String[] args) {
        HashMap<String, Customer> bankDB = new HashMap<>();
        HashMap<String, Checking> checkingDB = new HashMap<>();
        HashMap<Integer, Checking> checkingANDB = new HashMap<>();
        HashMap<String, Savings> savingsDB = new HashMap<>();
        HashMap<Integer, Savings> savingsANDB = new HashMap<>();
        HashMap<String, Credit> creditDB = new HashMap<>();
        HashMap<Integer, Credit> creditANDB = new HashMap<>();
        Checking tempChecking = new Checking();
        tempChecking.setAccountNumber(1);
        tempChecking.setBalance(1600.0);
        Savings tempSave = new Savings();
        tempSave.setAccountNumber(2);
        tempSave.setBalance(34000.0);
        Credit tempCre = new Credit();
        tempCre.setAccountNumber(3);
        tempCre.setBalance(669.0);
        tempCre.setCreScore(569);
        tempCre.setCreLimit(1900.0);
        Customer tempCustomer = new Customer("Tom", "Nook", "Center", "12/14/01",
                1, "Diamadre", "Animal Crossing", 654321, "pipupipo", tempChecking, tempCre, tempSave);
        bankDB.put("Tom Nook", tempCustomer);
        checkingDB.put("Tom Nook", tempChecking);
        checkingANDB.put(1, tempChecking);
        savingsDB.put("Tom Nook", tempSave);
        savingsANDB.put(2, tempSave);
        creditDB.put("Tom Nook", tempCre);
        creditANDB.put(3, tempCre);
        Checking tempChecking2 = new Checking();
        tempChecking.setAccountNumber(4);
        tempChecking.setBalance(2300.0);
        Savings tempSave2 = new Savings();
        tempSave.setAccountNumber(5);
        tempSave.setBalance(70000.0);
        Credit tempCre2 = new Credit();
        tempCre.setAccountNumber(6);
        tempCre.setBalance(420.0);
        tempCre.setCreScore(678);
        tempCre.setCreLimit(2000.0);
        Customer tempCustomer2 = new Customer("Isabelle", "Canelita", "Center", "11/08/12",
                2, "Diamadre", "Animal Crossing", 654322, "pipupipa", tempChecking2, tempCre2, tempSave2);
        bankDB.put("Isabelle", tempCustomer2);
        checkingDB.put("Isabelle", tempChecking2);
        savingsDB.put("Isabelle", tempSave2);
        creditDB.put("Isabelle", tempCre2);

        //Scanner sc = new Scanner(System.in);
        String fName = searchUser(bankDB);
        Customer myAccount = bankDB.get(fName);
        Checking myChecking = checkingDB.get(fName);
        Savings mySavings = savingsDB.get(fName);
        Credit myCredit = creditDB.get(fName);
        System.out.println("Welcome, " + myAccount.getFirstName());
        boolean keepUsing = true;
        try {
            FileWriter myWriter = new FileWriter("log.txt");
            Scanner sc = new Scanner(System.in);
            System.out.println("Sos admin?");
            String role = sc.nextLine();
            if (role.equals("Si")) {
                System.out.println("What would you like to do?");
                System.out.println("1) Inquire a user");
                System.out.println("2) Make Transactions");
                System.out.println("3) Make bank statement for a user");
                System.out.println("Would you like to inquire by:");
                System.out.println("A. Name");
                System.out.println("B. Number");
                String ans = sc.nextLine();
                boolean rightAns = true;
                while (rightAns) {
                    if (ans.equals("A")) {
                        String name = "";
                        boolean leuserExists = true;
                        while (leuserExists) {
                            System.out.println("Who’s account would you like to inquire about?");
                            name = sc.nextLine();
                            if (bankDB.get(name) == null) {
                                System.out.println("Sorry, it there is no user registered with that name");
                                System.out.println("Would you like to try again?");
                                System.out.println("Yes(Y)    No(N)");
                                boolean lerightAns = true;
                                while (lerightAns) {
                                    String path = sc.nextLine();
                                    if (path.equals("Y")) {
                                        System.out.println("Ok");
                                        rightAns = false;
                                    } else if (path.equals("N")) {
                                        System.out.println("Thank you for using our service");
                                        System.exit(0);
                                    } else {
                                        System.out.println("C'mon bruh, just pick one of the letters.");
                                    }
                                }
                            } else {
                                leuserExists = false;
                            }
                        }
                        Customer leAccount = bankDB.get(name);
                        System.out.println("brrr");
                        if (!anodaTrans()) {
                            System.out.println("Thank you for using our service");
                            System.exit(0);
                        }
                    } else if (ans.equals("B")) {
                        String name = "";
                        boolean leuserExists = true;
                        while (leuserExists) {
                            System.out.println("Welcome, please enter your name");
                            fName = sc.nextLine();
                            if (bankDB.get(fName) == null) {
                                System.out.println("Sorry, it there is no user registered with that name");
                                System.out.println("Would you like to try again?");
                                System.out.println("Yes(Y)    No(N)");
                                boolean lerightAns = true;
                                while (lerightAns) {
                                    String path = sc.nextLine();
                                    if (path.equals("Y")) {
                                        System.out.println("Ok");
                                        rightAns = false;
                                    } else if (path.equals("N")) {
                                        System.out.println("Thank you for using our service");
                                        System.exit(0);
                                    } else {
                                        System.out.println("C'mon bruh, just pick one of the letters.");
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("C'mon bruh, just pick one of the letters.");
                    }
                }
            } else {
                while (keepUsing) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. Inquire");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. Payment");
                    System.out.println("5. Transfer");
                    System.out.println("6. Create new user");
                    String answer = sc.nextLine();

                    if (answer.equals("1")) {
                        System.out.println(myChecking.inquire());
                        System.out.println(mySavings.inquire());
                        System.out.println(myCredit.inquire());
                        myWriter.write(myAccount.getFirstName() + ", checked its balance");
                        if (!anodaTrans()) {
                            System.out.println("Thank you for using our service");
                            System.exit(0);
                        }
                    }else if (answer.equals("2")) {
                        System.out.println("Please enter the amount to deposit:");
                        String leDeposit = sc.nextLine();
                        double deposit = Double.parseDouble(leDeposit);
                        String path = whichAccount();
                        if(path.equals("1")){
                            myChecking.deposit(deposit);
                            System.out.println("Success! Your checking balance is " + myChecking.inquire());
                        }
                        else if(path.equals("2")){
                            mySavings.deposit(deposit);
                            System.out.println("Success! Your saving balance is " + mySavings.inquire());
                        }
                        else{
                            myCredit.deposit(deposit);
                            System.out.println("Success! Your credit balance is " + myCredit.inquire());
                        }
                        if (!anodaTrans()) {
                            System.out.println("Thank you for using our service");
                            System.exit(0);
                        }
                    }
                    else if(answer.equals("3")) {
                        System.out.println("Please enter the amount to witdraw:");
                        String leWithdraw = sc.nextLine();
                        double withdraw = Double.parseDouble(leWithdraw);
                        if (myChecking.getBalance() < withdraw) {
                            System.out.println("Sorry, the amount you entered surpasses your current balance.");
                            System.out.println("Would you like to try again?");
                            System.out.println("Yes(Y)    No(N)");
                            String ans = sc.nextLine();
                            boolean rightAns = true;
                            while (rightAns) {
                                if (ans.equals("Y")) {
                                    System.out.println("Ok");
                                    rightAns = false;
                                } else if (ans.equals("N")) {
                                    if (!anodaTrans()) {
                                        System.out.println("Thank you for using our service");
                                        System.exit(0);
                                    }
                                    break;
                                } else {
                                    System.out.println("C'mon bruh, just pick one of the letters.");
                                }
                            }
                        }
                        String path = whichAccount();
                        if(path.equals("1")){
                            myChecking.withdraw(withdraw);
                            System.out.println("Success! Your checking balance is " + myChecking.inquire());
                            myWriter.write(myAccount.getFirstName() + ", withdrawed $" + withdraw);
                        }
                        else if(path.equals("2")){
                            mySavings.withdraw(withdraw);
                            System.out.println("Success! Your saving balance is " + mySavings.inquire());
                            myWriter.write(myAccount.getFirstName() + ", withdrawed $" + withdraw);
                        }
                        else{
                            myCredit.withdraw(withdraw);
                            System.out.println("Success! Your credit balance is " + myCredit.inquire());
                            myWriter.write(myAccount.getFirstName() + ", withdrawed $" + withdraw);
                        }
                        if (!anodaTrans()) {
                            System.out.println("Thank you for using our service");
                            System.exit(0);
                        }
                    } else if (answer.equals("4")) {
                        String friendName = searchUser(bankDB);

                        //Creates the instance of the receiver
                        Checking friendChecking = checkingDB.get(friendName);
                        Savings friendSaving = savingsDB.get(friendName);
                        Credit friendCredit = creditDB.get(friendName);

                        //This executes the deposit method with the amount the user inputs
                        System.out.println("Insert the amount to transfer:");
                        String pay = sc.nextLine();
                        double payment = (Double.parseDouble(pay));
                        if (myChecking.getBalance() < payment) {
                            System.out.println("Sorry, the amount you entered surpasses your current balance.");
                            System.out.println("Would you like to try again?");
                            System.out.println("Yes(Y)    No(N)");
                            String ans = sc.nextLine();
                            boolean rightAns = true;
                            while (rightAns) {
                                if (ans.equals("Y")) {
                                    System.out.println("Ok");
                                    rightAns = false;
                                } else if (ans.equals("N")) {
                                    if (!anodaTrans()) {
                                        System.out.println("Thank you for using our service");
                                        System.exit(0);
                                    }
                                    break;
                                } else {
                                    System.out.println("C'mon bruh, just pick one of the letters.");
                                }
                            }
                        }
                        String path = whichAccount();
                        System.out.println("For the receiver's account...");
                        String dest = whichAccount();
                        if(path.equals("1")){
                            myChecking.withdraw(payment);
                            if(dest.equals("1")){
                                friendChecking.deposit(payment);
                                System.out.println("Success! Your checking balance is " + myChecking.inquire());
                                System.out.println("Your friend's checking balance is " + friendCredit.inquire());
                            }
                            else if(path.equals("2")){
                                friendSaving.deposit(payment);
                                System.out.println("Success! Your saving balance is " + mySavings.inquire());
                                System.out.println("Your friend's saving balance is " + friendSaving.inquire());
                            }
                            else{
                                friendCredit.deposit(payment);
                                System.out.println("Success! Your credit balance is " + myCredit.inquire());
                                System.out.println("Your friend's credit balance is " + friendCredit.inquire());
                            }
                        }
                        else if(path.equals("2")){
                            mySavings.withdraw(payment);
                            if(dest.equals("1")){
                                friendChecking.deposit(payment);
                                System.out.println("Success! Your checking balance is " + myChecking.inquire());
                                System.out.println("Your friend's checking balance is " + friendChecking.inquire());
                            }
                            else if(dest.equals("2")){
                                friendSaving.deposit(payment);
                                System.out.println("Success! Your saving balance is " + mySavings.inquire());
                                System.out.println("Your friend's saving balance is " + friendSaving.inquire());
                            }
                            else{
                                friendCredit.deposit(payment);
                                System.out.println("Success! Your credit balance is " + myCredit.inquire());
                                System.out.println("Your friend's credit balance is " + friendCredit.inquire());
                            }
                        }
                        else{
                            myCredit.deposit(payment);
                            if(dest.equals("1")){
                                friendChecking.deposit(payment);
                                System.out.println("Success! Your checking balance is " + myChecking.inquire());
                                System.out.println("Your friend's checking balance is " + friendChecking.inquire());
                            }
                            else if(dest.equals("2")){
                                friendSaving.deposit(payment);
                                System.out.println("Success! Your saving balance is " + mySavings.inquire());
                                System.out.println("Your friend's saving balance is " + friendSaving.inquire());
                            }
                            else{
                                friendCredit.deposit(payment);
                                System.out.println("Success! Your credit balance is " + myCredit.inquire());
                                System.out.println("Your friend's credit balance is " + friendCredit.inquire());
                            }
                        }

                        if (!anodaTrans()) {
                            System.out.println("Thank you for using our service");
                            System.exit(0);
                        }
                    }
                    else if(answer.equals("5")){
                        System.out.println("Insert the amount to transfer:");
                        String pay = sc.nextLine();
                        double payment = (Double.parseDouble(pay));
                        if (myChecking.getBalance() < payment) {
                            System.out.println("Sorry, the amount you entered surpasses your current balance.");
                            System.out.println("Would you like to try again?");
                            System.out.println("Yes(Y)    No(N)");
                            String ans = sc.nextLine();
                            boolean rightAns = true;
                            while (rightAns) {
                                if (ans.equals("Y")) {
                                    System.out.println("Ok");
                                    rightAns = false;
                                } else if (ans.equals("N")) {
                                    if (!anodaTrans()) {
                                        System.out.println("Thank you for using our service");
                                        System.exit(0);
                                    }
                                    break;
                                } else {
                                    System.out.println("C'mon bruh, just pick one of the letters.");
                                }
                            }
                        }
                        String path = whichAccount();
                        System.out.println("For the account to transfer...");
                        String dest = whichAccount();
                        if(path.equals("1")){
                            myChecking.withdraw(payment);
                            if(dest.equals("1")){
                                myChecking.deposit(payment);
                                System.out.println("Success! Your checking balance is " + myChecking.inquire());
                            }
                            else if(path.equals("2")){
                                mySavings.deposit(payment);
                                System.out.println("Success! Your saving balance is " + mySavings.inquire());
                            }
                            else{
                                myCredit.deposit(payment);
                                System.out.println("Success! Your credit balance is " + myCredit.inquire());
                            }
                        }
                        else if(path.equals("2")){
                            mySavings.withdraw(payment);
                            if(dest.equals("1")){
                                myChecking.deposit(payment);
                                System.out.println("Success! Your checking balance is " + myChecking.inquire());
                            }
                            else if(dest.equals("2")){
                                mySavings.deposit(payment);
                                System.out.println("Success! Your saving balance is " + mySavings.inquire());}
                            else{
                                myCredit.deposit(payment);
                                System.out.println("Success! Your credit balance is " + myCredit.inquire());
                            }
                        }
                        else{
                            myCredit.deposit(payment);
                            if(dest.equals("1")){
                                myChecking.deposit(payment);
                                System.out.println("Success! Your checking balance is " + myChecking.inquire());
                            }
                            else if(dest.equals("2")){
                                mySavings.deposit(payment);
                                System.out.println("Success! Your saving balance is " + mySavings.inquire());
                            }
                            else{
                                myCredit.deposit(payment);
                                System.out.println("Success! Your credit balance is " + myCredit.inquire());
                            }
                        }

                        if (!anodaTrans()) {
                            System.out.println("Thank you for using our service");
                            System.exit(0);
                        }
                    }
                    else if(answer.equals("6")){
                        Random rand = new Random();
                        System.out.println("First Name:");
                        String firstName = sc.nextLine();
                        System.out.println("Last Name:");
                        String lastName = sc.nextLine();
                        System.out.println("Address:");
                        String address = sc.nextLine();
                        System.out.println("Date of Birth:");
                        String dob = sc.nextLine();
                        int leID = bankDB.size() + 1;
                        System.out.println("Your ID is" + leID);
                        System.out.println("City:");
                        String City = sc.nextLine();
                        System.out.println("State:");
                        String State = sc.nextLine();
                        System.out.println("Zip:");
                        String Zip = sc.nextLine();
                        int leZip = Integer.parseInt(Zip);
                        System.out.println("Phone Number:");
                        String phoneNum = sc.nextLine();
                        int checkingNum = 0;
                        boolean thereIsNum = true;
                        while(thereIsNum){
                            int RcheckingNum = rand.nextInt(9);
                            if(checkingANDB.get(RcheckingNum) == null){
                                checkingNum = RcheckingNum;
                                thereIsNum = false;
                            }
                        }
                        System.out.println("Your checking account number is " + checkingNum);
                        int savingNum = 0;
                        thereIsNum = true;
                        while(thereIsNum){
                            int RsavingNum = rand.nextInt(9);
                            if(checkingANDB.get(RsavingNum) == null){
                                savingNum = RsavingNum;
                                thereIsNum = false;
                            }
                        }
                        System.out.println("Your saving account number is " + savingNum);
                        int creditNum = 0;
                        thereIsNum = true;
                        while(thereIsNum){
                            int RcreditNum = rand.nextInt(9);
                            if(checkingANDB.get(RcreditNum) == null){
                                checkingNum = RcreditNum;
                                thereIsNum = false;
                            }
                        }
                        System.out.println("Your credit account number is " + creditNum);
                        System.out.println("Enter Checking balance:");
                        String checkingBal = sc.nextLine();
                        double checkBal = Double.parseDouble(checkingBal);
                        System.out.println("Enter Savings balance:");
                        String savingBal = sc.nextLine();
                        double saveBal = Double.parseDouble(savingBal);
                        System.out.println("Enter Credit balance:");
                        String creditBal = sc.nextLine();
                        double creBal = Double.parseDouble(checkingBal);
                        System.out.println("Credit Score:");
                        String creditScore = sc.nextLine();
                        int creScore = Integer.parseInt(creditScore);
                        double creLimit = 0;
                        if(creScore < 580) {
                            double random_int = (int)Math.floor(Math.random()*(699-100+1)+100);
                        }
                        else if(creScore <= 669 && creScore >= 580) {
                            double random_int = (int)Math.floor(Math.random()*(4999-700+1)+700);
                        }
                        else if(creScore <= 739 && creScore >= 670) {
                            double random_int = (int)Math.floor(Math.random()*(7499-5000+1)+5000);
                        }
                        else if(creScore <= 799 && creScore >= 740) {
                            double random_int = (int)Math.floor(Math.random()*(15999-7500+1)+7500);
                        }
                        else if(creScore >= 800) {
                            double random_int = (int)Math.floor(Math.random()*(25000-1600+1)+1600);
                        }
                        Checking newCheck = new Checking();
                        newCheck.setBalance(checkBal);
                        newCheck.setAccountNumber(checkingNum);
                        Savings newSave = new Savings();
                        newSave.setBalance(saveBal);
                        newSave.setAccountNumber(savingNum);
                        Credit newCredit = new Credit();
                        newCredit.setAccountNumber(creditNum);
                        newCredit.setBalance(creBal);
                        newCredit.setCreLimit(creLimit);
                        newCredit.setCreScore(creScore);
                        Customer newUser = new Customer(firstName, lastName, address, dob, leID, City, State,
                                leZip, phoneNum, newCheck, newCredit, newSave);
                        System.out.println("New user, " + newUser.getFirstName() + " created.");
                        if (!anodaTrans()) {
                            System.out.println("Thank you for using our service");
                            System.exit(0);
                        }
                    }
                    else {
                        System.out.println("brrr");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}