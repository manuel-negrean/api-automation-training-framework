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

private long createdPetOrderId;
    @Test
    public void createPetOrder()
    {
        createdPetOrderId = System.currentTimeMillis();
        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto storeOrderDto = TestDataFactory.createOrder(createdPetOrderId, 12345, 2, shipDate, "delivered", true);
        Response response = given()
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

        StoreOrderDto createdPetOrder = response.as(StoreOrderDto.class);
        createdPetOrderId = createdPetOrder.getId();
        log.info("Created pet order :" + createdPetOrder);

        long ReturnedPetId = createdPetOrder.getPetId();
        Assert.assertEquals(ReturnedPetId, 12345, "PetId is not correct");
        int returnedQuantity = createdPetOrder.getQuantity();
        Assert.assertEquals(returnedQuantity, 2, "Quantity is not correct");
        String returnedStatus = createdPetOrder.getStatus();
        Assert.assertEquals(returnedStatus, "delivered", "Status is not correct");
    }


}
