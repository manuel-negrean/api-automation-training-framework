package tests;

import base.BaseTest;
import dto.PetDto;
import dto.StoreOrderDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class PetOrderChallengeTest extends BaseTest {
    @Test

    public void createPetOrder(){

        long id = System.currentTimeMillis();
        String shipDate = LocalDateTime.now().toString();
        StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id,3456 ,20,shipDate,"delivered" ,true);

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
        StoreOrderDto createOrder = response.as(StoreOrderDto.class);
        log.info("Create Store Order:" + createOrder);




    }


}
