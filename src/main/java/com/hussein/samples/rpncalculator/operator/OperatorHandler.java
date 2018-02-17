package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;

public interface OperatorHandler {
    void handle(String operator, CalculatorSession session);
}
