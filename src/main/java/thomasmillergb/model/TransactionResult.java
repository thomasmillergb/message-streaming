package thomasmillergb.model;

import java.util.Optional;

public class TransactionResult {

    private final String errorMessage;

    public TransactionResult() {
        errorMessage = null;
    }

    public TransactionResult(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }
}
