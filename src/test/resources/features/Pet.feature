Feature: Pet API

  @createBuddy
  Scenario: Create new pet
    Given I have a pet with name "Buddy" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 10
    When I create the pet via the API
    Then the response code should be 200
    And the response should contain the pet name "Buddy"


  @createBuddy
  Scenario: Create new pet
    Given I have a pet with name "Grivei" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 10
    When I create the pet via the API
    Then the response code should be 200
    And the response should contain the pet name "Grivei"

  @createBuddy
  Scenario: Create new pet
    Given I have a pet with name "Zdreanta" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 10
    When I create the pet via the API
    Then the response code should be 200
    And the response should contain the pet name "Zdreanta"

  @createBuddy
  Scenario Outline: Create new pet for "<name>"
    Given I have a pet with name "<name>" and status "<status>"
    And the pet has category "<categoryName>" with id <categoryID>
    And the pet has tag "<tagName" with id 10
    When I create the pet via the API
    Then the response code should be 200
    And the response should contain the pet name "<name>"

    Examples:
      | name     | status    | categoryName | categoryID | tagName  | tagID |
      | Grivelut | available | Dogs         | 2          | friendly | 2     |
