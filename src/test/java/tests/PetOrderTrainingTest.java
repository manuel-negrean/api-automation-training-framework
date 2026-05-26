package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PetOrderTrainingTest extends BaseTest {
    // POST (Create Order)
    //TEST: Get /store/order/{orderId} - find purchase order by ID
    // DELETE /store/order/{orderId} - delete purchase order by ID
    long createdOrderId;

    @BeforeMethod
    public void createPetOrder() {
        long orderId = System.currentTimeMillis();
        long petId = orderId + 1000;
        String shipDate = "2024-06-01T12:00:00.000Z"; // ISO 8601 format

        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        log.info("Creating order with ID: " + orderId);
        StoreOrderDto createdPetOrder = given()
                .spec(requestSpecification)
                .body(storeOrderDtoBuilder)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        createdOrderId = createdPetOrder.getId();
    }


    @Test
    public void getPetOrderTest() {
        StoreOrderDto fetchedPetOrder = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/order/{orderId}", createdOrderId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        log.info("Fetched order: {}", fetchedPetOrder);
    }

    @AfterMethod
    public void petOrderCleanup() {
        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/store/order/{orderId}", createdOrderId)
                .then()
                .log().all()
                .statusCode(200);
        log.info("Deleted order with ID: " + createdOrderId);

        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/order/{orderId}", createdOrderId)
                .then()
                .log().all()
                .statusCode(404);

    }

}
