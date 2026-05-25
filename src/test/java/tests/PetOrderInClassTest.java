package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import dto.StoreOrderDto;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrderInClassTest extends BaseTest {

    long createdOrderId;

    //POST (Create order)
    //TEST:  // GET /store/order/{orderId}
    //DELETE order

    @BeforeMethod
    public void createPetOrder() {

        long orderId = System.currentTimeMillis();
        long petId = orderId + 10000;

        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        log.info("Placing store order with id {} for pet {} ", orderId, petId);

        StoreOrderDto createdPetOrder = given()
                .spec(requestSpecification)
                .body(storeOrderDtoBuilder)
                .log().all()
                .when()
                .post("/store/order/")
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
                .extract().response()
                .as(StoreOrderDto.class);

        log.info("Created pet: " + fetchedPetOrder);

    }

    @AfterMethod
    public void petOrderCleanup() {
       given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .delete("/store/order/{id}", createdOrderId)
                .then()
                .log().all()
                .statusCode(200);

        log.info("Deleted store order {} in @AfterMethod ", createdOrderId);
        //this is just for demo purpose, as a verification
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



