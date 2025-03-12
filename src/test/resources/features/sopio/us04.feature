Feature: As a librarian, I want to create a new user
  @wip
  Scenario: Create a new user API
    Given I logged Library api as a "librarian"_SK
    And Accept header is "application/json"_SK
    And Request Content Type header is "application/x-www-form-urlencoded"_SK
    And I create a random "user" as request body_SK
    When I send POST request to "/add_user" endpoint_SK
    Then status code should be 200_SK
    And Response Content type is "application/json; charset=utf-8"_SK
    And the field value for "message" path should be equal to "The user has been created."_SK
    And "user_id" field should not be null_SK



  @db @ui @wip
  Scenario: Create a new user all layers
    Given I logged Library api as a "librarian"_SK
    And Accept header is "application/json"_SK
    And Request Content Type header is "application/x-www-form-urlencoded"_SK
    And I create a random "user" as request body_SK
    When I send POST request to "/add_user" endpoint_SK
    Then status code should be 200_SK
    And Response Content type is "application/json; charset=utf-8"_SK
    And the field value for "message" path should be equal to "The user has been created."_SK
    And "user_id" field should not be null_SK
    And created user information should match with Database_SK
    And created user should be able to login Library UI_SK
    And created user name should appear in Dashboard Page_SK
