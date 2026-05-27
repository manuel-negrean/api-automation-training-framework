package steps;

import config.BaseConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.slf4j.LoggerFactory;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;

public class Hooks {
    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    static RequestSpecification requestSpecification;

    @Before
    public void setUp(Scenario scenario) {
        log.info("Starting scenario: {}", scenario.getName());
        RestAssured.baseURI = BaseConfig.BASE_URL;
        requestSpecification = new RequestSpecBuilder()
                .addHeaders(BaseConfig.getDefaultHeaders())
                .setRelaxedHTTPSValidation()
                .build();
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("Finished scenario: {} with status: {}", scenario.getName(), scenario.getStatus());
    }
}
