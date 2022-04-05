import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsTest {
    Transactions transactionHandler = Transactions.getInstance();
    @Test
    void userDeposit1() {
        boolean didNotThrow = true;
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        cust.getCheck().setBalance(100);
        //user enters a correct value
        try {
            transactionHandler.userDeposit(cust, "Checking", 30);
        }catch (TransactionException e){
                didNotThrow = false;
        }
        Assertions.assertTrue(didNotThrow);
    }
    @Test
    void userDeposit2() {
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        //user enters a bad value
        //Assertions.assertFalse(transactionHandler.userDeposit(cust,"Savings",-100));
    }

    @Test
    void accountTransfer1() {
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        cust.getCheck().setBalance(100);
        cust.setSave(new Savings());
        cust.getSave().setBalance(30);
        //a successful transaction happens
        //Assertions.assertTrue(transactionHandler.accountTransfer(cust,"Checking","Savings",50));
    }
    @Test
    void accountTransfer2() {
        Customer cust = new Customer();
        cust.setCheck(new Checking());
        cust.getCheck().setBalance(100);
        cust.setSave(new Savings());
        cust.getSave().setBalance(30);
        //transaction fails do to lack of funds
        //Assertions.assertFalse(transactionHandler.accountTransfer(cust,"Checking","Savings",1000));
    }

    @Test
    void transactionToOther1() {
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
        //Assertions.assertTrue(transactionHandler.transactionToOther(cust1,cust2,"Checking","Checking",50));
    }
    @Test
    void transactionToOther2() {
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
        //Assertions.assertFalse(transactionHandler.transactionToOther(cust2,cust1,"Checking","Checking",50));
    }
}