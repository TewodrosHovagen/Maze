package e2e;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"} , features = "src/test/java/e2e/MatchE2E.feature" )
public class Run {
}
