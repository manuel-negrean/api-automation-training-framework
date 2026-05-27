Feature: Pet API
  @createLeo
  Scenario: Create new pet
    Given I have a pet with name "Leo" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 1
    When I create the pet via the API
    Then the response status code should be 200
    And the response should contain the pet with name "Leo"

  @createFelix
  Scenario: Create new pet
    Given I have a pet with name "Felix" and status "available"
    And the pet has category "Dogs" with id 1
    And the pet has tag "friendly" with id 1
    When I create the pet via the API
    Then the response status code should be 200
    And the response should contain the pet with name "Felix"



