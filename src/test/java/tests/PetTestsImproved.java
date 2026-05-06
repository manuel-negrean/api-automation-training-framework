package tests;

import base.BaseTest;
import dto.PetDto;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetTestsImproved extends BaseTest {

    @Test
    public void createPet(){
        long id = System.currentTimeMillis();
        PetDto petDto = TestDataFactory.createPet(id,
                "Azorel",
                new PetDto.CategoryDto(1,"CaineMare"),
                new PetDto.TagDto(1, "CatelCuminte"),"available");
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

        PetDto createdPet = response.as(PetDto.class);
        log.info("Created pet :" + createdPet);

    }

    //punem pe branch de improved


}
