package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;


import static io.restassured.RestAssured.given;
@Slf4j

public class PetTestsImproved extends BaseTest {
    @Test
    public void createPet() {
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(
                999666,
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
    public void findPetsByStatus() {

        Response response = given()
                .spec(requestSpecification)
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void findPetsByID() {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get("/pet/" + 1)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        PetDto foundPetById = response.as(PetDto.class);
        log.info("Found pet by id: " + foundPetById);
    }

    @Test
    public void updatePet() {
        log.info("Id = " + 999666);

        PetDto petDto = TestDataFactory.createPet(
                999666,
                "Bethoven",
                new PetDto.CategoryDto(1, "hamham"),
                new PetDto.TagDto(1, "rau"),
                "available");

        Response response = given()
                .spec(requestSpecification)
                .body(petDto)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertEquals(response.jsonPath().getString("name"), "Bethoven",
                "The name was updated to Bethoven");
    }

    @Test
    public void deletePet(){
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/"+999666)
                .then()
                .log().all()
                .statusCode(200);

        given()
                .spec(requestSpecification)
                .when()
                .get("/pet/" + 999666)
                .then()
                .log().all()
                .statusCode(404);
    }
}
