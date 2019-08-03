package thomasmillergb.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Optional;

public class TransactionResultRx {

    private final String errorMessage;
    private final Exception exception;

    public TransactionResultRx() {
        errorMessage = null;
        exception = null;
    }

    public TransactionResultRx(final String errorMessage) {
        this.errorMessage = errorMessage;
        exception = null;
    }
    public TransactionResultRx(final Exception exception) {
        this.errorMessage = exception.getMessage();
        this.exception = exception;
    }

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public Optional<Exception> getException(){
        return Optional.ofNullable(exception);
    }


    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
