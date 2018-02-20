package com.hussein.samples.rpncalculator;

import com.hussein.samples.rpncalculator.engine.Calculator;
import com.hussein.samples.rpncalculator.engine.DigitProcessor;
import com.hussein.samples.rpncalculator.engine.OperatorHandlerFactory;
import com.hussein.samples.rpncalculator.operator.*;
import com.hussein.samples.rpncalculator.service.CalculatorInputStream;
import com.hussein.samples.rpncalculator.service.CalculatorOutputStream;
import com.hussein.samples.rpncalculator.service.CalculatorProcessor;
import com.hussein.samples.rpncalculator.service.CalculatorSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class RpnCalculatorConfiguration {

    @Value("${application.number_format}")
    private String numberFormat;

    @Bean
    public OperatorHandlerFactory operatorHandlerFactory(DigitProcessor digitProcessor) {
        Map<String, OperatorHandler> operatorHandlerMap = new HashMap<>();
        operatorHandlerMap.put("+", new TwoParameterBasedOperatorHandler( (p1, p2) -> p1+p2, digitProcessor));
        operatorHandlerMap.put("-", new TwoParameterBasedOperatorHandler( (p1, p2) -> p1-p2, digitProcessor));
        operatorHandlerMap.put("*", new TwoParameterBasedOperatorHandler((p1, p2) -> p1*p2, digitProcessor));
        operatorHandlerMap.put("/", new TwoParameterBasedOperatorHandler((p1, p2) -> p1/p2, digitProcessor));
        operatorHandlerMap.put("sqrt", new OneParameterBasedOperatorHandler(Math::sqrt, digitProcessor));
        operatorHandlerMap.put("clear", new ClearOperatorHandler());
        operatorHandlerMap.put("undo", new UndoOperatorHandler());

        return new OperatorHandlerFactory(operatorHandlerMap);
    }

    @Bean
    public CalculatorProcessor calculatorProcessor(CalculatorInputStream inputStream, CalculatorOutputStream outputStream,
                                                   Calculator calculator) {
        return new CalculatorProcessor(inputStream, outputStream, calculator, calculatorSessionFactory());
    }

    @Bean
    public CalculatorSessionFactory calculatorSessionFactory() {
        return new CalculatorSessionFactory();
    }

    @Bean
    public Calculator calculator(DigitProcessor digitProcessor, OperatorHandlerFactory operatorHandlerFactory) {
        return new Calculator(digitProcessor, operatorHandlerFactory);
    }

    @Bean
    public CalculatorInputStream consoleInputStream() {
        return new ConsoleCalculatorInputStream();
    }

    @Bean
    public CalculatorOutputStream consoleOutputStream() {
        return new CalculatorOutputStream() {
            @Override
            public void writeLine(String result) {
                System.out.println(result);
            }
        };
    }

    @Bean
    public DigitProcessor digitProcessor() {
        return new DigitProcessor(new DecimalFormat(numberFormat));
    }

    private static class ConsoleCalculatorInputStream implements CalculatorInputStream {
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        @Override
        public String readLine() {
            try {
                System.out.println("Please start typing your expression: ");
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
