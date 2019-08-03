package thomasmillergb.service;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;
import thomasmillergb.model.TransactionResultRx;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class StreamingService implements Service {


    private final Database database;


    public StreamingService(final Database database) {
        this.database = database;
    }

    @Override
    public CompletionStage<Optional<String>> processTransaction(final Transaction tx) {
        return CompletableFuture.supplyAsync(() -> database.saveTransaction(tx))
                .thenApply(TransactionResult::getErrorMessage);

    }

    @Override
    public Observable<TransactionResultRx> processTransactionRx(final Transaction tx) {
        return Observable.just(tx)
                .observeOn(Schedulers.io())
                .map(database::saveTransactionRx);
    }

}
