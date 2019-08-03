package thomasmillergb.service;

import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;

public interface Database {
    TransactionResult saveTransaction(final Transaction transaction);
}
