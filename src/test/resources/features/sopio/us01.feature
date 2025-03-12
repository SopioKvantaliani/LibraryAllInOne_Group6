Feature: As a librarian, I want to retrieve all users

  @wip
  Scenario: Retrieve all users from the API endpoint

    Given I logged Library api as a "librarian"_SK
    And Accept header is "application/json"_SK
    When I send GET request to "/get_all_users" endpoint_SK
    Then status code should be 200_SK
    And Response Content type is "application/json; charset=utf-8"_SK
    And Each "id" field should not be null_SK
    And Each "name" field should not be null_SK




