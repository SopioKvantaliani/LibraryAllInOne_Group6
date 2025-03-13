Feature: As a librarian, I want to create a new book

  @ts
  Scenario: Create a new book API
    Given I logged Library api as a "librarian"_RL
    And Accept header is "application/json"_RL
    And Request Content Type header is "application/x-www-form-urlencoded"_RL
    And I create a random "book" as request body_RL
    When I send POST request to "/add_book" endpoint_RL
    Then status code should be 200_RL
    And Response Content type is "application/json; charset=utf-8"_RL
    And the field value for "message" path should be equal to "The book has been created."_RL
    And "book_id" field should not be null_RL



 @ui @db @wip
  Scenario: Create a new book all layers
    Given I logged Library api as a "librarian"_RL
    And Accept header is "application/json"_RL
    And Request Content Type header is "application/x-www-form-urlencoded"_RL
    And I create a random "book" as request body_RL
    And I logged in Library UI as "librarian"_RL
    And I navigate to "Books" page_RL
    When I send POST request to "/add_book" endpoint_RL
    Then status code should be 200_RL
    And Response Content type is "application/json; charset=utf-8"_RL
    And the field value for "message" path should be equal to "The book has been created."_RL
    And "book_id" field should not be null_RL
    And UI, Database and API created book information must match_RL