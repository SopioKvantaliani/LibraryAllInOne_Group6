Feature: As a user, I want to search for a specific user by their id so that I can quickly find the information I need.


  Scenario: Retrieve single user
    Given I logged Library api as a "librarian"_FF
    And Accept header is "application/json"_FF
    And Path param "id" is "1"_FF
    When I send GET request to "/get_user_by_id/{id}" endpoint_FF
    Then status code should be 200_FF
    And Response Content type is "application/json; charset=utf-8"_FF
    And "id" field should be same with path param_FF
    And following fields should not be null_FF
      | full_name |
      | email     |
      | password  |
