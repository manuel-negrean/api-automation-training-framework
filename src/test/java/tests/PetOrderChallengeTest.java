package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import dto.StoreOrderDto;
import utils.TestDataFactory;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class PetOrderChallengeTest extends BaseTest {

    @Test
    public void createdOrder() {
        long orderId = System.currentTimeMillis();
        long petId = orderId + 10000;
        String shipDate = LocalDateTime.now().toString();


//        StoreOrderDto storeOrderDto = TestDataFactory.createOrder(orderId,3456, 15, "", "delivered", true);


        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder().id(orderId)
                .id(orderId)
                .petId(petId)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        log.info("Placing store order with id {} for pet {} ", orderId, petId);


        Response response = given()
                .spec(requestSpecification)
                .body(storeOrderDtoBuilder)
                .log().all()
                .when()
                .post("store/order")
                .then()
                .log().all()
                .statusCode(200 )
                .extract()
                .response();

        StoreOrderDto createdOrder = response.as(StoreOrderDto.class);
        log.info("Created store order : {} ", createdOrder);

        Assert.assertEquals(storeOrderDtoBuilder.getId(), createdOrder.getId(),
                createdOrder.getId(), "Returned order id must match the request");


        Assert.assertEquals(createdOrder.getPetId(), 3456, "PetId is not correct.");
        Assert.assertEquals(createdOrder.getQuantity(), 15, "Incorrect quantity ordered.");
        Assert.assertEquals(createdOrder.getStatus(), "delivered", "Wrong status.");


    }

    @Test
    public void getAllInventory(){
        Response response = given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/inventory")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        StoreOrderDto getAllInventory = response.as(StoreOrderDto.class);
        log.info("All Store orders are" + getAllInventory);

    }

}
