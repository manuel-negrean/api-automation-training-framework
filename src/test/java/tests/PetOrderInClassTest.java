package tests;

import base.BaseTest;
import dto.StoreDto;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrderInClassTest extends BaseTest {

    long createdOrderId;

    // POST (Create order)
    // GET /store/order/{orderId}
    // DELETE order

    @BeforeMethod
    public void createPetOder() {
        long orderId = System.currentTimeMillis();
        long petId = 108108;
        String shipDate = LocalDateTime.now().toString();

        StoreDto storeDtoBuilder = StoreDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(1)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        log.info("Placing store order with {} for pet {}", orderId, petId);

        StoreDto createdPetOrder = given()
                .spec(requestSpecification)
                .body(storeDtoBuilder)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreDto.class);

        createdOrderId = createdPetOrder.getId();
        log.info("Created order with id: " + createdOrderId);
    }

    @Test
    public void getPetOrderTest() {

        StoreDto fetchedPetOrder = given()
                .spec(requestSpecification)
                .when()
                .get("/store/order/{orderId}", createdOrderId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreDto.class);

        log.info("Fetched store order: {}", fetchedPetOrder);
    }

    @AfterMethod
    public void petOrderCleanup() {
        given()
                .spec(requestSpecification)
                .when()
                .delete("store/order/{id}", createdOrderId)
                .then()
                .statusCode(200);

        log.info("Deleted store order {} in @AfterMethod", createdOrderId);

        given()
                .spec(requestSpecification)
                .when()
                .get("/store/order/{orderId}", createdOrderId)
                .then()
                .log().all()
                .statusCode(404);
    }
}
