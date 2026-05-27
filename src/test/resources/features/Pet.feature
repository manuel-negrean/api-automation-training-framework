Feature: Pet API

  @create
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
        | name     | status    | categoryName  | categoryId | tagName       | tagId |
        | Patrocle | available | Dogs      | 1          | friendly  | 1     |
        | Goji     | available | Dogs      | 1          | friendly  | 1     |
        | Mitzike  | available | Dogs      | 1          | friendly  | 1     |
        | Marian   | available | Dogs      | 1          | friendly  | 1     |
