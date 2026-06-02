package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.annotations.*;


import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;



public class PetOrderInClass extends BaseTest {

    long createdOrderId;

     //POST (Create Order)
    //GET  /store/order/{orderId}
    // DELETE order


    @BeforeMethod
    public void createPetOrder() {
        long orderId = System.currentTimeMillis();
        //long petId = orderId + 1000;
        long petId = 108108;
        String shipDate = LocalDateTime.now().toString();

        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipdate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        log.info("Placing store order with id {} for pet {}", orderId, petId);

        StoreOrderDto createPetOrder = given()
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

        createdOrderId = createPetOrder.getId();
        log.info("Created order with ID: " + createdOrderId);
    }
        //log.info("Order details: " + createPetOrder.toString());
        //long createOrderId = createPetOrder.getId();




    @Test

    public void getPetOrderTest() {

        StoreOrderDto fetchedPetOrder = given()
        //Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/order/{orderId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        log.info("Fetched store orfer:{}", fetchedPetOrder);
    }

        @AfterMethod
                public void petOrderCleanup() {
               given()
                       .spec(requestSpecification)
                       .when()
                       .delete("/store/order/{orderId}", createdOrderId)
                       .then()
                       .statusCode(200);
               log.info("Deleted store order {} in @AfterMethod",  createdOrderId);

               given()
                       .spec(requestSpecification)
                       .when()
                       .get("/store/order/{orderId}", createdOrderId)
                       .then()
                       .log().all()
                       .statusCode(404);

        }








    }


