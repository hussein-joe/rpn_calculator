package com.hussein.samples.rpncalculator.engine;

import com.hussein.samples.rpncalculator.operator.OperatorHandler;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.unmodifiableMap;

public class OperatorHandlerFactory {
    private final Map<String, OperatorHandler> operatorHandlerMap;

    public OperatorHandlerFactory(Map<String, OperatorHandler> operatorHandlerMap) {
        Objects.requireNonNull(operatorHandlerMap);
        this.operatorHandlerMap = unmodifiableMap(operatorHandlerMap);
    }

    public Optional<OperatorHandler> handlerFor(String operator) {
        return Optional.ofNullable(operatorHandlerMap.get(operator.trim()));
    }
}
