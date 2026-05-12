package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

@Slf4j

public class PetTestsImproved extends BaseTest {
    private long petID = System.currentTimeMillis();

    @Test(priority = 1)
    public void createPet() {
        petID = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(
                petID,
                "Grivei",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "cuminte"),
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

    @Test(priority = 2)
    public void findPetById() {
        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/pet/" + petID)  // aici imi aduc ID-ul pet-ului creat
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto foundPetID = response.as(PetDto.class);
        log.info("The pet fount by ID is: " + foundPetID);  // aici loghez pet-ul gasit dupa ID
    }

    @Test(priority = 3)
    public void updatePet() {
        long id = petID;
        PetDto petDto = TestDataFactory.createPet(
                petID,
                "GriveiUpdatat", //update nume
                new PetDto.CategoryDto(1, "CaineMediu"), // update category
                new PetDto.TagDto(1, "rau"), //update tag
                "sold");
        Response response = given()
                .spec(requestSpecification)
                .body(petDto)
                .log().all()
                .when()
                .put("/pet")  // ca sa ii fac update folosesc metoda put
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        PetDto createdPet = response.as(PetDto.class);
        log.info("Updated pet: " + createdPet);  // aici loghez numele pet-ului updatat

    }

    @Test(priority = 4)
    public void findPetByStatus() {
        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/pet/findByStatus?status=sold")  // aici imi aduc statusul pet-ului creat
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        List<PetDto> fountPetStatus = response.as(new TypeRef<List<PetDto>>() {
        });
        log.info("The pet fount by Status is: " + fountPetStatus);  // aici loghez pet-ul gasit dupa Status
    }

    @Test(priority = 5)
    public void deletedPet() {
        long id = petID;
        PetDto petDto = TestDataFactory.createPet(
                petID,
                "Grivei",
                new PetDto.CategoryDto(1, "CaineMare"),
                new PetDto.TagDto(1, "cuminte"),
                "available");
        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/pet/" + petID)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();


        log.info("Created pet: " + petID); // aici loghez pet-ul sters
    }


}
