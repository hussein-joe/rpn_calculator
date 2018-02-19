package com.hussein.samples.rpncalculator;

import com.hussein.samples.rpncalculator.service.CalculatorProcessor;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RpnCalculatorConfiguration.class)
public class RpnCalculatorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(RpnCalculatorApplication.class)
				.main(RpnCalculatorApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.build()
				.run(args);

		//SpringApplication.run(RpnCalculatorApplication.class, args);
		CalculatorProcessor calculatorProcessor = context.getBean(CalculatorProcessor.class);
		calculatorProcessor.start();
	}
}
