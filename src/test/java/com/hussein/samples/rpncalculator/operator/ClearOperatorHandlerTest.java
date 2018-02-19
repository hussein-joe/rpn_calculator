package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(MockitoJUnitRunner.class)
public class ClearOperatorHandlerTest {

    @Mock
    private CalculatorSession session;

    private ClearOperatorHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.handler = new ClearOperatorHandler();
    }

    @Test
    public void shouldDelegateToSessionToClearNumbersAndAppliedInstructions() {
        handler.handle("+", session);

        verify(session).clearNumbers();
        verify(session).clearAppliedInstruction();
    }
}