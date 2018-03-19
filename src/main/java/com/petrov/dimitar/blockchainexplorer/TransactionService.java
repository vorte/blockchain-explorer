package com.petrov.dimitar.blockchainexplorer;

import info.blockchain.api.APIException;
import info.blockchain.api.blockexplorer.BlockExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private BlockExplorer blockExplorer;

    @Autowired
    public TransactionService(BlockExplorer blockExplorer) {
        this.blockExplorer = blockExplorer;
    }

    public UnspentOutputs getUnspentOutputs(String address) throws IOException, BlockchainAPIException {
        List<info.blockchain.api.blockexplorer.UnspentOutput> unspentOutputs;
        try {
            unspentOutputs = blockExplorer.getUnspentOutputs(address);
        } catch (APIException ex) {
            throw new BlockchainAPIException(ex);
        }

        List<UnspentOutput> transformedOutputs = unspentOutputs.stream()
                .map(e -> new UnspentOutput(e.getN(), e.getTransactionHash(), e.getValue()))
                .collect(Collectors.toList());

        return new UnspentOutputs(transformedOutputs);
    }

}
