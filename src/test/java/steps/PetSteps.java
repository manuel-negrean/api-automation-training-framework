package steps;

import dto.PetDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.Collections;

import static io.restassured.RestAssured.given;

@Slf4j
public class PetSteps {
    private PetDto petDto;
    private Response response;
    private long petId;

    @Given("I have a pet with name {string} and status {string}")
    public void iHaveAPetWithNameAndStatus(String name, String status) {
        log.info("Creating a pet with name: {} and status: {}", name, status);
        petId = System.currentTimeMillis();
        petDto = new PetDto();
        petDto.setId(petId);
        petDto.setName(name);
        petDto.setStatus(status);
        petDto.setPhotoUrls(Collections.emptyList());

    }

    @And("the pet has category {string} with id {int}")
    public void thePetHasCategoryWithId(String categoryName, int categoryId) {
       petDto.setCategory(new PetDto.CategoryDto(categoryId, categoryName));
    }

    @And("the pet has a tag {string} with id {int}")
    public void thePetHasATagWithId(String tagName, int tagId) {
        petDto.setTags(Collections.singletonList(new PetDto.TagDto(tagId, tagName)));
    }

    @When("I create the pet via the API")
    public void iCreateThePetViaTheAPI() {
        response = given()
                .spec(Hooks.requestSpecification)
                .body(petDto)
                .when()
                .post("/pet")
                .then()
                .extract()
                .response();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int responseCode) {
        Assert.assertEquals(response.statusCode(),responseCode);
    }

    @Then("the response should contain the pet with name {string}")
    public void theResponseShouldContainThePetWithName(String name) {
       PetDto responseDTO = response.as(PetDto.class);
       Assert.assertEquals(responseDTO.getName(),name);
    }

    @Then("the pet should have the status {string}")
    public void theResponseShouldContainThePetWithStatus(String status) {
        PetDto responseDTO = response.as(PetDto.class);
        Assert.assertEquals(responseDTO.getStatus(),status);
    }
    @Then("the pet should have category {string} with id {int}")
    public void thePetShouldHaveCategoryWithId(String category, int categoryId) {
        PetDto responseDTO = response.as(PetDto.class);
        Assert.assertEquals(responseDTO.getCategory().getName(),category);
        Assert.assertEquals(responseDTO.getCategory().getId(),categoryId);
    }

    @And("the pet should have a tag {string} with id {int}")
    public void thePetShouldHaveATagWithId(String tagName, int tagId) {
        PetDto responseDTO = response.as(PetDto.class);
        Assert.assertEquals(responseDTO.getTags().get(0).getName(), tagName);
        Assert.assertEquals(responseDTO.getTags().get(0).getId(), tagId);
    }
}
