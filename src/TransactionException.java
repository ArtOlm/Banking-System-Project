/**
 * @author Arturo Olmos
 * @version 1.0
 * this class helps handle transaction exceptions of the Transactions class
 */
public class TransactionException extends Exception{
    /**
     * Constructor-sets the message for the exception
     * @param exMessage the message of the exception
     */
    public TransactionException(String exMessage){
        super(exMessage);
    }
}
