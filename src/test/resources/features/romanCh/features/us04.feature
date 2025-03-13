Feature: As a librarian, I want to create a new user
  @romanCh
  Scenario: Create a new user API
    Given I logged Library api as a "librarian"_RCH
    And Accept header is "application/json"_RCH
    And Request Content Type header is "application/x-www-form-urlencoded"_RCH
    And I create a random "user" as request body_RCH
    When I send POST request to "/add_user" endpoint_RCH
    Then status code should be 200_RCH
    And Response Content type is "application/json; charset=utf-8"_RCH
    And the field value for "message" path should be equal to "The user has been created."_RCH
    And "user_id" field should not be null_RCH



  @romanCh @db @ui
  Scenario: Create a new user all layers
    Given I logged Library api as a "librarian"_RCH
    And Accept header is "application/json"_RCH
    And Request Content Type header is "application/x-www-form-urlencoded"_RCH
    And I create a random "user" as request body_RCH
    When I send POST request to "/add_user" endpoint_RCH
    Then status code should be 200_RCH
    And Response Content type is "application/json; charset=utf-8"_RCH
    And the field value for "message" path should be equal to "The user has been created."_RCH
    And "user_id" field should not be null_RCH
    And created user information should match with Database_RCH
    And created user should be able to login Library UI_RCH
    And created user name should appear in Dashboard Page_RCH
