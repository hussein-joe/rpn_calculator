package com.hussein.samples.rpncalculator.engine;

import java.text.DecimalFormat;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class DigitProcessor {

    private DecimalFormat decimalFormat;

    public DigitProcessor(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public String formatNumber(Double number) {
        requireNonNull(number);
        return decimalFormat.format(number);
    }
}
