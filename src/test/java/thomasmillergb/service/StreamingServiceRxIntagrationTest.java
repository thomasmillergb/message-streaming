package thomasmillergb.service;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResultRx;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StreamingServiceRxIntagrationTest {

    private Database database;

    private Service underTest;

    @BeforeEach
    void setup() {
        database = new DatabaseImpl();
        underTest = new StreamingService(database);
    }


    @Test
    void shouldCompleteSuccessfuly() throws InterruptedException {
        List<TransactionResultRx> results = new ArrayList<>();
        var streamOfTransationToProccess = asList(
                new Transaction(1),
                new Transaction(1),
                new Transaction(2),
                new Transaction(4),
                new Transaction(5),
                new Transaction(1),
                new Transaction(2),
                new Transaction(1),
                new Transaction(5),
                new Transaction(1),
                new Transaction(6)
        );

        Observable.fromIterable(streamOfTransationToProccess)
                .flatMap(t -> underTest.processTransactionRx(t))
                .blockingSubscribe(results::add);
        assertEquals(streamOfTransationToProccess.size(), results.size());
        assertTrue(results.stream().allMatch(x -> x.getErrorMessage().isEmpty() && x.getException().isEmpty()));


    }

    @Test
    void shouldCompleteExceptionally() {
        List<TransactionResultRx> results = new ArrayList<>();
        var streamOfTransationToProccess = asList(
                new Transaction(1),
                new Transaction(1),
                new Transaction(2),
                new Transaction(4),
                new Transaction(5),
                new Transaction(1),
                new Transaction(2),
                new Transaction(-1),
                new Transaction(5),
                new Transaction(1),
                new Transaction(6)
        );
        Observable.fromIterable(streamOfTransationToProccess)
                .flatMap(t -> underTest.processTransactionRx(t))
                .blockingSubscribe(results::add);
        assertEquals(streamOfTransationToProccess.size(), results.size());
        assertTrue(results.stream().anyMatch(x -> x.getErrorMessage().isPresent() && x.getException().isPresent()));


    }

    @Test
    void shouldCompleteSuccessfulyWithErrors() {

        List<TransactionResultRx> results = new ArrayList<>();
        var streamOfTransationToProccess = asList(
                new Transaction(1),
                new Transaction(1),
                new Transaction(2),
                new Transaction(4),
                new Transaction(5),
                new Transaction(1),
                new Transaction(2),
                new Transaction(0),
                new Transaction(5),
                new Transaction(1),
                new Transaction(6)
        );

        Observable.fromIterable(streamOfTransationToProccess)
                .flatMap(t -> underTest.processTransactionRx(t))
                .blockingSubscribe(results::add);
        assertEquals(streamOfTransationToProccess.size(), results.size());
        assertTrue(results.stream().anyMatch(x -> x.getErrorMessage().isPresent() && x.getException().isEmpty()));


    }

}