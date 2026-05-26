package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import static io.restassured.RestAssured.given;

public class PetTestsImproved extends BaseTest {
    private long petId;

    @Test
    public void createPet() {
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(id, "Rexy",
                new PetDto.CategoryDto(1, "Caine Mic"),
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
        petId = createdPet.getId();
        log.info("Created pet with ID: " + petId);

        Assert.assertEquals(createdPet.getId(), id, "PetId is not correct");
        Assert.assertEquals(createdPet.getCategory().getName(), "Caine Mic", "Category name was not found");
        Assert.assertEquals(createdPet.getStatus(), "available", "Status is not correct");
    }

    @Test(dependsOnMethods = "createPet")
    public void findPetsByID() {
        log.info("Finding pet with ID: " + petId);

        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto foundPet = response.as(PetDto.class);
        Assert.assertEquals(foundPet.getId(), petId, "Returned pet ID does not match");
        log.info("Found pet: " + foundPet.getName());
    }

    @Test
    public void findPetsByStatus() {
        log.info("Finding pets by status: available");

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
        Assert.assertTrue(pets.length > 0, "No available pets found");
        log.info("Found " + pets.length + " available pets");
    }

    @Test(dependsOnMethods = "findPetsByID")
    public void updatePet() {
        log.info("Updating pet with ID: " + petId);

        PetDto updatedPetDto = TestDataFactory.createPet(
                petId,
                "UpdatedAdultDog",
                new PetDto.CategoryDto(1, "RexyUpdated"),
                new PetDto.TagDto(1, "friendly"),
                "available"
        );

        Response response = given()
                .spec(requestSpecification)
                .body(updatedPetDto)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto updatedPet = response.as(PetDto.class);
        log.info("Updated pet with ID: " + updatedPet.getId());
        Assert.assertEquals(updatedPet.getName(), "UpdatedAdultDog",
                "The updated name was not UpdatedAdultDog");
    }

    @Test(dependsOnMethods = "updatePet")
    public void deletePet() {
        log.info("Deleting pet with ID: " + petId);

        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200);

        log.info("Verifying pet was deleted");

    }
}
