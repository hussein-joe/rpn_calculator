package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;

import java.util.function.Function;

public class OneParameterBasedOperatorHandler extends AbstractOperatorHandler {
    private final Function<Double, Double> function;

    public OneParameterBasedOperatorHandler(Function<Double, Double> function,
                                            DigitProcessor digitProcessor) {
        super(digitProcessor);
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
