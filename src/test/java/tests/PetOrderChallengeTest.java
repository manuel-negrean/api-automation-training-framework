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
    public void orderPetTest(){
        long id = System.currentTimeMillis();
        long petId = 42142;
        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto dto = StoreOrderDto.builder()
                .id(id)
                .petId(petId)
                .quantity(1)
                .shipDate(shipDate)
                .status("placed")
                .complete(false)
                .build();

        Response response = given()
                .spec(requestSpecification)
                .body(dto)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        log.info("Created order : {}", createdOrder);

        Assert.assertEquals(createdOrder.getId(), id, "Order ID is not correct");
        Assert.assertEquals(createdOrder.getPetId(), petId, "Pet ID is not correct");
        Assert.assertEquals(createdOrder.getQuantity(), 1, "Quantity is not correct");
        Assert.assertNotNull(createdOrder.getShipDate(), "Ship date is not correct");
        Assert.assertNotEquals(createdOrder.getShipDate(),"", "Ship date is not correct");
        Assert.assertEquals(createdOrder.getStatus(), "placed", "Status is not correct");
        Assert.assertFalse(createdOrder.getComplete(), "Complete flag should be false");
    }
}
