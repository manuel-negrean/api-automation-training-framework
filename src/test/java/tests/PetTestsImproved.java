package tests;
import lombok.extern.slf4j.Slf4j;
import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class PetTestsImproved extends BaseTest {
    @Test
    public void createPet() {
        // Implement the test using the improved structure
        long id = System.currentTimeMillis(); // Unique ID for the pet
        PetDto petDto = TestDataFactory.createPet(id, "Azorel", new PetDto.CategoryDto(1, "Caine Mic"), new PetDto.TagDto(1, "Cuminte"), "available");
        Response response = given()
                .spec(requestSpecification)
                .body(petDto)
                .log().all()
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto createdPet = response.as(PetDto.class);
        log.info("Created pet with ID: " + createdPet.getId());

    }
}
