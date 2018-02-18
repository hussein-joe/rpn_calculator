package com.hussein.samples.rpncalculator.engine;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CalculatorSession {

    private final Stack<Double> numberStack;

    public CalculatorSession() {
        this.numberStack = new Stack<>();
    }

    public List<Double> getNumbersInStack() {
        return Collections.unmodifiableList(this.numberStack);
    }

    public void addNumber(Double number) {
        numberStack.push(number);
    }

    public int countDigits() {
        return numberStack.size();
    }

    public Double popDigit() {
        return numberStack.pop();
    }
}
