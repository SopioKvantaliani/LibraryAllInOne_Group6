Feature: As a librarian, I want to create a new user

  Scenario: Create a new user API
    Given I logged Library api as a "librarian"_RL
    And Accept header is "application/json"_RL
    And Request Content Type header is "application/x-www-form-urlencoded"_RL
    And I create a random "user" as request body_RL
    When I send POST request to "/add_user" endpoint_RL
    Then status code should be 200_RL
    And Response Content type is "application/json; charset=utf-8"_RL
    And the field value for "message" path should be equal to "The user has been created."_RL
    And "user_id" field should not be null_RL



  @db @ui
  Scenario: Create a new user all layers
    Given I logged Library api as a "librarian"_RL
    And Accept header is "application/json"_RL
    And Request Content Type header is "application/x-www-form-urlencoded"_RL
    And I create a random "user" as request body_RL
    When I send POST request to "/add_user" endpoint_RL
    Then status code should be 200_RL
    And Response Content type is "application/json; charset=utf-8"_RL
    And the field value for "message" path should be equal to "The user has been created."_RL
    And "user_id" field should not be null_RL
    And created user information should match with Database_RL
    And created user should be able to login Library UI_RL
    And created user name should appear in Dashboard Page_RL
