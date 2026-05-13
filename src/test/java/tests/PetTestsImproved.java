package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.TestDataFactory;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class PetTestsImproved extends BaseTest {

    @Test
    public void createPet() {
        long id = System.currentTimeMillis(); // Generate a unique pet ID based on the current time
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

        PetDto createdPet = response.as(PetDto.class);
        log.info("Created pet: " + createdPet);
    }
    
    @Test
    public void findPetsByStatus(){
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

        PetDto[] pets = response.as(PetDto[].class);
        log.info("Found " + pets.length + " pets with status 'available'");
    }

    @Test
    public void findPetsById(){
        long id = 557;
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

        PetDto pet = response.as(PetDto.class);
        log.info("Found pet with ID " + id + ": " + pet);
    }

    @Test
    public void updatePet(){
        long id = 557;
        PetDto petDto = TestDataFactory.createPet(id, "New_Azorel", new PetDto.CategoryDto(1, "CaineMare"), new PetDto.TagDto(1, "Cuminte"), "available");
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

        PetDto updatedPet = response.as(PetDto.class);
        log.info("Updated pet: " + updatedPet);
    }

    @Test
    public void deletePet(){
        long id = 557;
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        log.info("Deleted pet with ID " + id);

        // Verify the pet no longer exists
        given()
                .spec(requestSpecification)
                .when()
                .get("/pet/" + id)
                .then()
                .statusCode(404);

        log.info("Confirmed pet with ID " + id + " no longer exists");
    }
}
