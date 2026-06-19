Feature: Pet API
  @createBuddy
  Scenario: Create new pet
    Given I have a pet with name "Buddy" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 10
    When I create a pet via the API
    Then the response code should be 200
    And the response should contain the pet the name "Buddy"

  @createBuddy
  Scenario: Create new pet
    Given I have a pet with name "Zdreanta" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 10
    When I create a pet via the API
    Then the response code should be 200
    And the response should contain the pet the name "Zdreanta"

  @createBuddy
  Scenario Outline: Create new pet for "<name>"
    Given I have a pet with name "<name>" and status "<status>"
    And the pet has category "<categoryName>" with id <categoryId>
    And the pet has tag "<tagName>" with id <tagId>
    When I create a pet via the API
    Then the response code should be 200
    And the response should contain the pet the name "<name>"

    Examples:
      |name    |status   |categoryName|categoryId|tagName |tagId|
      |Zdreanta|available|Dogs        |2         |friendly| 2   |
      |Rex     |available|Dogs        |2         |friendly| 2   |
      |Tommy   |available|Dogs        |2         |friendly| 2   |
      |Flex    |available|Dogs        |2         |friendly| 2   |



