package thomasmillergb.service;

import io.reactivex.Observable;
import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResultRx;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface Service {
    CompletionStage<Optional<String>> processTransaction(Transaction tx);

    Observable<TransactionResultRx> processTransactionRx(Transaction tx);
}
