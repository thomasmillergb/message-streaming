package thomasmillergb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;
import thomasmillergb.model.TransactionResultRx;


public class DatabaseImpl implements Database {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseImpl.class);

    @Override
    public TransactionResult saveTransaction(final Transaction transaction) {
        try {
            Thread.sleep((long) (Math.random() * 100L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(transaction.getAmount() < 0) {
            throw new RuntimeException("Cannot be below 0");
        }
        else if(transaction.getAmount() == 0){
            LOGGER.info("Did not try to save, Amount is zero no action taken\" " + transaction);
            return new TransactionResult("Amount is zero no action taken");
        }
        else{
            LOGGER.info("Saved " + transaction);
            return new TransactionResult();
        }


    }

    @Override
    public TransactionResultRx saveTransactionRx(final Transaction transaction) {
        try {
            Thread.sleep((long) (Math.random() * 100L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(transaction.getAmount() < 0) {
            return new TransactionResultRx(new RuntimeException("Cannot be below 0"));
        }
        else if(transaction.getAmount() == 0){
            LOGGER.info("Did not try to save, Amount is zero no action taken\" " + transaction);
            return new TransactionResultRx("Amount is zero no action taken");
        }
        else{
            LOGGER.info("Saved " + transaction);
            return new TransactionResultRx();
        }
    }
}
