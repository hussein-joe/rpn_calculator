package com.hussein.samples.rpncalculator.operator;

import com.hussein.samples.rpncalculator.engine.AppliedInstruction;
import com.hussein.samples.rpncalculator.engine.CalculatorSession;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UndoOperatorHandler implements OperatorHandler {
    @Override
    public void handle(String operator, CalculatorSession session) {
        if ( session.countAppliedInstruction() == 0 ) {
            session.popDigit();
            return;
        }

        reverseLastInstruction(session);
    }

    private void reverseLastInstruction(CalculatorSession session) {
        AppliedInstruction lastInstruction = session.popAppliedInstruction().get();
        Double[] instructionParameters = lastInstruction.getParameters();

        session.popDigit();
        ArrayUtils.reverse(instructionParameters);
        Stream.of(instructionParameters).forEach(session::addNumber);
    }
}
