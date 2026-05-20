package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;


public class PetOrderChallengeTest extends BaseTest {
    @Test
    public void createPetOrder(){
      long id = System.currentTimeMillis();
      String shipDate = LocalDateTime.now().toString();
      int petId = 123;

      //StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id, 123, 3, shipDate, "placed", true);

      StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
              .id(id)
              .petId(petId)
              .quantity(2)
              .shipDate(shipDate)
              .status("placed")
              .complete(true)
              .build();

        Response response = given()
        .spec(requestSpecification)
        .body(storeOrderDtoBuilder)
              .log().all()
              .when()
              .post("/store/order/")
              .then()
              .log().all()
              .statusCode(200)
              .extract()
              .response();

      StoreOrderDto createOrder = response.as(StoreOrderDto.class);
      log.info("Create Store Order" + createOrder);


        Assert.assertEquals(createOrder.getPetId(), 123, "PetId is not correct");
        Assert.assertEquals(createOrder.getQuantity(), 3, "Incorrect quantity");
        Assert.assertEquals(createOrder.getStatus(), "placed", "Incorrect status");
        Assert.assertEquals(storeOrderDtoBuilder.getId(), createOrder.getId(), "Returned order id must match the request");
        //assertThat("");



    }

}
