package com.library.steps.romanL;

import com.library.pages.BookPageRomanL;

import com.library.utility.DB_Util;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class APIStepDefs {
    RequestSpecification givenPart = given().log().uri();
    Response response;
    ValidatableResponse thenPart;
    JsonPath jp;
    String pathParamValue;
    public static Map<String, Object> randomData = new HashMap<>();
    String token;
    String bookId;
    String userId;
    String bookName;
    BookPageRomanL bookPageRomanL = new BookPageRomanL();


    @Given("I logged Library api as a {string}_RL")
    public void i_logged_library_api_as_a(String userType) {
        givenPart.header("x-library-token", LibraryAPI_Util.getToken(userType));
    }

    @Given("Accept header is {string}_RL")
    public void accept_header_is(String acceptHeader) {
        givenPart.accept(acceptHeader);
    }

    @When("I send GET request to {string} endpoint_RL")
    public void i_send_get_request_to_endpoint(String endPoint) {
        response = givenPart.when().get(endPoint);
        thenPart = response.then();
        jp = response.jsonPath();
    }

    @Then("status code should be {int}_RL")
    public void status_code_should_be(int expectedStatusCode) {
        thenPart.statusCode(expectedStatusCode);
    }

    @Then("Response Content type is {string}_RL")
    public void response_content_type_is(String expectedContentType) {
        thenPart.contentType(expectedContentType);
    }

    @Then("Each {string} field should not be null_RL")
    public void each_field_should_not_be_null(String string) {
        thenPart.body(string, everyItem(is(notNullValue())));
    }

    @Given("Path param {string} is {string}_RL")
    public void path_param_is(String pathParam, String value) {
        pathParamValue = value;
        givenPart.pathParam(pathParam, value);
    }

    @Then("{string} field should be same with path param_RL")
    public void field_should_be_same_with_path_param(String path) {
        thenPart.body(path, is(equalTo(pathParamValue)));
    }

    @Then("following fields should not be null_RL")
    public void following_fields_should_not_be_null(List<String> dataTable) {
        for (String eachData : dataTable) {
            thenPart.body(eachData, is(notNullValue()));
        }
    }

    @Given("Request Content Type header is {string}_RL")
    public void request_content_type_header_is(String contentType) {
        givenPart.contentType(contentType);
    }

    @Given("I create a random {string} as request body_RL")
    public void i_create_a_random_as_request_body(String createData) {
        switch (createData) {
            case "book":
                randomData = LibraryAPI_Util.getRandomBookMap();
                break;
            case "user":
                randomData = LibraryAPI_Util.getRandomUserMap();
                break;
            default:
                throw new RuntimeException("Invalid Data Type " + createData);
        }
        givenPart.formParams(randomData);

    }

    @When("I send POST request to {string} endpoint_RL")
    public void i_send_post_request_to_endpoint(String endpoint) {
        response = givenPart.when().post(endpoint);
        thenPart = response.then();
        jp = response.jsonPath();
        response.prettyPeek();
        bookId = jp.getString("book_id");
        userId = jp.getString("user_id");

    }

    @Then("the field value for {string} path should be equal to {string}_RL")
    public void the_field_value_for_path_should_be_equal_to(String path, String pathValue) {
        thenPart.body(path, is(equalTo(pathValue)));
    }

    @Then("{string} field should not be null_RL")
    public void field_should_not_be_null(String path) {
        thenPart.body(path, is(notNullValue()));
    }

    @Given("I logged Library api with credentials {string} and {string}_RL")
    public void i_logged_library_api_with_credentials_and(String email, String password) {
        token = LibraryAPI_Util.getToken(email, password);
    }

    @Given("I send {string} information as request body_RL")
    public void i_send_information_as_request_body(String param) {
        givenPart.formParams(param, token);
    }

    @Then("UI, Database and API created book information must match_RL")
    public void ui_database_and_api_created_book_information_must_match() {
        jp = given()
                .accept(ContentType.JSON)
                .header("x-library-token", LibraryAPI_Util.getToken("librarian"))
                .pathParams("id", bookId)
                .when()
                .get("/get_book_by_id/{id}")
                .then().statusCode(200).extract().jsonPath();
        Map<String, Object> apiBookInfo = jp.getMap("$");
        bookName = jp.getString("name");

        DB_Util.createConnection();
        DB_Util.runQuery("select * from books where id=" + bookId);
        Map<String, Object> dbBookInfo = DB_Util.getRowMap(1);

        //Assert api and db
        assertEquals(apiBookInfo, dbBookInfo);
        System.out.println("API" + apiBookInfo);

        //NEED TO ADD UI ASSERTION!
        DB_Util.runQuery("select isbn,B.name,author,C.name,YEAR,B.description\n" +
                "from books B join book_categories C on B.book_category_id = C.id\n" +
                "where B.id =" + bookId);
        Map<String, Object> dbBookInfo2 = DB_Util.getRowMap(1);
        System.out.println("dbBookInfo" + dbBookInfo2);


        Map<String, Object> uiBookInfo = bookPageRomanL.bookInfoMap(bookName);
        System.out.println("UIINFO" + uiBookInfo);


        DB_Util.destroy();
    }

    @Then("created user information should match with Database_RL")
    public void created_user_information_should_match_with_database() {
        //API
        Map<String, Object> apiUserInfo = given()
                .accept(ContentType.JSON)
                .header("x-library-token", LibraryAPI_Util.getToken("librarian"))
                .pathParams("id", userId)
                .when()
                .get("/get_user_by_id/{id}")
                .then().statusCode(200).extract().jsonPath().getMap("$");

        //DB
        DB_Util.createConnection();
        DB_Util.runQuery("select * from users where id=" + userId);
        Map<String, Object> dbUserInfo = DB_Util.getRowMap(1);
        //Assert api and db
        assertEquals(apiUserInfo, dbUserInfo);

        DB_Util.destroy();

    }
}
