Feature: Pet API
  @create
  Scenario: Create new pet
    Given I have a pet with name "Buddy" and status "available"
    And The pet has category "Dogs" with id 1
    And The pet has tag "friendly" with id 1
    When I create the pet via the API
    Then The response code should be 200
    And The response should contain the pet name "Buddy"

  @create
  Scenario: Create new pet
    Given I have a pet with name "Max" and status "available"
    And The pet has category "Dogs" with id 1
    And The pet has tag "friendly" with id 1
    When I create the pet via the API
    Then The response code should be 200
    And The response should contain the pet name "Max"

  @create
  Scenario Outline: Create new pet for "<name>"
    Given I have a pet with name "<name>" and status "<status>>"
    And The pet has category "<categoryName>" with id <categoryId>
    And The pet has tag "<tagName>" with id <tagId>
    When I create the pet via the API
    Then The response code should be 200
    And The response should contain the pet name "<name>"

    Examples:
      | name   | status    | categoryName | categoryId | tagName  | tagId |
      | Buddy  | available | Dogs         | 1          | friendly | 1     |
      | Azorel | available | Dogs         | 1          | friendly | 2     |
      | Catty  | available | Cats         | 2          | friendly | 3     |