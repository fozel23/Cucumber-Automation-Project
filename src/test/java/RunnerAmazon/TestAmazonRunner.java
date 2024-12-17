package RunnerAmazon;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/featuresAmazon/amazon_search.feature", // Feature dosyasının yolu
        glue = {"MyStepDef1"}, // Step Definitions dosyasının yolu
        plugin = {"pretty", "html:target/cucumber-reports"} // Test raporu
)
public class TestAmazonRunner {
}
