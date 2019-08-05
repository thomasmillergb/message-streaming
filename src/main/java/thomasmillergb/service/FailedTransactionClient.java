package thomasmillergb.service;

import thomasmillergb.model.Transaction;

public class FailedTransactionClient {
    public void addFailedTransation(Transaction transaction){
        System.out.println("transation failed " + transaction);
    }
}
