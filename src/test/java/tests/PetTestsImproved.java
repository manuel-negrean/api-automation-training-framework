package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;

@Slf4j
public class PetTestsImproved extends BaseTest {

    @Test
    public void createPet() {
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "Cuminte"),
                "available");
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
        log.info("Created pet: " + createdPet);
    }

    @Test
    public void findPetById() {
        // First create a pet
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "Cuminte"),
                "available");
        given()
                .spec(requestSpecification)
                .body(petDto)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        // Then find it by ID
        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo("Azorel"))
                .body("status", equalTo("available"))
                .extract()
                .response();
        PetDto foundPet = response.as(PetDto.class);
        log.info("Found pet by ID: " + foundPet);
    }

    @Test
    public void findPetByStatus() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("status", "available")
                .log().all()
                .when()
                .get("/pet/findByStatus")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].status", equalTo("available"))
                .extract()
                .response();
        PetDto[] pets = response.as(PetDto[].class);
        log.info("Found " + pets.length + " pets with status 'available'");
    }

    @Test
    public void updatePet() {
        // First create a pet
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "Cuminte"),
                "available");
        given()
                .spec(requestSpecification)
                .body(petDto)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        // Update the pet
        petDto.setName("AzorelUpdated");
        petDto.setStatus("sold");

        Response response = given()
                .spec(requestSpecification)
                .body(petDto)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("AzorelUpdated"))
                .body("status", equalTo("sold"))
                .extract()
                .response();
        PetDto updatedPet = response.as(PetDto.class);
        log.info("Updated pet: " + updatedPet);
    }

    @Test
    public void deletePet() {
        // First create a pet
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "Cuminte"),
                "available");
        given()
                .spec(requestSpecification)
                .body(petDto)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        // Delete the pet
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/" + id)
                .then()
                .log().all()
                .statusCode(200);
        log.info("Deleted pet with ID: " + id);

        // Verify it's deleted
        given()
                .spec(requestSpecification)
                .when()
                .get("/pet/" + id)
                .then()
                .log().all()
                .statusCode(404);
        log.info("Verified pet with ID " + id + " no longer exists");
    }
}
