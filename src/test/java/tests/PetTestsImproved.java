package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PetTestsImproved extends BaseTest{

    @Test
    public void createPet() {
        long id = System.currentTimeMillis();
        PetDto newPet = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "Cuminte"),
                "available"
        );
        Response response = given()
                .spec(requestSpecification)
                .body(newPet)
                .log().all()
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        PetDto createdPet = response.as(PetDto.class);
        log.info("Created pet : {}", createdPet);
    }
}
