Feature: As a librarian, I want to retrieve all users


  Scenario: Retrieve all users from the API endpoint

    Given I logged Library api as a "librarian"_RL
    And Accept header is "application/json"_RL
    When I send GET request to "/get_all_users" endpoint_RL
    Then status code should be 200_RL
    And Response Content type is "application/json; charset=utf-8"_RL
    And Each "id" field should not be null_RL
    And Each "name" field should not be null_RL




