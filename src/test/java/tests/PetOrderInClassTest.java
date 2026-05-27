package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrderInClassTest extends BaseTest {

   long createdOrderID;
  //  private StoreOrderDto chedPetOrder;

    //POST (Create order)
    //TEST: GET /store/order/{orderId}
    //DELETE order

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

        log.info("Placing store order with id {} for pet {}", orderId, petId);

        StoreOrderDto createdPetOrder = given()
                .spec(requestSpecification)
                .body(storeOrderDtoBuilder)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        createdOrderID = createdPetOrder.getId();

    }

    @Test
    public void getPetOrderTest() {
        StoreOrderDto fetchedPetOrder = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/order/{orderID}", createdOrderID)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        log.info("Fetched pet order: {} " + fetchedPetOrder);

    }

    @AfterMethod
    public void petOrderCleanup() {
       given()
                .spec(requestSpecification)
                .when()
                .delete("/store/order/{id}", createdOrderID)
                .then()
                .statusCode(200);

       log.info("Deleted store order {} in @AfterMethod" , createdOrderID);


       //this is just for demo purpose, as a verification
       given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("/store/order/{orderId}", createdOrderID)
                .then()
                .log().all()
                .statusCode(404);

    }

}
