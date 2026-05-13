package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import dto.PetDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.given;
@Slf4j
public class PetTestImproved extends BaseTest {





        long id = 108108;

        @Test
        public void createPet(){

            PetDto petDto = TestDataFactory.createAndUpdatePetBody(
                    id,
                    "Grivei",
                    new PetDto.CategoryDto(1, "Vagabond"),
                    new PetDto.TagDto(1, "Prietenos"),
                    "available"
            );
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
            log.info("Created pet :" + createdPet);
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
                    .get("/pet/" + id)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .response();
        }

        @Test
        public void updatePet(){

            PetDto petDto = TestDataFactory.createAndUpdatePetBody(
                    id,
                    "Maximus",
                    new PetDto.CategoryDto(1, "Dogs"),
                    new PetDto.TagDto(1, "Prietenos"),
                    "available"
            );
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
        public void deletePet() {
            given()
                    .spec(requestSpecification)
                    .when()
                    .delete("/pet/" + id)
                    .then()
                    .log().all()
                    .statusCode(200);

            given()
                    .spec(requestSpecification)
                    .when()
                    .get("/pet/" + id)
                    .then()
                    .log().all()
                    .statusCode(404);
        }
    }


