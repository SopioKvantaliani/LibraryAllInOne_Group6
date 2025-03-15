Feature: As a librarian, I want to retrieve all users


  Scenario: Retrieve all users from the API endpoint

    Given I logged Library api as a "librarian"_FF
    And Accept header is "application/json"_FF
    When I send GET request to "/get_all_users" endpoint_FF
    Then status code should be 200_FF
    And Response Content type is "application/json; charset=utf-8"_FF
    And Each "id" field should not be null_FF
    And Each "name" field should not be null_FF




