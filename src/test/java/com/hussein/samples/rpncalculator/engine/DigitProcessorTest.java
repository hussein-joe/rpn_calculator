package com.hussein.samples.rpncalculator.engine;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DecimalFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class DigitProcessorTest {

    private DecimalFormat decimalFormat;

    private DigitProcessor digitProcessor;

    @Before
    public void setUp() throws Exception {
        //DecimalFormat can not be mocked
        decimalFormat = new DecimalFormat("0.##");

        digitProcessor = new DigitProcessor(decimalFormat);
    }

    @Test
    public void shouldFormatNumberTo10DecimalPlaces() {
        double number = 1.123456789101112;
        String expectedResult = "1.12";

        String actualResult = digitProcessor.formatNumber(number);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}