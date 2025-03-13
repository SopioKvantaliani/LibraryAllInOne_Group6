Feature: As a librarian, I want to retrieve all users

  @romanCh
  Scenario: Retrieve all users from the API endpoint

    Given I logged Library api as a "librarian"_RCH
    And Accept header is "application/json"_RCH
    When I send GET request to "/get_all_users" endpoint_RCH
    Then status code should be 200_RCH
    And Response Content type is "application/json; charset=utf-8"_RCH
    And Each "id" field should not be null_RCH
    And Each "name" field should not be null_RCH




