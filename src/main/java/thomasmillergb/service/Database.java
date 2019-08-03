package thomasmillergb.service;

import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;
import thomasmillergb.model.TransactionResultRx;

public interface Database {
    TransactionResult saveTransaction(final Transaction transaction);
    TransactionResultRx saveTransactionRx(final Transaction transaction);
}
