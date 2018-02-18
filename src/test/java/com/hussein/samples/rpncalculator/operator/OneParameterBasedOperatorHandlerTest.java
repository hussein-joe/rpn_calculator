package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static com.hussein.samples.rpncalculator.operator.OperatorHandlerTestHelper.initializeSessionFor;
import static java.lang.Double.valueOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class OneParameterBasedOperatorHandlerTest {

    private static final String OPERATOR = "sqrt";

    @Mock
    private Function<Double, Double> operatorFunction;
    @Mock
    private DigitProcessor digitProcessor;
    @Mock
    private CalculatorSession session;

    private OneParameterBasedOperatorHandler handler;

    @Before
    public void setUp() {
        initMocks(this);

        handler = new OneParameterBasedOperatorHandler(operatorFunction,
                digitProcessor);
    }

    @Test
    public void shouldThrowExceptionWhenNumberOfDigitsInSessionIsZero() {
        when(session.countDigits()).thenReturn(0);

        assertThatThrownBy(() ->  handler.handle(OPERATOR, session))
                .isInstanceOf(OperatorInsufficientParametersException.class);
    }

    @Test
    public void shouldAddResultOfOperatorFunctionToCalculatorSession() {
        Double parameter = Double.valueOf(1);
        Double operatorFunctionResult = valueOf(3);
        initializeSessionFor(session, parameter);
        when(operatorFunction.apply(anyDouble())).thenReturn(operatorFunctionResult);

        handler.handle(OPERATOR, session);

        verify(session).addNumber(operatorFunctionResult);
    }
}