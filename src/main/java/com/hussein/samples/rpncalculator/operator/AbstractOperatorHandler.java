package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.AppliedInstruction;
import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;

abstract class AbstractOperatorHandler implements OperatorHandler {

    protected final DigitProcessor digitProcessor;

    protected AbstractOperatorHandler(DigitProcessor digitProcessor) {
        this.digitProcessor = digitProcessor;
    }

    @Override
    public void handle(String operator, CalculatorSession session) {
        validateParameters(operator, session);

        AppliedInstruction appliedInstruction = doHandle(operator, session);
        session.addNumber(appliedInstruction.getResult());
        session.addAppliedInstruction(appliedInstruction);
    }

    protected abstract int getNumberOfParameters();
    protected abstract AppliedInstruction doHandle(String operator, CalculatorSession session);

    double fetchParameter(CalculatorSession session) {
        return session.popDigit();
    }

    private void validateParameters(String operator, CalculatorSession session) {
        if ( session.countDigits() < getNumberOfParameters() ) {
            throw new OperatorInsufficientParametersException(()->operator);
        }
    }
}
