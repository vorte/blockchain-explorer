package com.petrov.dimitar.blockchainexplorer;

import info.blockchain.api.APIException;
import info.blockchain.api.blockexplorer.BlockExplorer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BlockExplorer blockExplorer;
    private TransactionService transactionService;
    private UnspentOutputs unspentOutputs;
    private String address;

    @Before
    public void setUp() throws Exception {
        address = "address";

        blockExplorer = mock(BlockExplorer.class);
        transactionService = new TransactionService(blockExplorer);

        String hash1 = "hash1";
        String hash2 = "hash2";
        long value1 = 10020;
        long value2 = 45;
        int n1 = 0;
        int n2 = 1;

        List<info.blockchain.api.blockexplorer.UnspentOutput> apiOutputs = Arrays.asList(
                new info.blockchain.api.blockexplorer.UnspentOutput(n1, hash1, 0, null, value1, 0),
                new info.blockchain.api.blockexplorer.UnspentOutput(n2, hash2, 0, null, value2, 0));
        when(blockExplorer.getUnspentOutputs(address)).thenReturn(apiOutputs);

        unspentOutputs = new UnspentOutputs(Arrays.asList(
                new UnspentOutput(n1, hash1, value1), new UnspentOutput(n2, hash2, value2)));
    }

    @Test
    public void getUnspentTransactions_success_returnsTransactions() throws Exception {
        UnspentOutputs result = transactionService.getUnspentOutputs(address);

        assertThat(result, equalTo(unspentOutputs));
    }

    @Test
    public void getUnspentTransactions_success_noTransactions() throws Exception {
        when(blockExplorer.getUnspentOutputs(address)).thenReturn(new ArrayList<>());

        UnspentOutputs result = transactionService.getUnspentOutputs(address);

        assertThat(result, equalTo(new UnspentOutputs(new ArrayList<>())));
    }

    @Test
    public void getUnspentTransactions_ioException_exceptionThrown() throws Exception {
        doThrow(new IOException()).when(blockExplorer).getUnspentOutputs(address);

        expectedException.expect(IOException.class);

        transactionService.getUnspentOutputs(address);
    }

    @Test
    public void getUnspentTransactions_apiException_wrappedInLocalException() throws Exception {
        APIException cause = new APIException("error");
        doThrow(cause).when(blockExplorer).getUnspentOutputs(address);

        expectedException.expect(BlockchainAPIException.class);
        expectedException.expectCause(equalTo(cause));

        transactionService.getUnspentOutputs(address);
    }

}