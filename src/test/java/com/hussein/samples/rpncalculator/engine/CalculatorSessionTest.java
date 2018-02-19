package com.hussein.samples.rpncalculator.engine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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

    @Test
    public void popShouldReturnNullWhenNumberStackIsEmpty() {
        Double actualResult = session.popDigit();

        assertThat(actualResult).isNull();
    }

    @Test
    public void clearShouldClearAllNumbersInStack() {
        session.addNumber(Double.valueOf(1));
        session.addNumber(Double.valueOf(2));

        session.clearNumbers();

        assertThat(session.countDigits()).isEqualTo(0);
        assertThat(session.getNumbersInStack()).isEmpty();
    }

    @Test
    public void givenStackIsEmptyClearShouldNotThrowException() {
        session.clearNumbers();
        session.clearNumbers();

        assertThat(session.countDigits()).isEqualTo(0);
        assertThat(session.getNumbersInStack()).isEmpty();
    }

    @Test
    public void shouldAddAppliedInstructionToStack() {
        AppliedInstruction appliedInstruction = mock(AppliedInstruction.class);

        session.addAppliedInstruction(appliedInstruction);

        assertThat(session.countAppliedInstruction()).isEqualTo(1);
        assertThat(session.popAppliedInstruction().get()).isSameAs(appliedInstruction);
    }

    @Test
    public void popAppliedInstructionShouldReturnEmptyOptionalWhenStackIsEmpty() {
        Optional<AppliedInstruction> actualResult = session.popAppliedInstruction();

        assertThat(actualResult.isPresent()).isFalse();
    }

    @Test
    public void clearShouldRemoveAllAppliedInstructionFromStack() {
        session.addAppliedInstruction(mock(AppliedInstruction.class));

        session.clearAppliedInstruction();

        assertThat(session.countAppliedInstruction()).isEqualTo(0);
    }
}