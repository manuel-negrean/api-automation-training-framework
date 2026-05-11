package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PetTestsImproved extends BaseTest{
    long id = System.currentTimeMillis();

    @Test(priority = 0)
    public void createPet() {
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
        Assert.assertEquals(createdPet.getId(), id, "PetId is not correct");
        Assert.assertEquals(createdPet.getName(), "Azorel", "Dog name is incorrect");
        Assert.assertEquals(createdPet.getStatus(), "available");
        Assert.assertEquals(createdPet.getCategory().getName(), "CaineMare", "Category name is incorrect");
        Assert.assertEquals(createdPet.getTags().size(), 1, "Incorrect number of tags");
        Assert.assertEquals(createdPet.getTags().get(0).getName(), "Cuminte", "Incorrect tag name");
    }

    @Test(priority = 1)
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
        List<PetDto> pets = response.jsonPath().getList("", PetDto.class);
        Assert.assertFalse(pets.isEmpty(), "No pets found with status available");
        boolean foundPet = false;
        for (PetDto pet : pets) {
            Assert.assertEquals(pet.getStatus(), "available", "Pet status is not available");
            if (pet.getId() == id) {
                foundPet = true;
                log.info("Found created pet in list: {}", pet);
                Assert.assertEquals(pet.getName(), "Azorel", "Dog name is incorrect");
                Assert.assertEquals(pet.getCategory().getName(), "CaineMare", "Category name is incorrect");
                Assert.assertEquals(pet.getTags().size(), 1, "Incorrect number of tags");
                Assert.assertEquals(pet.getTags().get(0).getName(), "Cuminte", "Incorrect tag name");
            }
        }
        Assert.assertTrue(foundPet, "Created pet was not found in the list of available pets");
    }
    @Test(priority = 1)
    public void findPetById(){
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
        log.info("Found pet by ID: {}", pet);
        Assert.assertEquals(pet.getId(), id, "PetId is not correct");
        Assert.assertEquals(pet.getName(), "Azorel", "Dog name is incorrect");
        Assert.assertEquals(pet.getStatus(), "available");
        Assert.assertEquals(pet.getCategory().getName(), "CaineMare", "Category name is incorrect");
        Assert.assertEquals(pet.getTags().size(), 1, "Incorrect number of tags");
        Assert.assertEquals(pet.getTags().get(0).getName(), "Cuminte", "Incorrect tag name");
    }
    @Test(priority = 2)
    public void updatePet() {
        PetDto updatedPet = TestDataFactory.createPet(
                id,
                "AzorelUpdated",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "Cuminte"),
                "sold"
        );
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
        PetDto pet = response.as(PetDto.class);
        log.info("Updated pet: {}", pet);
        Assert.assertEquals(pet.getId(), id, "PetId is not correct");
        Assert.assertEquals(pet.getName(), "AzorelUpdated", "Dog name is incorrect");
        Assert.assertEquals(pet.getStatus(), "sold");
        Assert.assertEquals(pet.getCategory().getName(), "CaineMare", "Category name is incorrect");
        Assert.assertEquals(pet.getTags().size(), 1, "Incorrect number of tags");
        Assert.assertEquals(pet.getTags().get(0).getName(), "Cuminte", "Incorrect tag name");
    }
    @Test(priority = 3)
    public void deletePet() {
            given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/" + id)
                .then()
                .log().all()
                .statusCode(200);
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
