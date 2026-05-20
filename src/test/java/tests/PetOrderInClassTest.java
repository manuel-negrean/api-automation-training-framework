package tests;

import base.BaseTest;
import dto.StoreOrderDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.restassured.path.xml.XmlPath.given;

public class PetOrderInClassTest extends BaseTest {
    long createOrderId;

    //POST (Create order)
    //TEST: GET /store/order/{orderId}
    //DELETE order
    @BeforeMethod
    public void createPetOrder(){
        long id = System.currentTimeMillis();
        String shipDate = LocalDateTime.now().toString();
        int petId = 123;

        //StoreOrderDto storeOrderDto = TestDataFactory.createOrder(id, 123, 3, shipDate, "placed", true);

        StoreOrderDto storeOrderDtoBuilder = StoreOrderDto.builder()
                .id(id)
                .petId(petId)
                .quantity(3)
                .shipDate(shipDate)
                .status("placed")
                .complete(true)
                .build();

        StoreOrderDto createPetOrder = RestAssured.given()
                .spec(requestSpecification)
                .body(storeOrderDtoBuilder)
                .log().all()
                .when()
                .post("/store/order/")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        createOrderId = createPetOrder.getId();
    }

    @Test
    public void getPetOrderTest(){

        StoreOrderDto fetchPetOrder = RestAssured.given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get("/store/order/{orderId}", createOrderId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response()
                .as(StoreOrderDto.class);

        log.info("Fetched store order: {}", fetchPetOrder);
    }

    @AfterMethod
    public void petOrderCleanuo(){
         RestAssured.given()
                .spec(requestSpecification)
                .when()
                .delete("/store/order/{id}", createOrderId)
                .then()
                .statusCode(200);

        log.info("Deleted store order: {}", createOrderId);
    }

}
