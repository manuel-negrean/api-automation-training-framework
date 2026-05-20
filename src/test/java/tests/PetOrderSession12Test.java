package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrderSession12Test extends BaseTest {

    long createOrderId;
    //POST create order
    // Test: GET/store/order/{orderId}
    // DELETE order
    @BeforeMethod
    public void createPetOrder() {

        long orderID = System.currentTimeMillis();
        long petID = orderID + 1000;

        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderID)
                .petId(petID)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        log.info("Placing store order with id {} for pet {}", orderID, petID);

        StoreOrderDto createPetOrder = given()
                .spec(requestSpecification)
                .body(storeOrderDtoBuilder)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        createOrderId = createPetOrder.getId();

    }

    @Test
    public void getPetOrderTest() {
        StoreOrderDto fetchPetOrder = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/order/{orderId}", createOrderId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        log.info("Fetched store order: {}", fetchPetOrder);

    }
    // delete order
    @AfterMethod
    public void petOrderCleanup() {
        given()
                .spec(requestSpecification)
                .when()
                .delete("/store/order/{id}", createOrderId)
                .then()
                .statusCode(200);

        log.info("Deleted store order with id: {}", createOrderId);

        // pt verificare

        given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/order/{orderId}", createOrderId)
                .then()
                .log().all()
                .statusCode(404);

    }


}
