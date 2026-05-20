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
    public void createOrder() {
        long id = System.currentTimeMillis();
        long orderID = System.currentTimeMillis();
        long petID = orderID + 1000;
        String shipDate = LocalDateTime.now().toString();
      //  StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id, 234, 15,shipDate, "placed", false);

        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(orderID)
                .petId(petID)
                .quantity(2)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        log.info("Placing store order with id {} for pet {}", orderID, petID);

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
        StoreOrderDto createPetOrder = response.as(StoreOrderDto.class);
        log.info("Create pet order:" + createPetOrder);

        assertThat("Returned order id must match the request",
                createPetOrder.getId() , equalTo(orderID));
        assertThat("Returned pet id must match the request",
                createPetOrder.getPetId() , equalTo(petID));
        assertThat("Returned quantity must match the request",
                createPetOrder.getQuantity(), equalTo(2));
        assertThat("Returned status must match the request",
                createPetOrder.getStatus(), equalTo("placed"));
        assertThat("Returned complete flag must match the request",
                createPetOrder.isComplete(), equalTo(true));
        assertThat("Returned petId must match the request",
                createPetOrder.getPetId(), equalTo(petID));




    }

}
