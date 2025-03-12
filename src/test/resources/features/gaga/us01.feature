
Feature: As a librarian, I want to retrieve all users

  @first
  Scenario: Retrieve all users from the API endpoint

    Given I logged Library api as a "librarian"_DN
    And Accept header is "application/json"_DN
    When I send GET request to "/get_all_users" endpoint_DN
    Then status code should be 200_DN
    And Response Content type is "application/json; charset=utf-8"_DN
    And Each "id" field should not be null_DN
    And Each "name" field should not be null_DN




