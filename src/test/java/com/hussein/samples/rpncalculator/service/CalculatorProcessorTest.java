package com.hussein.samples.rpncalculator.service;

import com.hussein.samples.rpncalculator.engine.Calculator;
import com.hussein.samples.rpncalculator.engine.CalculatorResult.CalculatorResultBuilder;
import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import com.hussein.samples.rpncalculator.exceptions.NotSupportedOperatorException;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorProcessorTest {

    private static final String CALCULATOR_RESULT_PREFIX = "stack: ";
    @Mock
    private Calculator calculator;
    @Mock
    private CalculatorInputStream inputStream;
    @Mock
    private CalculatorOutputStream outputStream;
    @Mock
    private CalculatorSession session;
    @Mock
    private CalculatorSessionFactory sessionFactory;

    private CalculatorProcessor processor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        processor = new CalculatorProcessor(inputStream, outputStream, calculator, sessionFactory);
        when(sessionFactory.newSession()).thenReturn(session);
    }

    @Test
    public void shouldEndCalculationWhenPassExit() {
        when(inputStream.readLine()).thenReturn("exit");

        processor.start();

        verify(outputStream, never()).write(anyString());
    }

    @Test
    public void shouldIgnoreEmptyUserInput() {
        givenInputStreamInitializedWithUserInputs(inputStream, "");

        processor.start();

        verify(outputStream, never()).write(anyString());
    }

    @Test
    public void shouldWriteResultOfCalculationToOutputStream() {
        String expectedResult = "5";
        String userInput = "2 3 +";
        givenUserInputInitializeMockedCalculatorEvaluate(userInput, expectedResult);
        givenInputStreamInitializedWithUserInputs(inputStream, userInput);

        processor.start();

        verify(outputStream).write(CALCULATOR_RESULT_PREFIX + expectedResult);
    }

    @Test
    public void shouldWrite2ResultsOfCalculationsWhenPass2UserInputs() {
        String expectedResult1 = "5";
        String expectedResult2 = "20";
        String userInput1 = "2 3 +";
        String userInput2 = "4 *";
        givenUserInputInitializeMockedCalculatorEvaluate(userInput1, expectedResult1);
        givenUserInputInitializeMockedCalculatorEvaluate(userInput2, expectedResult2);
        givenInputStreamInitializedWithUserInputs(inputStream, userInput1, userInput2);

        processor.start();

        verify(outputStream).write(CALCULATOR_RESULT_PREFIX + expectedResult1);
        verify(outputStream).write(CALCULATOR_RESULT_PREFIX + expectedResult2);
    }

    @Test
    public void shouldWriteErrorMessageAndCalculationResultToOutputStreamWhenOperatorInsufficientParametersExceptionThrown() {
        String expectedResult = "5";
        String userInput = "2 3 + *";

        givenUserInputInitializeMockedCalculatorEvaluate(userInput, expectedResult,
                new OperatorInsufficientParametersException(()->"*"));
        givenInputStreamInitializedWithUserInputs(inputStream, userInput);

        processor.start();

        verify(outputStream).write(CALCULATOR_RESULT_PREFIX + expectedResult);
        verify(outputStream).write(contains("*" + ": insufficient parameters"));
    }

    @Test
    public void shouldWriteErrorMessageAndCalculationResultToOutputStreamWhenOperatorIsUnknownPassed() {
        String expectedResult = "5";
        String unknownOperator = "$";
        String userInput = "2 3 + " + unknownOperator;

        givenUserInputInitializeMockedCalculatorEvaluate(userInput, expectedResult,
                new NotSupportedOperatorException(()->unknownOperator));
        givenInputStreamInitializedWithUserInputs(inputStream, userInput);

        processor.start();

        verify(outputStream).write(CALCULATOR_RESULT_PREFIX + expectedResult);
        verify(outputStream).write(contains(unknownOperator + " is unknown"));
    }

    private void givenUserInputInitializeMockedCalculatorEvaluate(String userInput, String expectedResult) {
        CalculatorResultBuilder resultBuilder = new CalculatorResultBuilder();
        resultBuilder.withCalculationResult(expectedResult);
        when(calculator.evaluate(userInput, session)).thenReturn(resultBuilder.build());
    }

    private void givenUserInputInitializeMockedCalculatorEvaluate(String userInput,
                                                                  String expectedResult, RuntimeException exp) {
        CalculatorResultBuilder resultBuilder = new CalculatorResultBuilder();
        resultBuilder.withCalculationResult(expectedResult);
        resultBuilder.withCalculationError(exp);
        when(calculator.evaluate(userInput, session)).thenReturn(resultBuilder.build());
    }

    private void givenInputStreamInitializedWithUserInputs(CalculatorInputStream inputStreamReader,
                                                           String userInput, String... moreUserInputs) {
        OngoingStubbing<String> inputStreamStubbing = when(inputStreamReader.readLine()).thenReturn(userInput);
        for (String s: moreUserInputs) {
            inputStreamStubbing = inputStreamStubbing.thenReturn(s);
        }
        inputStreamStubbing = inputStreamStubbing.thenReturn("exit");
    }
}