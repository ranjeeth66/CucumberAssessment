package TestRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/Feature",
		glue={"stepDefinitions"},
		plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber-report.json"},
		monochrome = true,
		tags = "@newsValidation")

public class Runner
{
}
