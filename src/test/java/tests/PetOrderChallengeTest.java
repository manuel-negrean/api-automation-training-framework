package tests;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;

import base.BaseTest;
import dto.PetDto;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.time.LocalDateTime;

public class PetOrderChallengeTest extends BaseTest {
    @Test
    public void createPetOrder() {

        long id = System.currentTimeMillis();
        long petId = 108108;

        //long petId = orderId + 1000


        String shipDate = LocalDateTime.now().toString();
        // StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id, 108108, 20, "", "delivered", true);

        Response response = given()
                //.spec(requestSpecification).body(storeOrderDto)            //  .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        //PetDto createPetOrder = response.as(PetDto.class);


        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(id)
                .petId(petId)
                .quantity(2)
                .shipdate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        log.info("Created order with ID: " + createdOrder);
        //log.info("Order details: " + createdOrder.toString());

       Assert.assertEquals(createdOrder.getPetId(), petId, "PetId is not correct.");

        // Additional assertions can be added here to verify the order details
        //long returnedPetId = response.jsonPath().getLong("petId");
        //long returnedPetId = createdOrder.getPetId();
       // Assert.assertEquals(createdOrder.getPetId(), petId, "PetId is not correct.");
       // Assert.assertEquals(createdOrder.getQuantity(), 20, "Incorrect quantity ordered.");
        //String returnedStatus = response.jsonPath().getString("status");
        //Assert.assertEquals(createdOrder.getStatus(), "delivered", "Wrong status.");


    }
}

     /*

    Challenge: Create a test that creates a pet, then creates an order for that pet, and finally verifies that the order was created successfully.

     Steps:
     1. Create a pet using the POST /pet endpoint.
     2. Create an order for that pet using the POST /store/order endpoint.
     3. Verify that the order was created successfully by checking the response status code and the order details.

     */



