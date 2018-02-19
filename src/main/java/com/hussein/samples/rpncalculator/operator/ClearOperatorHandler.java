package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;

public class ClearOperatorHandler implements OperatorHandler {
    @Override
    public void handle(String operator, CalculatorSession session) {
        session.clearNumbers();
        session.clearAppliedInstruction();
    }
}
