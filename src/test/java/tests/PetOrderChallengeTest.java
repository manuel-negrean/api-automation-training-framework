package tests;


import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;


public class PetOrderChallengeTest extends BaseTest {

    @Test
    public void createPetOrder() {
        // Implement the test to create a pet order
        long orderId;
        long id = System.currentTimeMillis();
        String shipDate = LocalDateTime.now().toString();// ISO 8601 format
        StoreOrderDto storeOrderDto = TestDataFactory.createStoreOrder(id, 5555, 1, shipDate, "placed", true);

        Response response = (Response) given()
                .spec(requestSpecification)
                .body(storeOrderDto)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        orderId = createdOrder.getId();
        log.info("Created order with ID: " + orderId);
        Assert.assertEquals(createdOrder.getId(), id, "Order id is not correct");
        Assert.assertEquals(createdOrder.getPetId(),444447);
    }


}
