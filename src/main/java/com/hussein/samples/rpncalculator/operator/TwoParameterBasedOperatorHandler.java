package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import static java.lang.String.format;

public class TwoParameterBasedOperatorHandler implements OperatorHandler {

    private final Predicate<String> operatorHandler;
    private final BiFunction<Double, Double, Double> function;
    private final DigitProcessor digitProcessor;

    public TwoParameterBasedOperatorHandler(Predicate<String> operatorHandler,
                                            BiFunction<Double, Double, Double> function,
                                            DigitProcessor digitProcessor) {
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

        double parameter1 = fetchParameter(session);
        double parameter2 = fetchParameter(session);

        double result = function.apply(parameter1, parameter2);

        session.addDigit(result);
    }

    private double fetchParameter(CalculatorSession session) {
        return digitProcessor.toDigit( session.popDigit() );
    }

    private void validateParameters(String operator, CalculatorSession session) {
        if ( session.countDigits() < 2 ) {
            throw new OperatorInsufficientParametersException();
        }
    }

    private boolean canHandle(String operator) {
        return operatorHandler.test(operator.trim());
    }
}
