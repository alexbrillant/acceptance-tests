
package ca.ulaval.glo4002.med.acctests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "classpath:med_features/", tags = {"@medium,@large"})
public class MedAccTest {
}