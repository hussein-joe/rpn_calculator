package com.hussein.samples.rpncalculator.engine;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class CalculatorResult<T extends RuntimeException> {
    private String calculationResult;
    private T calculationError;

    public String getCalculationResult() {
        return calculationResult;
    }

    public Optional<T> getCalculationError() {
        return Optional.ofNullable(calculationError);
    }

    public static class CalculatorResultBuilder<T extends RuntimeException> {
        private String calculationResult;
        private T calculationError;

        public CalculatorResultBuilder<T> withCalculationResult(String calculationResult) {
            requireNonNull(calculationResult, "Result can not be null");
            this.calculationResult = calculationResult;
            return this;
        }

        public CalculatorResultBuilder<T> withCalculationError(T calculationError) {
            requireNonNull(calculationError, "Error can not be null");

            this.calculationError = calculationError;
            return this;
        }

        public CalculatorResult build() {
            CalculatorResult calculatorResult = new CalculatorResult();
            calculatorResult.calculationResult = calculationResult;
            calculatorResult.calculationError = calculationError;

            return calculatorResult;
        }
    }
}
