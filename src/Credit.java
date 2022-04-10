/**
 * @author Arturo Olmos/Jaehyeon Park
 * @version 2.0
 * credit account of a customer
 */
public class Credit extends Account{
	//note:limit refers to the credit limit of a customer
	private int limit;
	private int score;

	/**
	 * default constructor
	 */
	public Credit(){

	}
	/**
	 *Constructor-sets all the attributes of the object
	 * @param accNum account number of the Credit account
	 * @param bal balance of the Credit account
	 * @param limit the limit of the balance of the Credit account
	 * @param score credit score of the Credit account
	 */
	public Credit(String accNum,double bal,int limit,int score){
		super(accNum,bal);
		this.limit = limit;
		this.score = score;

	}
	/**
	 * generates the credit limit based on the score if needed
	 * @param creditScore the credit score of the user
	 */
	public void generateCredit(int creditScore){
		this.limit = -9999;

		if(creditScore > 800){
			this.limit = (int)Math.floor(Math.random()*(25000-100+1)+16000);
		}else if(creditScore > 739){
			this.limit = (int)Math.floor(Math.random()*(15999-100+1)+7500);

		}else if (creditScore > 669){
			this.limit = (int)Math.floor(Math.random()*(7499-100+1)+5000);

		}else if (creditScore > 580){
			this.limit = (int)Math.floor(Math.random()*(4999-100+1)+700);

		}else if (creditScore > -1) {
			this.limit = (int)Math.floor(Math.random()*(699-100+1)+100);
		}
	}
	/**
	 *
	 * @param limit sets the limit to the Credit account balance
	 */
	public void setLimit(int limit){
		this.limit = limit;
	}

	/**
	 *
	 * @param score sets the credit score of the Credit account
	 */
	public void setScore(int score){
		this.score = score;
	}

	/**
	 *
	 * @return returns the limit of the Credit balance
	 */
	public int getLimit(){
		return this.limit;
	}

	/**
	 *
	 * @return returns the credit score
	 */
	public int getScore(){
		return this.score;
	}
}