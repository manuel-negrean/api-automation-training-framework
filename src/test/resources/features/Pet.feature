Feature: Pet API
  @createBuddy

    Scenario: Create new pet
    Given I have a pet with name "Buddy" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 1
    When I create the pet via the Api
    Then the response status code should be 200
    And the response should contain the pet name "Buddy"

  @createZdreanta
    Scenario: Create new pet
    Given I have a pet with name "Zdreanta" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 1
    When I create the pet via the Api
    Then the response status code should be 200
    And the response should contain the pet name "Zdreanta"

  @createBuddy
  Scenario Outline: Create new pet for "<name>"
    Given I have a pet with name "<name>" and status "<status>"
    And the pet has category "<categoryName>" with id <categoryId>
    And the pet has tag "<tagName>" with id <tagId>
    When I create the pet via the Api
    Then the response status code should be 200
    And the response should contain the pet name "<name>"

    Examples:
    |name|status|categoryName|categoryId|tagName|tagId|
    |Zdreanta|available|Dogs|2|friendly|2|
    |Max|available|Dogs|2|friendly|2|
    |Leo|available|Dogs|2|friendly|2|
    |Pixy|available|Dogs|2|friendly|2|