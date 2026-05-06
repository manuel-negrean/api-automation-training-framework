package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@Slf4j
public class PetTestsImproved extends BaseTest {


    @Test
    public void createPet(){
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(id ,
                "Azorel" , new PetDto.CategoryDto(1,
                        "CaineMare") ,new PetDto.TagDto(1,"cuminte"),
                "availabe");
        Response response = given()
                .spec(requestSpecification)
                .body(petDto)
                .log().all()
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        PetDto createPet =  response.as(PetDto.class);
        log.info("Create pet: " + createPet);



    }
}
