package tests;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

@Slf4j
public class PetTests {
    private static final String BASE_URL = "https://petstore.swagger.io/v2/";
    private long petId = 557;
    @Test
    public void createPet() {
       // long petId = System.currentTimeMillis(); // Generate a unique pet ID based on the current time
        log.info("Creating a new pet with ID: " + petId);

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
                .statusCode(200)
                .extract()
                .response();

        long returnedId = response.jsonPath().getLong("id");
        Assert.assertEquals(returnedId, petId, "The returned pet ID doesn't match the created pet ID");
        String returnedName = response.jsonPath().getString("name");
        Assert.assertEquals(returnedName, "AdultDog", "The returned pet name doesn't match the created pet name");
        String returnedStatus = response.jsonPath().getString("status");
        Assert.assertEquals(returnedStatus, "available");
    }

    @Test(priority = 2)
    public void findPetsByStatus(){
        Response response = given()
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

     @Test(priority = 3)
     public void findPetsById(){
         Response response = given()
                 .baseUri(BASE_URL)
                 .relaxedHTTPSValidation()
                 .header("Accept", "application/json")
                 .when()
                 .get("/pet/" + petId)
                 .then()
                 .log().all()
                 .statusCode(200)
                 .extract()
                 .response();
     }

     @Test(priority = 4)
    public void updatePet(){
        log.info("Updating pet with ID: " + petId);

        String updatedBody = "{"
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
                 .header("Content-Type", "application/json")
                 .body(updatedBody)
                 .log().all()
                 .when()
                 .put("/pet")
                 .then()
                 .statusCode(200)
                 .extract()
                 .response();

         Assert.assertEquals(response.jsonPath().getString("name"), "UpdateAdultDog", "The pet name was not updated correctly");
     }

     @Test(priority = 5)
    public void deletePet(){
        log.info("Deleting pet with ID: " + petId);

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

        //daca nu facem response response = given nu putem face assert pe status code
        /*    Response response = given()
                    .baseUri(BASE_URL)
                    .relaxedHTTPSValidation()
                    .header("Accept", "application/json")
                    .when()
                    .get("/pet/" + petId)
                    .then()
                    .statusCode(404)
                    .extract()
                    .response();

         */
     }
}
