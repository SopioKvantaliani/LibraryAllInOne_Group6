Feature: As a librarian, I want to create a new book

  @wip
  Scenario: Create a new book API
    Given I logged Library api as a "librarian"_SK
    And Accept header is "application/json"_SK
    And Request Content Type header is "application/x-www-form-urlencoded"_SK
    And I create a random "book" as request body_SK
    When I send POST request to "/add_book" endpoint_SK
    Then status code should be 200_SK
    And Response Content type is "application/json; charset=utf-8"_SK
    And the field value for "message" path should be equal to "The book has been created."_SK
    And "book_id" field should not be null_SK



 @ui @db @wip
  Scenario: Create a new book all layers
    Given I logged Library api as a "librarian"_SK
    And Accept header is "application/json"_SK
    And Request Content Type header is "application/x-www-form-urlencoded"_SK
    And I create a random "book" as request body_SK
    And I logged in Library UI as "librarian"_SK
    And I navigate to "Books" page_SK
    When I send POST request to "/add_book" endpoint_SK
    Then status code should be 200_SK
    And Response Content type is "application/json; charset=utf-8"_SK
    And the field value for "message" path should be equal to "The book has been created."_SK
    And "book_id" field should not be null_SK
    And UI, Database and API created book information must match_SK