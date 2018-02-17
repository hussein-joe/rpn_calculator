package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;

import java.util.function.Function;
import java.util.function.Predicate;

public class OneParameterBasedOperatorHandler implements OperatorHandler {
    private final Predicate<String> operatorHandler;
    private final Function<Double, Double> function;
    private final DigitProcessor digitProcessor;

    public OneParameterBasedOperatorHandler(Predicate<String> operatorHandler, Function<Double, Double> function, DigitProcessor digitProcessor) {
        this.operatorHandler = operatorHandler;
        this.function = function;
        this.digitProcessor = digitProcessor;
    }

    @Override
    public void handle(String operator, CalculatorSession session) {
        if ( !canHandle(operator) ) {
            return;
        }
        validateParameters(operator, session);
        double parameter = fetchParameter(session);

        double result = function.apply(parameter);

        session.addDigit(result);
    }

    private double fetchParameter(CalculatorSession session) {
        return digitProcessor.toDigit( session.popDigit() );
    }

    private void validateParameters(String operator, CalculatorSession session) {
        if ( session.countDigits() < 1 ) {
            throw new OperatorInsufficientParametersException();
        }
    }

    private boolean canHandle(String operator) {
        return operatorHandler.test(operator.trim());
    }
}
