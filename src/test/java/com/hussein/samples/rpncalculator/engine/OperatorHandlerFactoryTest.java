package com.hussein.samples.rpncalculator.engine;

import com.hussein.samples.rpncalculator.operator.OperatorHandler;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class OperatorHandlerFactoryTest {

    private static final String OPERATOR = "+";
    private static final String OPERATOR_CLEAR = "clear";

    @Mock
    private OperatorHandler operatorHandler;
    @Mock
    private OperatorHandler clearOperatorHandler;

    private Map<String, OperatorHandler> operatorHandlerMap;

    private OperatorHandlerFactory operatorHandlerFactory;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        
        operatorHandlerMap = Maps.newHashMap(OPERATOR, operatorHandler);
        operatorHandlerMap = Maps.newHashMap(OPERATOR_CLEAR, clearOperatorHandler);
        operatorHandlerFactory = new OperatorHandlerFactory(operatorHandlerMap);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenPassNotConfiguredOperator() {
        String unknownOperator = "$";
        Optional<OperatorHandler> operatorHandler = operatorHandlerFactory.handlerFor(unknownOperator);

        assertThat(operatorHandler.isPresent()).isFalse();
    }

    @Test
    public void shouldReturnOperatorHandlerWhenPassConfiguredOperator() {
        Optional<OperatorHandler> actualResult = operatorHandlerFactory.handlerFor(OPERATOR);

        assertThat(actualResult.get()).isSameAs(operatorHandler);
    }

    @Test
    public void shouldIgnoreCaseForPassedOperator() {
        Optional<OperatorHandler> actualResult = operatorHandlerFactory.handlerFor(OPERATOR_CLEAR.toUpperCase());

        assertThat(actualResult.get()).isSameAs(clearOperatorHandler);
    }
}