public class TransactionException extends Exception{
    private String message;
    public TransactionException(String exMessage){
        super(exMessage);
    }
}
