package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;

import java.util.function.BiFunction;

public class TwoParameterBasedOperatorHandler extends AbstractOperatorHandler {

    private final BiFunction<Double, Double, Double> function;

    public TwoParameterBasedOperatorHandler(BiFunction<Double, Double, Double> function,
                                            DigitProcessor digitProcessor) {
        super(digitProcessor);
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

        return function.apply(parameter2, parameter1);
    }
}
