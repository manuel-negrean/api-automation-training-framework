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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PetOrderChallengeTest extends BaseTest {
    @Test
    public void shouldPlaceOrderWithPost() {
        long orderId = System.currentTimeMillis();
        long petId = orderId + 1000;
        String shipDate = LocalDateTime.now().toString();

//        StoreOrderDto storeOrderDto = TestDataFactory.createdOrder(
//                 999666,
//                 3456,
//                 20,
//                 shipDate,
//                 "delivered",
//                 true);

        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();
                log.info("Placing store order with id {} for pet {}", orderId, petId);

        Response response = given()
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
        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        log.info("Created Store order: " + createdOrder);

        Assert.assertEquals(storeOrderDtoBuilder.getId(),
        createdOrder.getId(),"returned order id must match the request");

        assertThat("returned order id must match the request",
                createdOrder.getId(), equalTo(storeOrderDtoBuilder.getId()));
        Assert.assertEquals(createdOrder.getPetId(), petId, "PetId is not correct.");
        Assert.assertEquals(createdOrder.getQuantity(), 2, "Quantity is not correct.");
        Assert.assertEquals(createdOrder.getStatus(), "placed", "Wrong status.");

        }
}
