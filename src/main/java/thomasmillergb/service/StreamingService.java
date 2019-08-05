package thomasmillergb.service;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;
import thomasmillergb.model.TransactionResultRx;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StreamingService implements Service {


    private final Database database;

    private final Executor executor;

    public StreamingService(final Database database) {
        this.database = database;
        //Set to 200 as the db is IO bound
        executor = Executors.newFixedThreadPool(200);
    }

    @Override
    public CompletionStage<Optional<String>> processTransaction(final Transaction tx) {
        return CompletableFuture.supplyAsync(() -> database.saveTransaction(tx), executor)
                .thenApply(TransactionResult::getErrorMessage);

    }

    @Override
    public Observable<TransactionResultRx> processTransactionRx(final Transaction tx) {
        return Observable.just(tx)
                //So much nicer
                .observeOn(Schedulers.io())
                .map(database::saveTransactionRx);
    }

}
