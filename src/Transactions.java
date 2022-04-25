/**
 * @author Arturo Olmos
 * @version 4.0
 * class helps handle all transaction processes
 */
public class Transactions{
	private static Transactions transactionHandler;
	final private String NEGATIVE_VALUE_MESSAGE = "Error: Cannot deposit negative values";
	final private String NOT_ENOUGH_FUNDS_MESSAGE  = "Error: cannot deposit more than credit balance";
	final private String OVER_CREDIT_LIMIT_MESSAGE = "Error: Not enough funds, check credit limit";
	final private String MORE_THAN_CREDIT_BALANCE_MESSAGE = "Error: Transaction is more than the balance";
	final private String UNKNOWN_EXCEPTION_MESSAGE  = "Error: Something went wrong, ensure all inputs are correct and try again";
	private Transactions(){
	}
	
	/**
	 *
	 * @return
	 * returns the only instance of this class
	 */
	public static Transactions getInstance(){
		if(transactionHandler == null){
			transactionHandler = new Transactions();
		}
		return transactionHandler;
	}
	/**
	 * handles the inquire balance procedure
	 * @param userAccount a users account used to return their balance, not user input
	 * @param accType type of account searched for, is user input
	 * @return returns a balance of the accType provided
	 * @throws TransactionException exception for bad transaction
	 */
	public double checkBalance(Customer userAccount,String accType) throws TransactionException{
		//compare to see what account user is looking for
		if(accType.equalsIgnoreCase("Checking")){
			return userAccount.getCheck().getBalance();
		}
		else if (accType.equalsIgnoreCase("Savings")) {
			return userAccount.getSave().getBalance();
		}
		else if(accType.equalsIgnoreCase("Credit")){
			return userAccount.getCredit().getBalance();
		}
		else {//user can enter something else this is handled in the Menu Class
			throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
		}
	}
	/**
	 * handles a users deposit
	 * @param userAccount object containing Customer information
	 * @param acountType account type where money is deposited to
	 * @param deposit ammount of money deposited
	 * @throws TransactionException exception for bad transaction
	 */
	public void userDeposit(Customer userAccount,String acountType,double deposit) throws TransactionException{
		//set a maximum and minimum value to how much they can deposit at once
		if(deposit < 0){
			throw new TransactionException(this.NEGATIVE_VALUE_MESSAGE);
		}
		//check to see what account is being deposited to
		if(acountType.equalsIgnoreCase("Checking")){
		double newBalance = userAccount.getCheck().getBalance() + deposit;
		userAccount.getCheck().setBalance(newBalance);//update balance accordingly
		} else if (acountType.equalsIgnoreCase("Savings")) {
			double newBalance = userAccount.getSave().getBalance() + deposit;
			userAccount.getSave().setBalance(newBalance);	
		} else if (acountType.equalsIgnoreCase("Credit")) {
			if(userAccount.getCredit().getBalance() - deposit < (-0.009)){
				throw new TransactionException(this.MORE_THAN_CREDIT_BALANCE_MESSAGE);
			}
			double newBalance = userAccount.getCredit().getBalance() - deposit;//deposit is being subtracted because it is money being paid
			userAccount.getCredit().setBalance(newBalance);
		} else{//there is an error, this is handled in Menu
			throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
		}
	}
	/**
	 * handles a users account transfer(e.g checking to savings)
	 * @param userAccount Customer object containing user information
	 * @param from account type money is transferred from
	 * @param to account money is transferred to
	 * @param transfer amount being transferred
	 * @throws TransactionException for bad transaction
	 */
	public void accountTransfer(Customer userAccount,String from,String to,double transfer) throws TransactionException{
		//ensure the transfer is not to the same account
		if(from.equalsIgnoreCase(to)){
			throw new TransactionException("ERROR: can only transfer to a different account type");
		} else if(transfer < 0){
			throw new TransactionException(this.NEGATIVE_VALUE_MESSAGE);
		}
		//check which account transferring from and to
		//lot at the Checking to see its generic procedure
		//credit procedure is a little different
		if(from.equalsIgnoreCase("Checking")){
			double nb = userAccount.getCheck().getBalance() - transfer;
			//if the transfer generates a negative value then we do not proceed
			//this is done for all other combinations of accounts below
			if(nb < 0){
				throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);
			}
			//to is the destination which the money will go to
		    if (to.equalsIgnoreCase("Savings")) {
				//update balance
		    	userAccount.getSave().setBalance(userAccount.getSave().getBalance() + transfer);
				userAccount.getCheck().setBalance(nb);
			} else if (to.equalsIgnoreCase("Credit")) {
				if(userAccount.getCredit().getBalance() - transfer < 0){
					throw new TransactionException(this.MORE_THAN_CREDIT_BALANCE_MESSAGE);
				}
				//update balance
				userAccount.getCheck().setBalance(nb);
				userAccount.getCredit().setBalance(userAccount.getCredit().getBalance() - transfer);
			} else{//at this point there is an error and then we do nothing
				throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
			}	
		} else if (from.equalsIgnoreCase("Savings")) {//this is done for savings, similar to checking
			double nb = userAccount.getSave().getBalance() - transfer;
			//check if the balance is less than 0
			if(nb < 0){
				throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);
			}
		    if (to.equalsIgnoreCase("Checking")) {
		    	userAccount.getCheck().setBalance(userAccount.getCheck().getBalance() + transfer);
				userAccount.getSave().setBalance(nb);
			} else if (to.equalsIgnoreCase("Credit")) {
				if(userAccount.getCredit().getBalance() - transfer < 0){
					throw new TransactionException(this.MORE_THAN_CREDIT_BALANCE_MESSAGE);
				}
				userAccount.getCredit().setBalance(userAccount.getCredit().getBalance() - transfer);
				userAccount.getSave().setBalance(nb);
			} else{
				throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
			}	
		} else if (from.equalsIgnoreCase("Credit")) {
			double nb = userAccount.getCredit().getBalance() + transfer;
			//ensure user does not go over the limit
			if(nb > userAccount.getCredit().getLimit()){
				throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);
			}
			userAccount.getCredit().setBalance(nb);
			//look for the account the transfer will go to
		    if (to.equalsIgnoreCase("Savings")) {
		    	userAccount.getSave().setBalance(userAccount.getSave().getBalance() + transfer);	
			} else if (to.equalsIgnoreCase("Checking")) {
				userAccount.getCheck().setBalance(userAccount.getCheck().getBalance() + transfer);
			} else{//at this point something went wrong, and we return to the main menu
				throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
			}
		} else{//something was wrong from the user, this is handled in the Menu Class
			throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
		}
	}
	/**
	 * handles a users withdraw procedure
	 * @param userAccount Customer object containing information about user
	 * @param accType type of account user will withdraw from
	 * @param withdrawl amount of money user wants to withdraw
	 * @throws TransactionException for bad transaction
	 */
	public void userWithdraw(Customer userAccount,String accType,double withdrawl) throws TransactionException{
		//ensure the withdaw is not negative
		if(withdrawl < 0){
			throw new TransactionException(this.NEGATIVE_VALUE_MESSAGE);
		}
		//check what account they want to withdaw from
		//the process for the withdraw is the same for all accounts except Credit
		if(accType.equalsIgnoreCase("Checking")){
			// new balance
			double nb = userAccount.getCheck().getBalance() - withdrawl;
			if(nb < 0){//ensure the user does not withdraw more money than what they have
				throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);
			}
			//sets new baalnce if all goes well
			userAccount.getCheck().setBalance(nb);
		} else if (accType.equalsIgnoreCase("Savings")) {
			double nb = userAccount.getSave().getBalance() - withdrawl;
			if(nb < 0){
				throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);
			}
			userAccount.getSave().setBalance(nb);	
		} else if (accType.equalsIgnoreCase("Credit")) {
			double nb = userAccount.getCredit().getBalance() + withdrawl;
			if(nb > userAccount.getCredit().getLimit()) {//ensure credit balance does not pass the limit
				throw new TransactionException(this.OVER_CREDIT_LIMIT_MESSAGE);
			}
			userAccount.getCredit().setBalance(nb);
		} else{
			throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
		}
	}
	/**
	 * handles a user's transaction to another user
	 * @param userAccount Customer object containing all information
	 * @param userToPay user that will receive money
	 * @param from the type of account user uses to pay
	 * @param to the type of account the user pays to
	 * @param pay the amount being paid
	 * @throws TransactionException for bad transaction
	 */
	public void transactionToOther(Customer userAccount,Customer userToPay,String from,String to,double pay) throws TransactionException{
		//ensure pay amount is not a negative value
		if(pay < 0){
			throw new TransactionException(this.NEGATIVE_VALUE_MESSAGE);
		}
		//check if the user is trying to pay themselves
        if(userAccount.getID() == userToPay.getID()){
			throw new TransactionException("Error: Cannot pay yourself");
        }
		//check whcih account we a transfering from and to
		if(from.equalsIgnoreCase("Checking")){
			double nb = userAccount.getCheck().getBalance() - pay;
			//if the transer generates a negative value then we do not proceed
			//this is done for all other combinations of accounts below
			if(nb < 0) {
				throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);
			}
		    if (to.equalsIgnoreCase("Savings")) {
		    	userToPay.getSave().setBalance(userToPay.getSave().getBalance() + pay);
				userAccount.getCheck().setBalance(nb);
			} else if (to.equalsIgnoreCase("Credit")) {
				if(userToPay.getCredit().getBalance() - pay < 0){
					throw new TransactionException(this.MORE_THAN_CREDIT_BALANCE_MESSAGE);
				}
				userToPay.getCredit().setBalance(userToPay.getCredit().getBalance() - pay);
				userAccount.getCheck().setBalance(nb);
			} else if (to.equalsIgnoreCase("Checking")) {
				userToPay.getCheck().setBalance(userToPay.getCheck().getBalance() + pay);
				userAccount.getCheck().setBalance(nb);
			} else{//at this point there is an error, then we do nothing, this is prevented in the Menu class
				throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
			}	
		}
		//transfer from savings to other
		else if (from.equalsIgnoreCase("Savings")) {
			double nb = userAccount.getSave().getBalance() - pay;
			//check for funds
			if(nb < 0) {
					throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);
			}
		    if (to.equalsIgnoreCase("Checking")) {
				//pay to checking
		    	userToPay.getCheck().setBalance(userToPay.getCheck().getBalance() + pay);
				userAccount.getSave().setBalance(nb);
			} else if (to.equalsIgnoreCase("Credit")) {
				//pay to credit
				//check for funds
				if(userToPay.getCredit().getBalance() - pay < 0){
					throw new TransactionException(this.MORE_THAN_CREDIT_BALANCE_MESSAGE);
				}
				//update info
				userToPay.getCredit().setBalance(userToPay.getCredit().getBalance() - pay);
				userAccount.getSave().setBalance(nb);
			} else if (to.equalsIgnoreCase("Savings")) {
				//pay to savings
				//update info
				userToPay.getSave().setBalance(userToPay.getSave().getBalance() + pay);
				userAccount.getSave().setBalance(nb);
			} else{
				//something went wrong
				throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
			}	
		} else if (from.equalsIgnoreCase("Credit")) {
			//paying from the credit
			double nb = userAccount.getCredit().getBalance() + pay;
			//check for funds
			if(nb > userAccount.getCredit().getLimit()){
				throw new TransactionException(this.OVER_CREDIT_LIMIT_MESSAGE);
			}
			//transfer to the appropriate account and update info
		    if (to.equalsIgnoreCase("Savings")) {
		    	userAccount.getSave().setBalance(userAccount.getSave().getBalance() + pay);
				userAccount.getCredit().setBalance(nb);
			} else if (to.equalsIgnoreCase("Checking")) {
				userToPay.getCheck().setBalance(userToPay.getCheck().getBalance() + pay);
				userAccount.getCredit().setBalance(nb);
			} else if (to.equalsIgnoreCase("Credit")) {
				if(userToPay.getCredit().getBalance() - pay < 0){
					throw new TransactionException(this.MORE_THAN_CREDIT_BALANCE_MESSAGE);
				}
				userToPay.getCredit().setBalance(userToPay.getCredit().getBalance() - pay);
				userAccount.getCredit().setBalance(nb);
			} else{//at this point something went wrong and we return to the main menu
				throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
			}
		} else{
			//something went wrong but it was not the user
			throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);
		}
	}
	/**
	 * handles the purchase of a user from miners mall
	 * @param userAccount user account, not provided by user
	 * @param accType account user will use, provided by user
	 * @param itemValue the price of an Item user will buy, not provided by user
	 * @throws TransactionException for bad transaction
	 */
	public void buyFromMinerMall(Customer userAccount,String accType,double itemValue)throws TransactionException{
		//check the account being used
		//look at the checking comparison it explains the generic concept of what is done
		if(accType.equalsIgnoreCase("Checking")){
			//nb is for new balance
			double nb = userAccount.getCheck().getBalance() - itemValue;
			if(nb < 0){//ensure user has funds

				throw new TransactionException(this.NOT_ENOUGH_FUNDS_MESSAGE);

			}
			//update balance
			userAccount.getCheck().setBalance(nb);
		} else if (accType.equalsIgnoreCase("Savings")) {
			//user cannot pay with savings
			throw new TransactionException("Error: Unable to use savings to purchase items!");
		} else if (accType.equalsIgnoreCase("Credit")) {
			//check for funds and update info
			double nb = userAccount.getCredit().getBalance() + itemValue;
			if(nb > userAccount.getCredit().getLimit()){//credit not allowed to continue if credit limit is reached

				throw new TransactionException(this.OVER_CREDIT_LIMIT_MESSAGE);

			}
			userAccount.getCredit().setBalance(nb);
		} else{//no proper account found, this is handled in Menu Class

			throw new TransactionException(this.UNKNOWN_EXCEPTION_MESSAGE);

		}
	}
}