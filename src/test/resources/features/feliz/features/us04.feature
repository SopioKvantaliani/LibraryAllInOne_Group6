Feature: As a librarian, I want to create a new user
  Scenario: Create a new user API
    Given I logged Library api as a "librarian"_FF
    And Accept header is "application/json"_FF
    And Request Content Type header is "application/x-www-form-urlencoded"_FF
    And I create a random "user" as request body_FF
    When I send POST request to "/add_user" endpoint_FF
    Then status code should be 200_FF
    And Response Content type is "application/json; charset=utf-8"_FF
    And the field value for "message" path should be equal to "The user has been created."_FF
    And "user_id" field should not be null_FF



  @db @ui
  Scenario: Create a new user all layers
    Given I logged Library api as a "librarian"_FF
    And Accept header is "application/json"_FF
    And Request Content Type header is "application/x-www-form-urlencoded"_FF
    And I create a random "user" as request body_FF
    When I send POST request to "/add_user" endpoint_FF
    Then status code should be 200_FF
    And Response Content type is "application/json; charset=utf-8"_FF
    And the field value for "message" path should be equal to "The user has been created."_FF
    And "user_id" field should not be null_FF
    And created user information should match with Database_FF
    And created user should be able to login Library UI_FF
    And created user name should appear in Dashboard Page_FF
