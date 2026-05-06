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
    @Test
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
                .header("Accept", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        long returnId = response.jsonPath().getLong("path");
        Assert.assertEquals(returnId, petId, "PetId is not correct");
        String returnedName = response.jsonPath().getString("category.name");
        Assert.assertEquals(returnedName, "Dogs","Category name Dogs was not found");
        String returnedStatus = response.jsonPath().getString("status");
        Assert.assertEquals(returnedStatus, "available");

    }

    public void findPetsByStatus() {
        Response response  = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }
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

    @Test
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

    @Test
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
