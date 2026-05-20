package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrderInClassTest extends BaseTest {

    Long createdOrderId;

    //POST (Create order)
    //TEST: GET /store/order/{orderId}
    //DELETE orderId
    @BeforeMethod
    public void createPetOrder() {
        long id = System.currentTimeMillis();
        long petId = 42142;
        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto dto = StoreOrderDto.builder()
                .id(id)
                .petId(petId)
                .quantity(1)
                .shipDate(shipDate)
                .status("placed")
                .complete(false)
                .build();

        Response response = given()
                .spec(requestSpecification)
                .body(dto)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        log.info("Created order : {}", createdOrder);
        createdOrderId = createdOrder.getId();
    }

    @Test
    public void getPetOrderTest() {
        Response response = given()
                .spec(requestSpecification)
                .pathParam("orderId", createdOrderId)
                .log().all()
                .when()
                .get("/store/order/{orderId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @AfterMethod
    public void deletePetOrder() {
        given()
                .spec(requestSpecification)
                .pathParam("orderId", createdOrderId)
                .log().all()
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .log().all()
                .statusCode(200);
        given()
                .spec(requestSpecification)
                .pathParam("orderId", createdOrderId)
                .log().all()
                .when()
                .get("/store/order/{orderId}")
                .then()
                .log().all()
                .statusCode(404);
    }
}