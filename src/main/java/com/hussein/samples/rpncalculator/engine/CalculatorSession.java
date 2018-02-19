package com.hussein.samples.rpncalculator.engine;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class CalculatorSession {

    private final Stack<Double> numberStack;
    private final Stack<AppliedInstruction> appliedInstructionStack;

    public CalculatorSession() {
        this.numberStack = new Stack<>();
        this.appliedInstructionStack = new Stack<>();
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
        if ( this.countDigits() == 0 ) {
            return null;
        }

        return numberStack.pop();
    }

    public void clearNumbers() {
        this.numberStack.clear();
    }

    public void addAppliedInstruction(AppliedInstruction appliedInstruction) {
        appliedInstructionStack.push(appliedInstruction);
    }

    public Optional<AppliedInstruction> popAppliedInstruction() {
        AppliedInstruction popedItem = null;
        if ( !appliedInstructionStack.isEmpty() ) {
            popedItem = appliedInstructionStack.pop();
        }

        return Optional.ofNullable(popedItem);
    }

    public void clearAppliedInstruction() {
        this.appliedInstructionStack.clear();
    }

    public int countAppliedInstruction() {
        return this.appliedInstructionStack.size();
    }
}
