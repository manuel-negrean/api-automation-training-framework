package tests;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetTests {
  public static final String BASE_URL = "https://petstore.swagger.io/v2";
  private long petId = System.currentTimeMillis();

  @Test(priority = 1)
    public void createPet(){

      log.info("ID = "+ petId);

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
      long returnId = response.jsonPath().getLong("id");
      Assert.assertEquals(returnId, petId,"PetId is not correct");
      String returnedName =  response.jsonPath().getString("category.name");
      Assert.assertEquals(returnedName, "Dogs", "Category name Dogs was not correct");
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
    public void findPetByID(){
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
    public void updatePet() {

        log.info("ID = " + petId);

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
                .header("Accept", "application/json")
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
        Assert.assertEquals(response.jsonPath().getString("name"), "UpdatedAdultDog", "The updated name was not UpdatedAdultDog");

    }
    @Test(priority = 5)
    public void deletePet(){
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
