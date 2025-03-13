Feature: As a user, I want to search for a specific user by their id so that
  I can quickly find the information I need.


  Scenario: Retrieve single user
    Given I logged Library api as a "librarian"_RL
    And Accept header is "application/json"_RL
    And Path param "id" is "1"_RL
    When I send GET request to "/get_user_by_id/{id}" endpoint_RL
    Then status code should be 200_RL
    And Response Content type is "application/json; charset=utf-8"_RL
    And "id" field should be same with path param_RL
    And following fields should not be null_RL
      | full_name |
      | email     |
      | password  |
