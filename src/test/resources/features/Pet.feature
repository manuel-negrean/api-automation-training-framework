Feature: Pet API

  @createBuddy
  Scenario: Create a new pet
    Given I have a pet with name "Buddy" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has a tag "friendly" with id 1
    When I create the pet via the API
    Then the response status code should be 200
    And the response should contain the pet with name "Buddy"
    And the pet should have the status "available"
    And the pet should have category "Dogs" with id 1
    And the pet should have a tag "friendly" with id 1

  @createZdreanta
  Scenario: Create a new pet
    Given I have a pet with name "Zdreanta" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has a tag "friendly" with id 1
    When I create the pet via the API
    Then the response status code should be 200
    And the response should contain the pet with name "Zdreanta"
    And the pet should have the status "available"
    And the pet should have category "Dogs" with id 1
    And the pet should have a tag "friendly" with id 1

  @createMultiPets
  Scenario Outline: Create a new pet
    Given I have a pet with name "<name>" and status "<status>"
    And the pet has category "<categoryName>" with id <categoryId>
    And the pet has a tag "<tagName>" with id <tagId>
    When I create the pet via the API
    Then the response status code should be 200
    And the response should contain the pet with name "<name>"
    And the pet should have the status "<status>"
    And the pet should have category "<categoryName>" with id <categoryId>
    And the pet should have a tag "<tagName>" with id <tagId>
    Examples:
    |name     |status   |categoryName|categoryId|tagName |tagId|
    |Zdreanta |available|Dogs        |1         |friendly|1    |
    |Buddy    |available|Dogs        |1         |friendly|1    |
    |Charlie  |available|Cat         |2         |friendly|1    |



