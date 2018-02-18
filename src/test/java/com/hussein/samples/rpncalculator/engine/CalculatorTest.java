package com.hussein.samples.rpncalculator.engine;

import com.hussein.samples.rpncalculator.exceptions.NotSupportedOperatorException;
import com.hussein.samples.rpncalculator.operator.OperatorHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {
    @Mock
    private CalculatorSession session;
    @Mock
    private DigitProcessor digitProcessor;

    @Mock
    private OperatorHandlerFactory operatorHandlerFactory;
    @Mock
    private OperatorHandler operatorHandler;

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        calculator = new Calculator(digitProcessor, operatorHandlerFactory);
    }

    @Test
    public void shouldPrintStackWhenEmptyStringPassed() {
        CalculatorResult actualResult = calculator.evaluate("", session);

        assertThat(actualResult.getCalculationResult()).isEmpty();
    }

    @Test
    public void shouldPrintTheSameNumbersInTheSameOrderWhenNoOperatorPassed() {
        when(session.getNumberStack()).thenReturn(newArrayList(1d, 2d, 3d));
        givenDigitalProcessorInitialized("1", "2", "3");

        CalculatorResult actualResult = calculator.evaluate("1 2 3", session);

        verify(session, times(3)).addDigit(anyDouble());
        assertThat(actualResult.getCalculationResult()).isEqualTo("1 2 3");
    }

    @Test
    public void shouldCallOperatorHandlerWhenPassOperator() {
        givenDigitalProcessorInitialized("1", "2");
        String operator = "op";
        when(operatorHandlerFactory.handlerFor(operator)).thenReturn(Optional.of(operatorHandler));

        CalculatorResult actualResult = calculator.evaluate("1 2 " + operator, session);

        verify(operatorHandler).handle(operator, session);
    }

    @Test
    public void shouldThrowExceptionWhenOperatorIsUnknown() {
        String operator = "$$$";

        when(operatorHandlerFactory.handlerFor(operator)).thenReturn(Optional.empty());

        CalculatorResult actualResult = calculator.evaluate(operator, session);
        assertThat(actualResult.getCalculationError().get())
                .isInstanceOf(NotSupportedOperatorException.class);
    }

    private void givenDigitalProcessorInitialized(String... values) {
        Stream.of(values).forEach(t->{
            Double value = Double.valueOf(t);
            when(digitProcessor.toDigit(t)).thenReturn(value);
            when(digitProcessor.isDigit(t)).thenReturn(true);
            when(digitProcessor.formatNumber(value)).thenReturn(Long.valueOf(t).toString());
        });
    }
}