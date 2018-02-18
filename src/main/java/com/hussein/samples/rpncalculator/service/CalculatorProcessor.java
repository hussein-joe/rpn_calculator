package com.hussein.samples.rpncalculator.service;

import com.hussein.samples.rpncalculator.engine.Calculator;
import com.hussein.samples.rpncalculator.engine.CalculatorResult;
import com.hussein.samples.rpncalculator.engine.CalculatorSession;

import static com.google.common.base.Strings.isNullOrEmpty;

public class CalculatorProcessor {

    private static final String CALCULATOR_RESULT_PREFIX = "stack: ";
    private final CalculatorInputStream inputStream;
    private final CalculatorOutputStream outputStream;
    private final Calculator calculator;
    private final CalculatorSessionFactory sessionFactory;

    public CalculatorProcessor(CalculatorInputStream inputStream, CalculatorOutputStream outputStream,
                               Calculator calculator, CalculatorSessionFactory sessionFactory) {

        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.calculator = calculator;
        this.sessionFactory = sessionFactory;
    }

    public void start() {
        CalculatorSession session = sessionFactory.newSession();
        while (true) {
            String userInput = inputStream.readLine();
            if (isNullOrEmpty(userInput)) {
                continue;
            }

            if ( exitCalculator(userInput) ) {
                break;
            }

            CalculatorResult calculationResult = calculator.evaluate(userInput, session);
            writeCalculatorResultToOutputStream(calculationResult);
        }
    }

    private void writeCalculatorResultToOutputStream(CalculatorResult<? extends RuntimeException> calculatorResult) {
        outputStream.writeLine(CALCULATOR_RESULT_PREFIX + calculatorResult.getCalculationResult());
        calculatorResult.getCalculationError().map(RuntimeException::getMessage)
                .ifPresent(outputStream::writeLine);
    }

    private boolean exitCalculator(String userInput) {
        return "exit".equalsIgnoreCase(userInput);
    }
}
