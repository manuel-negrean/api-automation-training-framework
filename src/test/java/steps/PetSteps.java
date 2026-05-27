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
    public void iHaveAPetWithNameAndStatus(String name, String status) {
        long id = System.currentTimeMillis();
        petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(name);
        petDto.setStatus(status);
        petDto.setPhotoUrls(java.util.Collections.emptyList()); // Assuming no photos for simplicity

    }

    @Given("the pet has category {string} with id {int}")
    public void thePetHasCategoryWithId(String categoryName, int categoryId) {
        petDto.setCategory(new PetDto.CategoryDto(categoryId, categoryName));
    }

    @Given("the pet has tag {string} with id {int}")
    public void thePetHasTagWithId(String tagName, int tagId) {
        petDto.setTags(Collections.singletonList(new PetDto.TagDto(tagId, tagName)));


    }

    @When("I create the pet via the API")
    public void iCreateThePetViaTheAPI() {
        response = given()
                .spec(Hooks.requestSpecification)
                .body(petDto)
                .log().all()
                .when()
                .post("/pet")
                .then()
                .log().all()
                .extract()
                .response();


    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedResponseCode) {
        Assert.assertEquals(response.getStatusCode(), expectedResponseCode,
                "The expected response code was " + expectedResponseCode + " but was actually " + response.getStatusCode());

    }

    @Then("the response should contain the pet with name {string}")
    public void theResponseShouldContainThePetWithName(String expectedName) {
        PetDto createdPet = response.as(PetDto.class);

        Assert.assertEquals(createdPet.getName(), expectedName,
                "The expected response name was " + expectedName + " but was actually " + createdPet.getName());
    }
}
