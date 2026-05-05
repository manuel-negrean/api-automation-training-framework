package tests;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetTests {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private long petId = 42142222;

    @Test(priority = 1)
    public void createPet(){
        // just for testing
        //long petId = System.currentTimeMillis();
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
                .header("Accept" , "application/json")
                .header("User-Agent" , "insomnia/11.1.0")
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
        Assert.assertEquals(returnedId, petId, "PetID is not correct");
        String returnedName = response.jsonPath().getString("category.name");
        Assert.assertEquals(returnedName, "Dogs", "Category name Dogs was not found");
        String returnedStatus = response.jsonPath().getString("status");
        Assert.assertEquals(returnedStatus, "available");

    }
    @Test(priority = 2)
    public void findPetsByStatus() {
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept" , "application/json")
                .header("User-Agent" , "insomnia/11.1.0")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }
    @Test(priority = 2)
    public void findPetsByID() {
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept" , "application/json")
                .header("User-Agent" , "insomnia/11.1.0")
                .header("Content-Type", "application/json")
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test(priority = 2)
    public void updatePet() {
        log.info("Id = " + petId);

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
                .header("Accept" , "application/json")
                .header("User-Agent" , "insomnia/11.1.0")
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

        Assert.assertEquals(response.jsonPath().getString("name"), "UpdatedAdultDog",
                "The updated name is UpdatedAdultDog");
    }

    @Test(priority = 3)
    public void deletePet() {
    given()
            .baseUri(BASE_URL)
            .relaxedHTTPSValidation()
            .header("Accept" , "application/json")
            .header("User-Agent" , "insomnia/11.1.0")
            .log().all()
            .when()
            .delete("/pet/" + petId)
            .then()
            .log().all()
            .statusCode(200);

        given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept" , "application/json")
                .header("User-Agent" , "insomnia/11.1.0")
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(404);

    }
}
