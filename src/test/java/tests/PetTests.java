package tests;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetTests {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private long petId = 90982L;


    @Test(priority = 1)
    public void createPet() {
        log.info("Id = {}", petId);

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
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        long returnedId = response.jsonPath().getLong("id");
        Assert.assertEquals(returnedId, petId, String.format("Incorrect id Actual=%s, Expected=%s", returnedId, petId));
        String returnedName = response.jsonPath().getString("category.name");
        Assert.assertEquals(returnedName, "Dogs", String.format("Incorrect name Actual=%s, Expected=%s", returnedName, "Dogs"));
        long statusCode = response.statusCode();
        Assert.assertEquals(statusCode, 200, String.format("Incorrect status Actual=%s, Expected=%s", statusCode, 200));
    }

    @Test(priority = 2)
    public void findPetsByStatus() {
        given()
                .baseUri(BASE_URL)
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("accept", "application/json")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test(priority = 3)
    public void findPetsById() {
        given()
                .baseUri(BASE_URL)
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("accept", "application/json")
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test(priority = 4)
    public void updatePet() {
        String updatedBody = "{"
                + "\"id\": " + petId + ","
                + "\"category\": {"
                + "  \"id\": 1,"
                + "  \"name\": \"UpdatedDogs\""
                + "},"
                + "\"name\": \"UpdatedAdultDog\","
                + "\"photoUrls\": [],"
                + "\"tags\": ["
                + "  {\"id\": 1, \"name\": \"friendly\"}"
                + "],"
                + "\"status\": \"available\""
                + "}";

        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(updatedBody)
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        String returnedName = response.jsonPath().getString("name");
        Assert.assertEquals(returnedName, "UpdatedAdultDog",
                String.format("Incorrect name Actual=%s, Expected=%s", returnedName, "UpdatedAdultDog"));
    }

    @Test(priority = 5)
    public void deletePet() {
        given()
                .baseUri(BASE_URL)
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("accept", "application/json")
                .when()
                .delete("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        given()
                .baseUri(BASE_URL)
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("accept", "application/json")
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(404);
    }
}