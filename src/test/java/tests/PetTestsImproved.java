package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import static config.BaseConfig.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.testng.TestRunner.PriorityWeight.priority;

@Slf4j

public class  PetTestsImproved extends BaseTest {

    private long createdPetId;

    @Test (priority = 1)
    public void createPet(){
        createdPetId = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(createdPetId, "Azorel", new PetDto.CategoryDto(1, "CaineMare"), "available", new PetDto.TagDto(1, "cuminte"));
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
        createdPetId = createdPet.getId();
        log.info("Created pet :" + createdPet);

    }
@Test (priority = 2)
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

        PetDto [] pets = response.as(PetDto[].class);
        Assert.assertTrue(pets.length > 0, "No pets found with status available");
    }
@Test (priority = 3, dependsOnMethods = "createPet")
    public void findPetsByID() {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get("/pet/" + createdPetId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

    PetDto pet = response.as(PetDto.class);

    Assert.assertEquals(pet.getId(), createdPetId, "Returned pet ID does not match the created pet ID");
    Assert.assertNotNull(pet.getName(), "Pet name should not be null");


}

    @Test (priority = 4, dependsOnMethods = "createPet")
    public void updatePet() {
        long id = System.currentTimeMillis();
        PetDto updatedBody = TestDataFactory.updatePet(createdPetId, new PetDto.CategoryDto(1, "UpdatedDogs"), "UpdatedAdultDog", new PetDto.TagDto(1, "friendly"), "available");
        Response response = given()
                .spec(requestSpecification)
                .body(updatedBody)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertEquals(response.jsonPath().getString("name"), "UpdatedAdultDog",
                "The updated name was not UpdatedAdultDog");

        PetDto updatedPet = response.as(PetDto.class);
        log.info("Updated pet :" + updatedPet);
    }

    @Test (priority = 5, dependsOnMethods = "createPet")
    public void deletePet(){
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/"+ createdPetId)
                .then()
                .log().all()
                .statusCode(200);

        given()
                .spec(requestSpecification)
                .when()
                .get("/pet/" + createdPetId)
                .then()
                .log().all()
                .statusCode(404);

        Assert.assertTrue(true, "Pet with ID 42142 was successfully deleted and cannot be found.");


    }


}
