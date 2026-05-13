package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.StoreOrderDto;
import utils.TestDataFactory;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrderChallengeTest extends BaseTest {

    @Test
    public void createdOrder() {
        long id = System.currentTimeMillis();
        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id,3456, 15, "", "delivered", true);
        Response response = given()
                .spec(requestSpecification)
                .body(storeOrderDto)
                .log().all()
                .when()
                .post("store/order")
                .then()
                .log().all()
                .statusCode(200 )
                .extract()
                .response();

        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        log.info("Create Store Order" + createdOrder);

        Assert.assertEquals(createdOrder.getPetId(), 3456, "PetId is not correct.");
        Assert.assertEquals(createdOrder.getQuantity(), 15, "Incorrect quantity ordered.");
        Assert.assertEquals(createdOrder.getStatus(), "delivered", "Wrong status.");
    }

}
