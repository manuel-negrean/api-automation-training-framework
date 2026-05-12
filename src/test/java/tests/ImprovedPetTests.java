package tests;

import base.BaseTest;
import dto.PetDto;
import dto.PetDto.CategoryDto;
import dto.PetDto.TagDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

@Slf4j
public class ImprovedPetTests extends BaseTest {

    private static final String CUSTOM_STATUS = "CUSTOM_STATUS_VLAD";
    long petId = System.currentTimeMillis();

    PetDto petDto = TestDataFactory.createPet(
            petId,
            "SomeName",
            new CategoryDto(1, "SomeCategory"),
            new TagDto(1, "SomeTag"),
            CUSTOM_STATUS);

    @Test(priority = 1)
    public void createPet() {

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
        log.info("Created pet: {}", createdPet);
        assertEquals(createdPet, petDto, "Created pet is not the same with the values sent to server");
    }

    @Test(priority = 2)
    public void findPetsByStatus() {

        given()
                .spec(requestSpecification)
                .when()
                .get(String.format("/pet/findByStatus?status=%s", CUSTOM_STATUS))
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test(priority = 3)
    public void findPetsById() {

        given()
                .spec(requestSpecification)
                .when()
                .get(String.format("/pet/%s", petId))
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test(priority = 4)
    public void updatePet() {

        PetDto beforeUpdatePet = petDto.updatePetName("UpdatedPetName");

        Response response = given()
                .spec(requestSpecification)
                .body(beforeUpdatePet)
                .log().all()
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto updatedPet = response.as(PetDto.class);
        log.info("Updated pet: {}", updatedPet);
        assertEquals(updatedPet, beforeUpdatePet, "Updated pet is not the same with the values sent to server");
    }

    @Test(priority = 5)
    public void deletePet(){

        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete(String.format("/pet/%s", petId))
                .then()
                .log().all()
                .statusCode(200);

        given()
                .spec(requestSpecification)
                .when()
                .get(String.format("/pet/%s", petId))
                .then()
                .log().all()
                .statusCode(404);
    }
}
