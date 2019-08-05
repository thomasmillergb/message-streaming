package thomasmillergb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import thomasmillergb.model.Transaction;
import thomasmillergb.model.TransactionResult;
import thomasmillergb.model.TransactionResultRx;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StreamingServiceRxTest {

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
        when(database.saveTransactionRx(transaction)).thenReturn(new TransactionResultRx());
        var result = underTest.processTransactionRx(transaction);
        assertEquals(new TransactionResultRx(), result.blockingSingle());

    }

    @Test
    void shouldCompleteFutureWithErrorMessage() throws ExecutionException, InterruptedException {
        String errorMessage = "There was a error";
        when(database.saveTransactionRx(transaction)).thenReturn(new TransactionResultRx(errorMessage));
        var result = underTest.processTransactionRx(transaction);
        assertEquals(new TransactionResultRx(errorMessage), result.blockingSingle());
    }

    @Test
    void shouldCompleteExceptionally(){
        when(database.saveTransactionRx(transaction)).thenReturn(new TransactionResultRx(new RuntimeException()));
        var result = underTest.processTransactionRx(transaction);
        assertTrue(result.blockingSingle().getException().isPresent());
    }
}