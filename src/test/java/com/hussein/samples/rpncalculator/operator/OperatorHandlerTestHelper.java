package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

class OperatorHandlerTestHelper {
    static void initializeDigitProcessorFor(DigitProcessor digitProcessor, String parameter1,
                                            String... remainingParameters) {
        when(digitProcessor.toDigit(parameter1)).thenReturn(Double.valueOf(parameter1));
        if ( remainingParameters == null )
            return;

        for (String parameter: remainingParameters) {
            when(digitProcessor.toDigit(parameter)).thenReturn(Double.valueOf(parameter));
        }
    }

    static void initializeSessionFor(CalculatorSession session, String... parameters) {
        Stream.of(parameters).forEach(s-> when(session.popDigit()).thenReturn(s));
        when(session.countDigits()).thenReturn(parameters.length);
    }
}
