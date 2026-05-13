package tests;

import base.BaseTest;
import dto.PetDto;
import org.testng.annotations.AfterClass;
import utils.TestDataFactory;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetTestsImproved extends BaseTest {
    private static long petId;

    @Test
    public void createPet() {
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(id, "Azorel", new PetDto.CategoryDto(1, "CaineMare"), new PetDto.TagDto(1, "Cuminte"), "available");
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
        PetDto createPet = response.as(PetDto.class);
        petId = createPet.getId();
        log.info("Create pet:" + createPet);

        // tema findPEt by status, update, delete , add,  commit si push


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
                .extract()
                .response();

    }

    @Test
    public void updatePet() {

        PetDto updatePet = TestDataFactory.createPet(petId, "Grivei", new PetDto.CategoryDto(1, "CaineMare"), new PetDto.TagDto(1, "Cuminte"), "sold");
        Response updateResponse = given()
                .spec(requestSpecification)
                .body(updatePet)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        PetDto updatedPet = updateResponse.as(PetDto.class);
        log.info("Updated pet:" + updatedPet);
    }

    @Test
    public void deletePet() {
        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
         log.info("Deleted pet with id: " + petId);
    }
}

