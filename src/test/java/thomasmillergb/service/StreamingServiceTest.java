package thomasmillergb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StreamingServiceTest {

    @Mock
    private Database database;

    @Mock
    private Transaction transaction;

    private Service underTest;

    @BeforeEach
    void setup() {
        initMocks(this);
        underTest = new StreamingService(database);
    }


    @Test
    void shouldCompleteSuccessfuly() throws ExecutionException, InterruptedException {
        when(database.saveTransaction(transaction)).thenReturn(new TransactionResult());
        var result = underTest.processTransaction(transaction).toCompletableFuture();
        assertEquals(Optional.empty(), result.get());

    }

    @Test
    void shouldCompleteFutureWithErrorMessage() throws ExecutionException, InterruptedException {
        String errorMessage = "There was a error";
        when(database.saveTransaction(transaction)).thenReturn(new TransactionResult(errorMessage));
        var result = underTest.processTransaction(transaction).toCompletableFuture();
        assertEquals(Optional.of("There was a error"), result.get());
    }

    @Test
    void shouldCompleteExceptionally(){
        when(database.saveTransaction(transaction)).thenThrow(new RuntimeException());
        var result = underTest.processTransaction(transaction).toCompletableFuture();
        assertThrows(ExecutionException.class, result::get);
        assertTrue(result.isCompletedExceptionally());
    }
}