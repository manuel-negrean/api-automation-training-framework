package test;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetTests {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    long petId = System.currentTimeMillis();

    @BeforeClass
    public void createPet(){
        log.info("Id = " + petId);
        String requestBody = "{"
                + "\"id\": " + petId + ","
                + "\"category\": {"
                + "  \"id\": 1,"
                + "  \"name\": \"Dogs\""
                + "},"
                + "\"name\": \"AdultDog\","
                + "\"photoUrls\": [],"
                + "\"tags\": ["
                + "  {\"id\": 1, \"name\": \"friendly\"}"
                + "],"
                + "\"status\": \"available\""
                + "}";

        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .headers("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        long returnedId = response.jsonPath().getLong("id");
        Assert.assertEquals(returnedId, petId, "Returned pet ID should match the one sent in the request");

        String returnedName = response.jsonPath().getString("category.name");
        Assert.assertEquals(returnedName, "Dogs", "Returned pet name should match the");

        String returnedStatus = response.jsonPath().getString("status");
        Assert.assertEquals(returnedStatus, "available", "Returned pet status should match the one sent in the request");
    }

    @Test
    public void findPetsStatus(){
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .headers("Accept", "application/json")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        //int petCount = response.jsonPath().getList("$").size();
        //Assert.assertTrue(petCount > 0, "There should be at least one available pet");
    }

    @Test
    public void findPetById(){
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .headers("Accept", "application/json")
                .when()
                .get("/pet/" + petId) //cel declarat la inceput
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void updatePet() {
        log.info("Id = " + petId);
        String updatedBody = "{"
                + "\"id\": " + petId + ","
                + "\"category\": {"
                + "  \"id\": 1,"
                + "  \"name\": \"DogsUpdated\""
                + "},"
                + "\"name\": \"AdultDogUpdated\","
                + "\"photoUrls\": [],"
                + "\"tags\": ["
                + "  {\"id\": 1, \"name\": \"friendly\"}"
                + "],"
                + "\"status\": \"available\""
                + "}";

        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .headers("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(updatedBody)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertEquals(response.jsonPath().getString("name"), "AdultDogUpdated", "Category name should be updated");

    }

    @AfterClass
    public void deletePet() {
        given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .headers("Accept", "application/json")
                .log().all()
                .when()
                .delete("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200);


        given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .headers("Accept", "application/json")
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(404);
    }
}
