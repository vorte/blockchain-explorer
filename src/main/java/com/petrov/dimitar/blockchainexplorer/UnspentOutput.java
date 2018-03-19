package com.petrov.dimitar.blockchainexplorer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UnspentOutput {

    @JsonProperty("outputidx")
    private int outputId;
    @JsonProperty("txhash")
    private String transactionHash;
    private long value;

    public UnspentOutput() {}

    public UnspentOutput(int outputId, String transactionHash, long value) {
        this.outputId = outputId;
        this.transactionHash = transactionHash;
        this.value = value;
    }

    public int getOutputId() {
        return outputId;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnspentOutput that = (UnspentOutput) o;
        return outputId == that.outputId &&
                value == that.value &&
                Objects.equals(transactionHash, that.transactionHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputId, transactionHash, value);
    }
}
