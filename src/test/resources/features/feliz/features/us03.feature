Feature: As a librarian, I want to create a new book


  Scenario: Create a new book API
    Given I logged Library api as a "librarian"_FF
    And Accept header is "application/json"_FF
    And Request Content Type header is "application/x-www-form-urlencoded"_FF
    And I create a random "book" as request body_FF
    When I send POST request to "/add_book" endpoint_FF
    Then status code should be 200_FF
    And Response Content type is "application/json; charset=utf-8"_FF
    And the field value for "message" path should be equal to "The book has been created."_FF
    And "book_id" field should not be null_FF



 @ui @db
  Scenario: Create a new book all layers
    Given I logged Library api as a "librarian"_FF
    And Accept header is "application/json"_FF
    And Request Content Type header is "application/x-www-form-urlencoded"_FF
    And I create a random "book" as request body_FF
    And I logged in Library UI as "librarian"_FF
    And I navigate to "Books" page_FF
    When I send POST request to "/add_book" endpoint_FF
    Then status code should be 200_FF
    And Response Content type is "application/json; charset=utf-8"_FF
    And the field value for "message" path should be equal to "The book has been created."_FF
    And "book_id" field should not be null_FF
    And UI, Database and API created book information must match_FF