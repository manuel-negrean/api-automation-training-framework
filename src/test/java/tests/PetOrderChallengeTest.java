package tests;


import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
<<<<<<< Updated upstream
=======
import org.hamcrest.Matcher;
>>>>>>> Stashed changes
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes

public class PetOrderChallengeTest extends BaseTest {

    private Object storeOrderDtoBuilder;

    @Test
    public void createPetOrder() {

        long orderId = System.currentTimeMillis();
        long petId = orderId + 1000;
        String shipDate = LocalDateTime.now().toString();// ISO 8601 format
        //   StoreOrderDto storeOrderDto = TestDataFactory.createStoreOrder(id, 5555, 1, shipDate, "placed", true);
        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

<<<<<<< Updated upstream
        Response response = (Response) given()
=======
        long orderId = System.currentTimeMillis();
        long petId = orderId + 1000;
        String shipDate = LocalDateTime.now().toString();
//        StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id,3456 ,20,shipDate,"delivered" ,true);


        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();



        Response response = given()
>>>>>>> Stashed changes
                .spec(requestSpecification)
                .body(storeOrderDtoBuilder)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
<<<<<<< Updated upstream
=======


        StoreOrderDto createOrder = response.as(StoreOrderDto.class);
        log.info("Create Store Order: {} ", createOrder);

        Assert.assertEquals(storeOrderDtoBuilder.getId(), createOrder.getId(), "Returned order id must match the request");

//        assertThat("Returned order is must match the request" , createOrder.getId(), equalsTo(storeOrderDtoBuilder.getId()));
>>>>>>> Stashed changes

        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        orderId = createdOrder.getId();
        log.info("Created order with ID: " + orderId);
        Assert.assertEquals(createdOrder.getId(), orderId, "Order id is not correct");
        Assert.assertEquals(createdOrder.getPetId(), petId, "PetId is not correct");
        Assert.assertEquals(createdOrder.getStatus(), "placed", "Status is not correct");
        Assert.assertEquals(createdOrder.getQuantity(), 2, "Quantity is not correct");
        Assert.assertEquals(createdOrder.getStatus(), "placed", "Status is not correct");

        //   assertThat("Quantity is not correct", createdOrder.getQuantity(), equalTo(2));
    }


}
