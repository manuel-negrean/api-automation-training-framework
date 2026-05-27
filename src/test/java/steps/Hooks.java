package steps;

import config.BaseConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {
    static RequestSpecification requestSpecification;

    @Before
    public void setUp(Scenario scenario) {
        log.info("Starting scenario {}",scenario);
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Accept", "application/json")
                .addHeader( "Content-Type", "application/json")
                .setBaseUri(BaseConfig.BASE_URL)
                .setRelaxedHTTPSValidation()
                .build();
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("Finished scenario {}",scenario);
    }
}
