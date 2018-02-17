package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import static java.lang.String.format;

public class TwoParameterBasedOperatorHandler extends AbstractOperatorHandler {

    private final BiFunction<Double, Double, Double> function;

    public TwoParameterBasedOperatorHandler(Predicate<String> operatorHandlerPredicate,
                                            BiFunction<Double, Double, Double> function,
                                            DigitProcessor digitProcessor) {
        super(digitProcessor, operatorHandlerPredicate);
        this.function = function;
    }

    @Override
    protected int getNumberOfParameters() {
        return 2;
    }

    @Override
    protected double doHandle(String operator, CalculatorSession session) {
        double parameter1 = fetchParameter(session);
        double parameter2 = fetchParameter(session);

        return function.apply(parameter1, parameter2);
    }
}
