Feature: As a user, I want to search for a specific user by their id so that I can quickly find the information I need.

  @wip
  Scenario: Retrieve single user
    Given I logged Library api as a "librarian"_SK
    And Accept header is "application/json"_SK
    And Path param "id" is "1"_SK
    When I send GET request to "/get_user_by_id/{id}" endpoint_SK
    Then status code should be 200_SK
    And Response Content type is "application/json; charset=utf-8"_SK
    And "id" field should be same with path param_SK
    And following fields should not be null_SK
      | full_name |
      | email     |
      | password  |
