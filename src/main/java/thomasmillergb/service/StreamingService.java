package thomasmillergb.service;

import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;

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

}
