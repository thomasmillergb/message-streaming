package thomasmillergb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thomasmillergb.model.Transaction;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class StreamingServiceIntagrationTest {

    private Database database;

    private Service underTest;

    @BeforeEach
    void setup() {
        database = new DatabaseImpl();
        underTest = new StreamingService(database);
    }


    @Test
    void shouldCompleteSuccessfulySinlgeThread(){

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

        // Will only run on one thread due to be only called one at a time
        var resultsSingleThread = streamOfTransationToProccess.stream()
                .map(transaction -> underTest.processTransaction(transaction).toCompletableFuture())
                .allMatch(f -> {
                    Optional<String> result = f.join();
                    return result.isEmpty() && f.isDone() && !f.isCompletedExceptionally();
                });


        assertTrue(resultsSingleThread);

    }
    @Test
    void shouldCompleteSuccessfulyWithErrorsSinlgeThread(){

        var streamOfTransationToProccess = asList(
                new Transaction(1),
                new Transaction(1),
                new Transaction(2),
                new Transaction(4),
                new Transaction(5),
                new Transaction(0),
                new Transaction(2),
                new Transaction(1),
                new Transaction(5),
                new Transaction(1),
                new Transaction(6)
        );

        // Will only run on one thread due to be only called one at a time
        var results = streamOfTransationToProccess.stream()
                .map(transaction -> underTest.processTransaction(transaction).toCompletableFuture())
                .filter(f -> {
                    f.join();
                    if (f.isCompletedExceptionally()) fail();
                    return f.join().isPresent();
                });


        assertEquals(1, results.count());

    }
    @Test
    void shouldCompleteSuccessfulyMultipleThread(){

        var streamOfTransationToProccess = asList(
                new Transaction(1),
                new Transaction(1),
                new Transaction(2),
                new Transaction(1),
                new Transaction(5),
                new Transaction(1),
                new Transaction(2),
                new Transaction(1),
                new Transaction(5),
                new Transaction(1),
                new Transaction(6)
        );

        //Will run in mutiple threads
        var resultsMutiple = CompletableFuture.allOf(streamOfTransationToProccess.stream()
                .map(transaction -> underTest.processTransaction(transaction).toCompletableFuture())
                .map(r -> r.thenApply(result -> result.isEmpty() ? result : fail()))
                .toArray(CompletableFuture[]::new));
        resultsMutiple.join();
        assertFalse(resultsMutiple.isCompletedExceptionally());

    }

    @Test
    void shouldCompleteExceptionallyMultipleThread(){

        var streamOfTransationToProccess = asList(
                new Transaction(1),
                new Transaction(1),
                new Transaction(2),
                new Transaction(1),
                new Transaction(5),
                new Transaction(-1),
                new Transaction(1),
                new Transaction(5),
                new Transaction(1),
                new Transaction(6)
        );

        //Will run in mutiple threads
        var resultsMutiple = CompletableFuture.allOf(streamOfTransationToProccess.stream()
                .map(transaction -> underTest.processTransaction(transaction).toCompletableFuture())
                .map(r -> r.thenApply(result -> result.isEmpty() ? result : fail()))
                .toArray(CompletableFuture[]::new));
        assertThrows(CompletionException.class, resultsMutiple::join);
        assertTrue(resultsMutiple.isCompletedExceptionally());

    }

}