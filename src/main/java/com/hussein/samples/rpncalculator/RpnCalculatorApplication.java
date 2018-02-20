package com.hussein.samples.rpncalculator;

import com.hussein.samples.rpncalculator.service.CalculatorProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import static java.lang.System.exit;

@SpringBootApplication
@Import(RpnCalculatorConfiguration.class)
public class RpnCalculatorApplication implements CommandLineRunner {

	@Autowired
	private  CalculatorProcessor calculatorProcessor;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(RpnCalculatorApplication.class)
				.main(RpnCalculatorApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.build()
				.run(args);

		SpringApplication.run(RpnCalculatorApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		calculatorProcessor.start();
		exit(0);
	}
}
