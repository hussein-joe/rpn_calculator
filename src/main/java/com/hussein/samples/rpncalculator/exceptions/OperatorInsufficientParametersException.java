package com.hussein.samples.rpncalculator.exceptions;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Exception thrown when unkown operator is passed.
 * I used Supplier here to avoid having a constructor with String(operator) which will conflict with the
 * default RuntimeException constructor which takes message as a parameter.
 */
public class OperatorInsufficientParametersException extends RuntimeException {
    private final Supplier<String> operatorSupplier;

    public OperatorInsufficientParametersException(Supplier<String> operatorSupplier) {
        Objects.requireNonNull(operatorSupplier);
        this.operatorSupplier = operatorSupplier;
    }

    @Override
    public String getMessage() {
        return String.format("Operator %s: insufficient parameters", operatorSupplier.get());
    }
}
