package tests;

import base.BaseTest;
import dto.PetDto;
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
    long petId = 13;
     long id = System.currentTimeMillis();
     String shipDate = LocalDateTime.now().toString();
     StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id,petId,2, shipDate, "delivered", true);

     Response response = given()
             .spec(requestSpecification)
             .body(storeOrderDto)
             .log().all()
             .when()
             .post("/store/order/")
             .then()
             .log().all()
             .statusCode(200)
             .extract()
             .response();

        StoreOrderDto createdStoreOrder = response.as(StoreOrderDto.class);
        log.info ("Created order: " + createdStoreOrder);

        long returnOrderId = createdStoreOrder.getPetId();
        Assert.assertEquals(createdStoreOrder.getPetId(), petId, "PetId is not correct.");
        Assert.assertEquals(createdStoreOrder.getQuantity(), 2, "Incorrect quantity ordered.");
        Assert.assertEquals(createdStoreOrder.getStatus(), "delivered", "Wrong status.");
    }


}
