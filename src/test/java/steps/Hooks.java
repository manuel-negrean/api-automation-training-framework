package steps;

import config.BaseConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.messages.types.Hook;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Hooks {
    private static final Logger log = LoggerFactory.getLogger(Hook.class);

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