package com.hussein.samples.rpncalculator.engine;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class AppliedInstruction {
    private final String operator;
    private final Double[] parameters;
    private final Double result;

    public AppliedInstruction(String operator, Double result, Double... parameters) {
        requireNonNull(operator);
        requireNonNull(result);
        assert ArrayUtils.isNotEmpty(parameters);

        this.operator = operator;
        this.parameters = Arrays.copyOf(parameters, parameters.length);
        this.result = result;
    }

    public String getOperator() {
        return operator;
    }

    public Double[] getParameters() {
        return Arrays.copyOf(parameters, parameters.length);
    }

    public Double getResult() {
        return result;
    }
}
