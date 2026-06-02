package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import utils.TestDataFactory;
import org.testng.Assert;

import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@Slf4j

public class PetTestsImproved extends BaseTest {

    //private long petID = 42142;

    @Test
    public void createPet() {
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "friendly"),
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
        log.info("Created pet with ID: " + createdPet.getId());

    }


    @Test
    public void findPetsByStatus() {

        Response response = given()
                .spec(requestSpecification)
                .queryParam("status", "available")
                .log().all()
                .when()
                .get("/pet/findByStatus")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertTrue(response.as(ArrayList.class).size() > 0, "No pets found");
    }


    @Test
    public void findPetsByID() {

        long id = System.currentTimeMillis();

        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "friendly"),
                "available");

        // create pet first
        given()
                .spec(requestSpecification)
                .body(petDto)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        // get pet
        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto foundPet = response.as(PetDto.class);
        Assert.assertEquals(foundPet.getId(), id);
    }


    @Test
    public void updatePet() {

        long id = System.currentTimeMillis();

        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "friendly"),
                "available");

        // create pet
        given()
                .spec(requestSpecification)
                .body(petDto)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        // update data
        PetDto updatedPet = TestDataFactory.createPet(
                id,
                "UpdatedAzorel",
                new PetDto.CategoryDto(1, "UpdatedCaine"),
                new PetDto.TagDto(1, "friendly"),
                "available");

        Response response = given()
                .spec(requestSpecification)
                .body(updatedPet)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto result = response.as(PetDto.class);
        Assert.assertEquals(result.getName(), "UpdatedAzorel");
    }


    @Test
    public void deletePet() {

        long id = System.currentTimeMillis();

        PetDto petDto = TestDataFactory.createPet(
                id,
                "Azorel",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "friendly"),
                "available");

        // create pet
        given()
                .spec(requestSpecification)
                .body(petDto)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        // delete pet
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/" + id)
                .then()
                .log().all()
                .statusCode(200);

        // verify deletion
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/pet/" + id)
                .then()
                .log().all()
                .statusCode(404);
    }
}




