package com.hussein.samples.rpncalculator.engine;

import com.google.common.base.Splitter;
import com.hussein.samples.rpncalculator.exceptions.NotSupportedOperatorException;
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

    public String evaluate(String userInput, CalculatorSession calculatorSession) {
        if ( userInput == null || userInput.trim().length() == 0 ) {
            return "";
        }

        List<String> tokens = Splitter.on(Pattern.compile("\\s")).omitEmptyStrings().splitToList(userInput);
        for (String token: tokens) {
            process(token, calculatorSession);
        }

        return stackContent(calculatorSession);
    }

    private void process(String token, CalculatorSession calculatorSession) {
        if ( digitProcessor.isDigit(token) ) {
            calculatorSession.addDigit(Double.valueOf(token));
            return;
        }
        Optional<OperatorHandler> handler = handlerFactory.handlerFor(token);
        handler.orElseThrow(() -> new NotSupportedOperatorException())
                .handle(token, calculatorSession);
    }

    private String stackContent(CalculatorSession calculatorSession) {
        List<Double> numbersInStack = calculatorSession.getNumberStack();
        return numbersInStack.stream().map(digitProcessor::formatNumber)
                .collect(Collectors.joining(" "));
    }


}
