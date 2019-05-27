package maze.application;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"} , features = "src\\test\\java\\maze\\application\\" , glue="maze.application")
public class Run {
}
