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

import static com.hussein.samples.rpncalculator.operator.OperatorHandlerTestHelper.initializeSessionFor;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TwoParameterBasedOperatorHandlerTest {

    private static final String OPERATOR = "/";

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

        handler = new TwoParameterBasedOperatorHandler(operatorFunction, digitProcessor);
    }

    @Test
    public void shouldAddResultOfOperatorFunctionToCalculatorSession() {
        Double param1 = Double.valueOf(12);
        Double param2 = Double.valueOf(2);
        Double operatorFunctionResult = 3d;
        initializeSessionFor(session, param1, param2);
        when(operatorFunction.apply(param2, param1)).thenReturn(operatorFunctionResult);

        handler.handle(OPERATOR, session);

        verify(session).addNumber(operatorFunctionResult);
    }

    @Test
    public void shouldThrowExceptionWhenDigitsInSessionNotEnoughForOperator() {
        when(session.countDigits()).thenReturn(1);

        assertThatThrownBy(() ->  handler.handle(OPERATOR, session))
                .isInstanceOf(OperatorInsufficientParametersException.class);
    }
}