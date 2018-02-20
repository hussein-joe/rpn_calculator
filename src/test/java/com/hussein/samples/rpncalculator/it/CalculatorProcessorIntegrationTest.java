package com.hussein.samples.rpncalculator.it;

import com.hussein.samples.rpncalculator.RpnCalculatorConfiguration;
import com.hussein.samples.rpncalculator.engine.Calculator;
import com.hussein.samples.rpncalculator.engine.OperatorHandlerFactory;
import com.hussein.samples.rpncalculator.service.CalculatorInputStream;
import com.hussein.samples.rpncalculator.service.CalculatorOutputStream;
import com.hussein.samples.rpncalculator.service.CalculatorProcessor;
import com.hussein.samples.rpncalculator.service.CalculatorSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.hussein.samples.rpncalculator.CalculatorTestHelper.givenInputStreamInitializedWithUserInputs;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RpnCalculatorConfiguration.class})
public class CalculatorProcessorIntegrationTest {

    @MockBean
    private CalculatorInputStream inputStream;
    @MockBean
    private CalculatorOutputStream outputStream;
    @Autowired
    private Calculator calculator;
    @Autowired
    private CalculatorSessionFactory calculatorSessionFactory;

    private CalculatorProcessor calculatorProcessor;

    @Before
    public void setUp() throws Exception {
        calculatorProcessor = new CalculatorProcessor(inputStream, outputStream,
                calculator, calculatorSessionFactory);
    }

    @Test
    public void shouldCalculateStandardExpressionWithMultipleLines() {
        givenInputStreamInitializedWithUserInputs(inputStream, "1 2 3 +",
                "*", " 2 /");

        calculatorProcessor.start();

        verify(outputStream).writeLine(contains("stack: 2.5"));
    }

    @Test
    public void shouldPrintInsufficientParameters() {
        givenInputStreamInitializedWithUserInputs(inputStream, "1 2 3 +",
                "*", "/");

        calculatorProcessor.start();

        InOrder outputStreamInOrder = inOrder(outputStream);

        outputStreamInOrder.verify(outputStream).writeLine(contains("insufficient parameters"));
        outputStreamInOrder.verify(outputStream).writeLine(contains("stack: 5"));
    }

    @Test
    public void shouldUndoAppliedOperations() {
        givenInputStreamInitializedWithUserInputs(inputStream,
                "5 4 3 2 undo undo * 5 * undo");

        calculatorProcessor.start();

        verify(outputStream).writeLine(contains("stack: 20 5"));
    }

    @Test
    public void shouldClearStack() {
        givenInputStreamInitializedWithUserInputs(inputStream,
                "5 4 3 2 undo undo * 5 * undo", "clear");

        calculatorProcessor.start();

        InOrder outputStreamInOrder = inOrder(outputStream);

        outputStreamInOrder.verify(outputStream).writeLine(contains("stack: 20 5"));
        outputStreamInOrder.verify(outputStream).writeLine(contains("stack: "));
    }
}
