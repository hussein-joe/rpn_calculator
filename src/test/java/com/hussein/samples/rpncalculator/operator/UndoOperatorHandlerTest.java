package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.AppliedInstruction;
import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UndoOperatorHandlerTest {

    private static final String OPERATOR = "+";

    @Mock
    private CalculatorSession session;

    private UndoOperatorHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        handler = new UndoOperatorHandler();
    }

    @Test
    public void shouldPopNumberFromStackWhenNoPreviousInstruction() {
        when(session.countAppliedInstruction()).thenReturn(0);

        handler.handle(OPERATOR, session);

        verify(session).popDigit();
    }

    @Test
    public void shouldPopLastNumberFromStackAndAddPreviousInstructionParametersIntoNumberStack() {
        AppliedInstruction lastInstruction = mock(AppliedInstruction.class);
        when(lastInstruction.getParameters()).thenReturn(new Double[]{1d, 2d});
        when(session.popAppliedInstruction()).thenReturn(Optional.of(lastInstruction));
        when(session.countAppliedInstruction()).thenReturn(1);


        handler.handle(OPERATOR, session);

        InOrder addNumberCallInOrder = inOrder(session);
        verify(session).popDigit();
        addNumberCallInOrder.verify(session).addNumber(2d);
        addNumberCallInOrder.verify(session).addNumber(1d);
    }
}