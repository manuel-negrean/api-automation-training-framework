package base;

import config.BaseConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;

@Slf4j
public class BaseTest {

    protected RequestSpecification requestSpecification;

    @BeforeClass
    public void setApiConfig() {
        RestAssured.baseURI = BaseConfig.BASE_URL;

        requestSpecification = new RequestSpecBuilder()
                .addHeaders(BaseConfig.getDefaultHeaders())
                .setRelaxedHTTPSValidation()
                .build();
        log.info("Configured with base URL: {}", RestAssured.baseURI);
    }
}

