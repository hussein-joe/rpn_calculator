package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

class OperatorHandlerTestHelper {
    static void initializeSessionFor(CalculatorSession session, Double... parameters) {
        Stream.of(parameters).forEach(s-> when(session.popDigit()).thenReturn(s));
        when(session.countDigits()).thenReturn(parameters.length);
    }
}
