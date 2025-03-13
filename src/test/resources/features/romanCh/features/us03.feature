Feature: As a librarian, I want to create a new book

  @romanCh
  Scenario: Create a new book API
    Given I logged Library api as a "librarian"_RCH
    And Accept header is "application/json"_RCH
    And Request Content Type header is "application/x-www-form-urlencoded"_RCH
    And I create a random "book" as request body_RCH
    When I send POST request to "/add_book" endpoint_RCH
    Then status code should be 200_RCH
    And Response Content type is "application/json; charset=utf-8"_RCH
    And the field value for "message" path should be equal to "The book has been created."_RCH
    And "book_id" field should not be null_RCH



  @romanCh @ui @db
  Scenario: Create a new book all layers_RCH
    Given I logged Library api as a "librarian"_RCH
    And Accept header is "application/json"_RCH
    And Request Content Type header is "application/x-www-form-urlencoded"_RCH
    And I create a random "book" as request body_RCH
    And I logged in Library UI as "librarian"_RCH
    And I navigate to "Books" page_RCH
    When I send POST request to "/add_book" endpoint_RCH
    Then status code should be 200_RCH
    And Response Content type is "application/json; charset=utf-8"_RCH
    And the field value for "message" path should be equal to "The book has been created."_RCH
    And "book_id" field should not be null_RCH
    And UI, Database and API created book information must match_RCH