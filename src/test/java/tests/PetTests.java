package tests;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


@Slf4j
public class PetTests {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    long petId = System.currentTimeMillis();

    @Test(priority = 0)
    public void createPet(){
        log.info("Creating pet with ID: " + petId);

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
                .header("Accept", "application/json")
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
        log.info("Response: " + response.asString());

        long returnedId = response.jsonPath().getLong("id");
        Assert.assertEquals(returnedId, petId, "Returned pet ID should match the created pet ID");
        String returnedName = response.jsonPath().getString("category.name");
        Assert.assertEquals(returnedName, "Dogs", "Returned category name should match the created category name");
        String returnedDogName = response.jsonPath().getString("name");
        Assert.assertEquals(returnedDogName, "AdultDog", "Returned pet name should match the created pet name");

    }

    @Test(priority = 0)
    public void findPetsByStatus(){
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .log().all()
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        log.info("Response: " + response.asString());
    }

    @Test(priority = 1)
    public void findPetsById(){
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .log().all()
                .when()
                .get("/pet/"+petId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        log.info("Response: " + response.asString());
    }

    @Test(priority = 1)
    public void updatePet(){
        String requestBody = "{"
                + "\"id\": " + petId + ","
                + "\"category\": {"
                + "  \"id\": 1,"
                + "  \"name\": \"Dogs\""
                + "},"
                + "\"name\": \"UpdatedDog\","
                + "\"photoUrls\": [],"
                + "\"tags\": ["
                + "  {\"id\": 1, \"name\": \"friendly\"}"
                + "],"
                + "\"status\": \"available\""
                + "}";
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        log.info("Response: " + response.asString());

        String returnedName = response.jsonPath().getString("name");
        Assert.assertEquals(returnedName, "UpdatedDog", "Returned pet name should match the updated pet name");
    }

    @Test(priority = 2)
    public void deletePet() {
         given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .log().all()
                .when()
                .delete("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200);

        given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .log().all()
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(404);
    }
}