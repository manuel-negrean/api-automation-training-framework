package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrder extends BaseTest {

    long createOrderId;

    //POST (CREATE Order)
    // Test : GET /store/order/{order ID}
    //DELETE


    @BeforeMethod
    public void createPetOrder(){
        long orderId = System.currentTimeMillis();
        long petId = orderId + 1000;

        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();
        log.info(("Placing store order with id {} for pet {} "), orderId , petId);

        StoreOrderDto createPetOrder  = given()
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

        long createOrderId = createPetOrder.getId();


    }
@Test
    public void getPetOrderTest(){

        StoreOrderDto fetchedPetOrder = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("store/order/{orderId}" , createOrderId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);
        log.info("Fetch store order: {} ", fetchedPetOrder);

    }
    @AfterMethod
    public void petOrderCleanUp(){
        given()
                .spec(requestSpecification)
                .when()
                .delete("/store/order/{id)",createOrderId)
                .then()
                .statusCode(200)
                .extract().response();

        log.info("deleted store order {} in @AfterMethod", createOrderId);
    }

}




