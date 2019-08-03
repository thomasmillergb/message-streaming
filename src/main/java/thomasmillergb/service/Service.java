package thomasmillergb.service;

import thomasmillergb.model.Transaction;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface Service {
    CompletionStage<Optional<String>> processTransaction(Transaction tx);
}
