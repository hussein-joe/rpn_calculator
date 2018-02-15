package com.hussein.samples.rpncalculator.engine;

import com.hussein.samples.rpncalculator.operator.OperatorHandler;

import java.util.Optional;

public class OperatorHandlerFactory {
    public Optional<OperatorHandler> handlerFor(String operator) {
        return Optional.empty();
    }
}
