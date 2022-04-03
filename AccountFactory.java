import java.util.ArrayList;

/**
 * @Author Jaehyeon Park
 */
public class AccountFactory {
    private AccountFactory factory;
    private AccountFactory(){

    }

    /**
     *
     * @return AccountFactory
     */
    public AccountFactory getInstance(){
        if(factory == null){
            factory = new AccountFactory();
        }
        return factory;
    }

    /**
     *
     * @param args
     * @return Account
     * @throws FactoryException
     */
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
