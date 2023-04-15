package com.wipro.calculadoraFrete;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = "com.wipro.calculadoraFrete.cucumberSteps")
public class TesteCucumber {
}
