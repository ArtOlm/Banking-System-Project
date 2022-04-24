import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class TransactionsTest {
    Transactions transactionHandler = Transactions.getInstance();
    @Test
    void userDeposit1() {
        boolean testResult = true;
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        cust.getCheck().setBalance(100);
        //user enters a correct value
        try {
            transactionHandler.userDeposit(cust, "Checking", 30);
        }catch (TransactionException e){
                testResult = false;
        }
        Assertions.assertTrue(testResult);
    }
    @Test
    void userDeposit2() {
        boolean testResult = true;
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        //user enters a bad value
        try {
            transactionHandler.userDeposit(cust,"Savings",-100);
        }catch (TransactionException e){
            testResult = false;
        }
        Assertions.assertFalse(testResult);
    }

    @Test
    void accountTransfer1() {
        boolean testResult = true;
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        cust.getCheck().setBalance(100);
        cust.setSave(new Savings());
        cust.getSave().setBalance(30);
        //a successful transaction happens
        try {
            transactionHandler.accountTransfer(cust, "Checking", "Savings", 50.00);
        }catch (TransactionException e){
            testResult = false;
        }
        Assertions.assertTrue(testResult);
    }
    @Test
    void accountTransfer2() {
        boolean testResult = true;
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        cust.getCheck().setBalance(100);
        cust.setSave(new Savings());
        cust.getSave().setBalance(30);
        //transaction fails do to lack of funds
        try {
            transactionHandler.accountTransfer(cust, "Checking", "Savings", 1000000);
        } catch (TransactionException e){
            testResult = false;
        }
        Assertions.assertFalse(testResult);
    }

    @Test
    void transactionToOther1() {
        boolean testResult = true;
        Customer cust1 = new Customer();
        cust1.setCheck(new Checking());
        cust1.getCheck().setBalance(100);
        cust1.setSave(new Savings());
        cust1.getSave().setBalance(30);

        Customer cust2 = new Customer();
        cust2.setCheck(new Checking());
        cust2.getCheck().setBalance(1);
        cust2.setSave(new Savings());
        cust2.getSave().setBalance(10000);
        cust1.setID(1);
        cust2.setID(2);
        //transaction is successful
        try {
            transactionHandler.transactionToOther(cust1, cust2, "Checking", "Savings", 100);
        } catch (TransactionException e) {
            testResult = false;
        }
        Assertions.assertTrue(testResult);
    }
    @Test
    void transactionToOther2() {
        boolean testResult = true;
        Customer cust1 = new Customer();
        cust1.setCheck(new Checking());
        cust1.getCheck().setBalance(100);
        cust1.setSave(new Savings());
        cust1.getSave().setBalance(30);

        Customer cust2 = new Customer();
        cust2.setCheck(new Checking());
        cust2.getCheck().setBalance(1);
        cust2.setSave(new Savings());
        cust2.getSave().setBalance(10000);
        //the transaction fails due to insufficient funds
        try {
            transactionHandler.transactionToOther(cust1, cust2, "checking", "Savings", 1000000);
        } catch (TransactionException e) {
            testResult = false;
        }
        Assertions.assertFalse(testResult);
    }
    @Test
    void buyItem1(){
       Customer cus = new Customer();
       Checking c =  new Checking();
       c.setBalance(100000);
       Item apple = new Item();
       apple.setPrice(100000);
       cus.setCheck(c);
       boolean didBuy = true;
       try{
           Transactions.getInstance().buyFromMinerMall(cus,"Checking", apple.getPrice());
       }catch (TransactionException e){
           didBuy = false;
       }
       Assertions.assertTrue(didBuy);
    }
    @Test
    void buyItem2(){
        Customer cus = new Customer();
        Checking c =  new Checking();
        c.setBalance(1);
        Item apple = new Item();
        apple.setPrice(100000);
        cus.setCheck(c);
        boolean didNotBuy = false;
        try{
            Transactions.getInstance().buyFromMinerMall(cus,"Checking", apple.getPrice());
        }catch (TransactionException e){
            didNotBuy = true;
        }
        Assertions.assertTrue(didNotBuy);
    }
    @Test
    void withdraw1(){
        Customer cus = new Customer();
        Checking c = new Checking();
        c.setBalance(100);
        cus.setCheck(c);
        boolean didNotWithraw = false;
        try {
            Transactions.getInstance().userWithdraw(cus, "Checking", -111);
        }catch (TransactionException e){
            didNotWithraw = true;
        }
        Assertions.assertTrue(didNotWithraw);
    }
    @Test
    void withdraw2(){
        Customer cus = new Customer();
        Checking c = new Checking();
        c.setBalance(100);
        cus.setCheck(c);
        boolean didWithraw = true;
        try {
            Transactions.getInstance().userWithdraw(cus, "Checking", 100);
        }catch (TransactionException e){
            didWithraw = false;
        }
        Assertions.assertTrue(didWithraw);
    }

}