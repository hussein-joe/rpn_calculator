package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import org.mockito.stubbing.OngoingStubbing;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

class OperatorHandlerTestHelper {
    static void initializeSessionFor(CalculatorSession session, Double numberInSession, Double... moreNumbers) {
        OngoingStubbing<Double> calculatorSessionStubbing = when(session.popDigit()).thenReturn(numberInSession);
        for (Double s: moreNumbers) {
            calculatorSessionStubbing = calculatorSessionStubbing.thenReturn(s);
        }
        when(session.countDigits()).thenReturn(moreNumbers.length+1);
    }
}
