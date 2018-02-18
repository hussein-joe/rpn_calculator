package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;

import java.util.function.Predicate;

abstract class AbstractOperatorHandler implements OperatorHandler {

    protected final DigitProcessor digitProcessor;
    protected final Predicate<String> operatorHandlerPredicate;

    protected AbstractOperatorHandler(DigitProcessor digitProcessor, Predicate<String> operatorHandlerPredicate) {
        this.digitProcessor = digitProcessor;
        this.operatorHandlerPredicate = operatorHandlerPredicate;
    }

    @Override
    public void handle(String operator, CalculatorSession session) {
        if ( !canHandle(operator) ) {
            return;
        }
        validateParameters(operator, session);

        double result = doHandle(operator, session);
        session.addDigit(result);
    }

    protected abstract int getNumberOfParameters();
    protected abstract double doHandle(String operator, CalculatorSession session);

    double fetchParameter(CalculatorSession session) {
        return digitProcessor.toDigit( session.popDigit() );
    }

    private void validateParameters(String operator, CalculatorSession session) {
        if ( session.countDigits() < getNumberOfParameters() ) {
            throw new OperatorInsufficientParametersException(()->operator);
        }
    }

    private boolean canHandle(String operator) {
        return operatorHandlerPredicate.test(operator.trim());
    }
}
