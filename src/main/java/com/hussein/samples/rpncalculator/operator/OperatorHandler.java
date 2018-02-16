package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;

public interface OperatorHandler {
    void handle(String token, CalculatorSession session);
}
