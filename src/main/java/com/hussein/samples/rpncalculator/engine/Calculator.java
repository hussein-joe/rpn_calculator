package com.hussein.samples.rpncalculator.engine;

import com.google.common.base.Splitter;
import com.hussein.samples.rpncalculator.operator.OperatorHandler;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator {

    private final CalculatorSession session;
    private final DigitProcessor digitProcessor;
    private final OperatorHandlerFactory handlerFactory;

    public Calculator(CalculatorSession session, DigitProcessor digitProcessor, OperatorHandlerFactory handlerFactory) {
        this.session = session;
        this.digitProcessor = digitProcessor;
        this.handlerFactory = handlerFactory;
    }

    public String evaluate(String userInput) {
        if ( userInput == null || userInput.trim().length() == 0 ) {
            return "";
        }

        List<String> tokens = Splitter.on(Pattern.compile("\\s")).omitEmptyStrings().splitToList(userInput);
        for (String token: tokens) {
            process(token);
        }

        return stackContent();
    }

    private void process(String token) {
        if ( digitProcessor.isDigit(token) ) {
            session.addDigit(Double.valueOf(token));
            return;
        }
        Optional<OperatorHandler> handler = handlerFactory.handlerFor(token);
        handler.orElseThrow(() -> new RuntimeException("Operator " + token + " is not supported"))
                .handle(token, session);
    }

    private String stackContent() {
        List<Double> numbersInStack = session.getNumberStack();
        return numbersInStack.stream().map(digitProcessor::formatNumber)
                .collect(Collectors.joining(" "));
    }


}
