import java.util.ArrayList;

/**
 * Name: Jaehyeon Park
 * Date:
 */
public class AccountFactory {
    private AccountFactory factory;
    private AccountFactory(){

    }
    public AccountFactory getInstance(){
        if(factory == null){
            factory = new AccountFactory();
        }
        return factory;
    }

    public Account generateAccount(String [] args) throws Exception {
        if (args.length == 0) {
            throw new FactoryException("Nothing has been entered");
        } else if (args[0].equalsIgnoreCase("Checking")) {
            Account ac = new Checking();
            return ac;
        } else if (args[0].equalsIgnoreCase("Saving")) {
            Account ac = new Savings();
            return ac;
        } else if (args[0].equalsIgnoreCase("Credit")) {
            Account ac = new Credit();
            return ac;
        } else {
            throw new FactoryException("Error:");
        }
    }
}
