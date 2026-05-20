package tests;

import base.BaseTest;
import dto.StoreDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class PetOrderChallengeTest extends BaseTest {

    @Test
    public void createOrder() {
        long id = System.currentTimeMillis();
        long petId = 108108;
        String shipDate = LocalDateTime.now().toString();

        StoreDto storeDtoBuilder = StoreDto.builder()
                .id(id)
                .petId(petId)
                .quantity(1)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();
//        StoreDto storeDto = TestDataFactory.createOrderBody(
//                id,
//                petId,
//                1,
//                shipDate,
//                "placed",
//                true
//        );
        Response response = given()
                .spec(requestSpecification)
                .body(storeDtoBuilder)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        StoreDto createdOrder = response.as(StoreDto.class);
        log.info("Created order: " + createdOrder);

        assertThat("Order ID is not correct.", createdOrder.getId(), equalTo(storeDtoBuilder.getId()));
        Assert.assertEquals(createdOrder.getPetId(), petId, "PetId is not correct.");
        Assert.assertEquals(createdOrder.getQuantity(), 1, "Incorrect quantity ordered.");
        Assert.assertEquals(createdOrder.getStatus(), "placed", "Wrong status.");
        assertThat("Order completion status is not correct.", createdOrder.isComplete(), equalTo(true));
        assertThat("Ship date is not correct.", createdOrder.getShipDate(), not(blankOrNullString()));
    }
}
