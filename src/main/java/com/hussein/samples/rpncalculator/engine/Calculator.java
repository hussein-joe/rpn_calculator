package com.hussein.samples.rpncalculator.engine;

import com.google.common.base.Splitter;
import com.hussein.samples.rpncalculator.engine.CalculatorResult.CalculatorResultBuilder;
import com.hussein.samples.rpncalculator.exceptions.NotSupportedOperatorException;
import com.hussein.samples.rpncalculator.exceptions.OperatorInsufficientParametersException;
import com.hussein.samples.rpncalculator.operator.OperatorHandler;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator {

    private final DigitProcessor digitProcessor;
    private final OperatorHandlerFactory handlerFactory;

    public Calculator(DigitProcessor digitProcessor, OperatorHandlerFactory handlerFactory) {
        this.digitProcessor = digitProcessor;
        this.handlerFactory = handlerFactory;
    }

    public CalculatorResult evaluate(String userInput, CalculatorSession calculatorSession) {
        if ( userInput == null || userInput.trim().length() == 0 ) {
            return new CalculatorResultBuilder()
                    .withCalculationResult("")
                    .build();
        }

        List<String> tokens = Splitter.on(Pattern.compile("\\s")).omitEmptyStrings().splitToList(userInput);
        CalculatorResultBuilder resultBuilder = new CalculatorResultBuilder();
        try {
            for (String token : tokens) {
                process(token, calculatorSession);
            }
        } catch (NotSupportedOperatorException | OperatorInsufficientParametersException exp) {
            resultBuilder.withCalculationError(exp);
        }

        return resultBuilder
                .withCalculationResult(stackContent(calculatorSession))
                .build();
    }

    private void process(String token, CalculatorSession calculatorSession) {
        if ( digitProcessor.isDigit(token) ) {
            calculatorSession.addDigit(Double.valueOf(token));
            return;
        }
        Optional<OperatorHandler> handler = handlerFactory.handlerFor(token);
        handler.orElseThrow(() -> new NotSupportedOperatorException(()->token))
                .handle(token, calculatorSession);
    }

    private String stackContent(CalculatorSession calculatorSession) {
        List<Double> numbersInStack = calculatorSession.getNumberStack();
        return numbersInStack.stream().map(digitProcessor::formatNumber)
                .collect(Collectors.joining(" "));
    }


}
