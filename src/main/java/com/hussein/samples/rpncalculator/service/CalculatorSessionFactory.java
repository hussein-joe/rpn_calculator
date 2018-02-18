package com.hussein.samples.rpncalculator.service;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;

public class CalculatorSessionFactory {

    public CalculatorSession newSession() {
        return new CalculatorSession();
    }
}
