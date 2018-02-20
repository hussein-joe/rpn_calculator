package com.hussein.samples.rpncalculator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.service.CalculatorInputStream;
import org.mockito.stubbing.OngoingStubbing;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

public class CalculatorTestHelper {
    public static void initializeSessionFor(CalculatorSession session, Double numberInSession, Double... moreNumbers) {
        OngoingStubbing<Double> calculatorSessionStubbing = when(session.popDigit()).thenReturn(numberInSession);
        for (Double s: moreNumbers) {
            calculatorSessionStubbing = calculatorSessionStubbing.thenReturn(s);
        }
        when(session.countDigits()).thenReturn(moreNumbers.length+1);
    }

    public static void givenInputStreamInitializedWithUserInputs(CalculatorInputStream inputStreamReader,
                                                           String userInput, String... moreUserInputs) {
        OngoingStubbing<String> inputStreamStubbing = when(inputStreamReader.readLine()).thenReturn(userInput);
        for (String s: moreUserInputs) {
            inputStreamStubbing = inputStreamStubbing.thenReturn(s);
        }
        inputStreamStubbing = inputStreamStubbing.thenReturn("exit");
    }
}
