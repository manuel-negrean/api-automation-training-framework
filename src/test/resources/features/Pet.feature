Feature: Pet API

  @createAzorel
  Scenario: Create new pet
    Given I have a pet with name "Azorel" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 1
    When I create the pet via the API
    Then the response code should be 200
    And the response should contain the pet with name "Azorel"

    @createBuddy
  Scenario: Create new pet
    Given I have a pet with name "Buddy" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 1
    When I create the pet via the API
    Then the response code should be 200
    And the response should contain the pet with name "Buddy"

  @createBuddy
  Scenario Outline: Create new pet for "<name>"
    Given I have a pet with name "<name>" and status "<status>"
    And the pet has category "<categoryName>" with id <categoryId>
    And the pet has tag "<tagName>" with id <tagId>
    When I create the pet via the API
    Then the response code should be 200
    And the response should contain the pet with name "<name>"

    Examples:

    | name   | status    | categoryName | categoryId | tagName | tagId |
    | Azorel | available | Dogs | 2 | friendly | 1 |
    | Test1 | available | Dogs | 2 | friendly | 1 |
    | Test2 | available | Dogs | 2 | friendly | 1 |
    | Test3 | available | Dogs | 2 | friendly | 1 |
