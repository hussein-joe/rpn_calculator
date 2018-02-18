package com.hussein.samples.rpncalculator.engine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorSessionTest {

    private CalculatorSession session;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        session = new CalculatorSession();
    }

    @Test
    public void shouldAddDigitToSession() {
        Double numberToAdd = Double.valueOf(1);

        session.addNumber(numberToAdd);

        assertThat(session.countDigits()).isEqualTo(1);
        assertThat(session.getNumbersInStack()).containsOnly(numberToAdd);
    }

    @Test
    public void popShouldRemoveAddedNumberFromStack() {
        Double numberToAdd = Double.valueOf(1);
        session.addNumber(numberToAdd);

        Double actualResult = session.popDigit();

        assertThat(session.countDigits()).isEqualTo(0);
        assertThat(session.getNumbersInStack()).isEmpty();
    }
}