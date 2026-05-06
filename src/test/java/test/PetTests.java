package test;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetTests {
    private  static final String BASE_URL = "https://petstore.swagger.io/v2";
    private long petId = 220;


    @Test
    public void createPet() {
        long petId = System.currentTimeMillis();
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
        long returnedID = response.jsonPath().getLong("id");
        Assert.assertEquals(returnedID, petId, "PetId is not correct");
        String retunedName = response.jsonPath().getString("category.name");
        Assert.assertEquals(retunedName, "Dogs", "Category name is not found");
        String returnedStatus = response.jsonPath().getString("status");
        Assert.assertEquals(returnedStatus, "available");
    }

    @Test
    public  void findPetsByStatus() {
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .queryParam("status", "available")
                .log().all()
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    public  void findPetsByID() {
        Response response = given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .queryParam("status", "available")
                .log().all()
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }
    public void updatePet() {
        long petId = System.currentTimeMillis();
        log.info("Id = " + petId);

        String updateBody = "{"
                + "\"id\": " + petId + ","
                + "\"category\": {"
                + "  \"id\": 1,"
                + "  \"name\": \"Dogs\""
                + "},"
                + "\"name\": \"UpdateAdultDog\","
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
                .body(updateBody)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertEquals(response.jsonPath().getString("name"), "UpdateAdultDog",
                "Pet name was not updated correctly");

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
                .when()
                .get("/pet/" + petId)
                .then()
                .log().all()
                .statusCode(404);
    }


}
