package com.petrov.dimitar.blockchainexplorer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    private static final Logger LOG = LoggerFactory.getLogger(AddressController.class);

    private TransactionService transactionService;

    @Autowired
    public AddressController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/address/{address}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUnspentTransactions(@PathVariable("address") String address) {
        try {
            UnspentOutputs unspentOutputs = transactionService.getUnspentOutputs(address);
            return ResponseEntity.ok(unspentOutputs);

        } catch (BlockchainAPIException ex) {
            String message = "Error getting unspent outputs for address " + address;
            LOG.error(message, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(message));

        } catch (Exception ex) {
            String message = "Error getting unspent outputs for address " + address;
            LOG.error(message, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(message));

        }
    }
}
