package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;

import java.util.function.Function;
import java.util.function.Predicate;

public class OneParameterBasedOperatorHandler extends AbstractOperatorHandler {
    private final Function<Double, Double> function;

    public OneParameterBasedOperatorHandler(Predicate<String> operatorHandlerPredicate,
                                            Function<Double, Double> function,
                                            DigitProcessor digitProcessor) {
        super(digitProcessor, operatorHandlerPredicate);
        this.function = function;
    }

    @Override
    protected int getNumberOfParameters() {
        return 1;
    }

    @Override
    protected double doHandle(String operator, CalculatorSession session) {
        double parameter = fetchParameter(session);

        return function.apply(parameter);
    }
}
