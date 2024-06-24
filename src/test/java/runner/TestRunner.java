package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/feature/webtable.feature",
        glue = "src/test/java/steps/WebtableStepDefinition.java"
)
public class TestRunner
{

}
