Feature: : As a user, I want to view my own user information using the API
  so that I can see what information is stored about me


  Scenario Outline: Decode User
    Given I logged Library api with credentials "<email>" and "<password>"_FF
    And Accept header is "application/json"_FF
    And Request Content Type header is "application/x-www-form-urlencoded"_FF
    And I send "token" information as request body_FF
    When I send POST request to "/decode" endpoint_FF
    Then status code should be 200_FF
    And Response Content type is "application/json; charset=utf-8"_FF
    And the field value for "user_group_id" path should be equal to "<user_group_id>"_FF
    And the field value for "email" path should be equal to "<email>"_FF
    And "full_name" field should not be null_FF
    And "id" field should not be null_FF


    Examples:
      | email               | password    | user_group_id |
      | student5@library    | libraryUser | 3             |
      | librarian10@library | libraryUser | 2             |
      | student10@library   | libraryUser | 3             |
      | librarian13@library | libraryUser | 2             |
