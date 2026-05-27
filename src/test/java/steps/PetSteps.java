package steps;

import dto.PetDto;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class PetSteps {

    private PetDto petDto;
    private Response response;
    private long petId;

    @Given("I have a pet with name {string} and status {string}")
    public void iHaveAPetWithNameAndStatus(String name, String status){
        long id = System.currentTimeMillis();
        petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(name);
        petDto.setStatus(status);
        petDto.setPhotoUrls(Collections.EMPTY_LIST);
    }

    @Given("the pet has category {string} with id {int}")
    public void thePetHasCategoryWithId(String categoryName, int categoryId){
        petDto.setCategory(new PetDto.CategoryDto(categoryId, categoryName));
    }

    @Given("the pet has tag {string} with id {int}")
    public void thePetHasTagWithId(String tagName, int tagId){
        petDto.setTags(Collections.singletonList(new PetDto.TagDto(tagId, tagName)));
    }

    @When("I create the pet via the API")
    public void iCreateThePetViaTheAPI() {
        // Write code here that turns the phrase above into concrete actions
        response = given()
                .spec(Hooks.requestSpecification)
                .body(petDto)
                .when()
                .log().all()
                .post("/pet")
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int expectedResponseCode) {
        Assert.assertEquals(response.statusCode(), expectedResponseCode, "The expected response code was " + expectedResponseCode + " but was found " + response.statusCode());
    }

    @Then("the response should contain the pet name {string}") //se poate folosi then in loc de and
    public void theResponseShouldContainThePetName(String expectedName) {
        PetDto responsePet = response.as(PetDto.class);
        Assert.assertEquals(responsePet.getName(), expectedName, "The expected name was " + expectedName + " but was found " + responsePet.getName());

    }
}
