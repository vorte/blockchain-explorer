package com.petrov.dimitar.blockchainexplorer;

import java.util.List;
import java.util.Objects;

public class UnspentOutputs {

    List<UnspentOutput> outputs;

    public UnspentOutputs() {}

    public UnspentOutputs(List<UnspentOutput> outputs) {
        this.outputs = outputs;
    }

    public List<UnspentOutput> getOutputs() {
        return outputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnspentOutputs that = (UnspentOutputs) o;
        return Objects.equals(outputs, that.outputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputs);
    }
}
