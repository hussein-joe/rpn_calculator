package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TwoParameterBasedOperatorHandlerTest {

    private static final String OPERATOR = "+";

    @Mock
    private Predicate<String> canHandlerPredicate;
    @Mock
    private BiFunction<Double, Double, Double> operatorFunction;
    @Mock
    private DigitProcessor digitProcessor;
    @Mock
    private CalculatorSession session;

    private TwoParameterBasedOperatorHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        handler = new TwoParameterBasedOperatorHandler(canHandlerPredicate, operatorFunction, digitProcessor);
        when(canHandlerPredicate.test(OPERATOR)).thenReturn(true);
    }

    @Test
    public void shouldNotCallOperatorFunctionWhenHandlerCanNotHandleOperator() {
        when(canHandlerPredicate.test(OPERATOR)).thenReturn(false);

        handler.handle("+", session);

        verify(operatorFunction, never()).apply(anyDouble(), anyDouble());
    }

    @Test
    public void shouldAddResultOfOperatorFunctionToCalculatorSession() {
        String param2 = "2";
        String param1 = "1";
        Double operatorFunctionResult = 3d;
        initializeDigitProcessorFor(param2, param1, operatorFunctionResult);
        initializeSessionFor(param2, param1);

        handler.handle(OPERATOR, session);

        verify(session).addDigit(operatorFunctionResult);
    }

    @Test
    public void shouldThrowExceptionWhenDigitsInSessionNotEnoughForOperator() {
        when(session.countDigits()).thenReturn(1);

        assertThatThrownBy(() ->  handler.handle(OPERATOR, session))
                .isInstanceOf(OperatorInsufficientParametersException.class);
    }

    private void initializeDigitProcessorFor(String param2, String param1, double result) {
        Double param1Digittized = Double.valueOf(param1);
        Double param2Digittized = Double.valueOf(param2);
        when(digitProcessor.toDigit(param1)).thenReturn(param1Digittized);
        when(digitProcessor.toDigit(param2)).thenReturn(param2Digittized);
        when(operatorFunction.apply(param1Digittized, param2Digittized)).thenReturn(result);
    }

    private void initializeSessionFor(String param2, String param1) {
        when(session.popDigit()).thenReturn(param1).thenReturn(param2);
        when(session.countDigits()).thenReturn(2);
    }
}