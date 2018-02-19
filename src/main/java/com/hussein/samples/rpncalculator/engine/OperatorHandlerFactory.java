package com.hussein.samples.rpncalculator.engine;

import com.hussein.samples.rpncalculator.operator.OperatorHandler;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

public class OperatorHandlerFactory {
    private final Map<String, OperatorHandler> operatorHandlerMap;

    public OperatorHandlerFactory(Map<String, OperatorHandler> operatorHandlerMap) {
        requireNonNull(operatorHandlerMap);
        this.operatorHandlerMap = unmodifiableMap(operatorHandlerMap);
    }

    public Optional<OperatorHandler> handlerFor(String operator) {
        requireNonNull(operator);
        return Optional.ofNullable(operatorHandlerMap.get(operator.trim().toLowerCase()));
    }
}
