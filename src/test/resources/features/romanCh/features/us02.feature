Feature: As a user, I want to search for a specific user by their id so that I can quickly find the information I need.

  @romanCh
  Scenario: Retrieve single user
    Given I logged Library api as a "librarian"_RCH
    And Accept header is "application/json"_RCH
    And Path param "id" is "1"_RCH
    When I send GET request to "/get_user_by_id/{id}" endpoint_RCH
    Then status code should be 200_RCH
    And Response Content type is "application/json; charset=utf-8"_RCH
    And "id" field should be same with path param_RCH
    And following fields should not be null_RCH
      | full_name |
      | email     |
      | password  |
