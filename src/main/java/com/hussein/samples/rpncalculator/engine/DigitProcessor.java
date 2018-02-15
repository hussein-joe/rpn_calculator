package com.hussein.samples.rpncalculator.engine;

public class DigitProcessor {

    public String formatNumber(Double number) {
        return String.valueOf(number);
    }

    public boolean isDigit(String token) {
        return true;
    }

    public Double toDigit(String token) {
        return Double.valueOf(token);
    }
}
