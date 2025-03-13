package com.library.steps.romanCh;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.DatabaseHelper;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIStepDefsROMANCH {



    RequestSpecification givenPart = given().log().uri();
    Map<String, Object> randomData = new HashMap<>();

    Response response;
    ValidatableResponse thenPart;
    JsonPath jp;


    public static Map<String, Object> userResponseMap;

    String authToken;
    String actualPathValue;
    String endPoint;
    BookPage bookPage = new BookPage();

    LoginPage loginPage = new LoginPage();



    @Given("I logged Library api as a {string}_RCH")
    public void i_logged_library_api_as_a(String role) {
        givenPart.header("x-library-token", LibraryAPI_Util.getToken(role));
    }

    @Given("Accept header is {string}_RCH")
    public void accept_header_is(String acceptedHeader) {
        givenPart.accept(acceptedHeader);
    }

    @When("I send GET request to {string} endpoint_RCH")
    public void i_send_get_request_to_endpoint(String endPoint) {
        response= givenPart.when().get(endPoint);
    }

    @Then("status code should be {int}_RCH")
    public void status_code_should_be(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);

    }

    @Then("Response Content type is {string}_RCH")
    public void response_content_type_is(String expectedContentType) {
        jp = response.then().contentType(expectedContentType).extract().jsonPath();

    }

    @Then("Each {string} field should not be null_RCH")
    public void each_field_should_not_be_null(String path) {

        List<String> list = jp.getList(path);

        for (String each : list) {

            Assert.assertNotNull(each);
        }
    }

    @Given("Path param {string} is {string}_RCH")
    public void path_param_is(String path, String pathParamValue) {
        actualPathValue = pathParamValue;
        givenPart.given().pathParams(path, pathParamValue);
    }

    @Then("{string} field should be same with path param_RCH")
    public void field_should_be_same_with_path_param(String path) {
        String expectedId = jp.getString(path);
        Assert.assertEquals(expectedId, actualPathValue);
    }

    @Then("following fields should not be null_RCH")
    public void following_fields_should_not_be_null(List <String> dataTable) {
        for (String eachData : dataTable) {
            Assert.assertNotNull( jp.getString(eachData));
        }
    }

    @Given("Request Content Type header is {string}_RCH")
    public void request_content_type_header_is(String contentType) {
        givenPart.contentType(contentType);
    }

    @Given("I create a random {string} as request body_RCH")
    public void i_create_a_random_as_request_body(String dataType) {

        switch (dataType) {
            case "book":
                randomData = LibraryAPI_Util.getRandomBookMap();
                break;

            case "user":
                randomData = LibraryAPI_Util.getRandomUserMap();
                break;
            default:
                throw new RuntimeException("Invalid Data Type" + dataType);
        }
        givenPart.formParams(randomData);

    }

    @When("I send POST request to {string} endpoint_RCH")
    public void i_send_post_request_to_endpoint(String endpoint) {

        response = givenPart.when().post(endpoint);
        thenPart = response.then();
        jp = response.jsonPath();
    }

    @Then("the field value for {string} path should be equal to {string}_RCH")
    public void the_field_value_for_path_should_be_equal_to(String path, String expectedMessage) {
        String actualMessage = jp.getString(path);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Then("{string} field should not be null_RCH")
    public void field_should_not_be_null(String path) {
        thenPart.body(path, Matchers.notNullValue());

    }

    @Given("I logged in Library UI as {string}_RCH")
    public void i_logged_in_library_ui_as(String userRole) {
        loginPage.login(userRole);
    }

    @Given("I navigate to {string} page_RCH")
    public void i_navigate_to_page(String uiPage) {
        bookPage.navigateModule(uiPage);
    }

    @Then("UI, Database and API created book information must match_RCH")
    public void ui_database_and_api_created_book_information_must_match() {
        String bookId = jp.getString("book_id");

        //DB Data
        DB_Util.createConnection();
        //modified query
        String query = "select isbn, name, author, year from books where id="+bookId;
        DB_Util.runQuery(query);
        Map<String, Object> dbData = DB_Util.getRowMap(1);

        //full query
        String query2 = "select * from books where id="+bookId;
        DB_Util.runQuery(query2);
        Map<String, Object> dbData2 = DB_Util.getRowMap(1);

        Object bookName = dbData.get("name");

        //UI Data
        Map<String, String> createBookUi = bookPage.findSpecificBook(bookName.toString());
        String category = createBookUi.remove("category");
        System.out.println(createBookUi);
        Assert.assertEquals(dbData, createBookUi);
        createBookUi.put("category", category);

        //API Data
        jp =given()
                .accept(ContentType.JSON)
                .header("x-library-token", LibraryAPI_Util.getToken("librarian"))
                .pathParams("id", bookId)
                .when()
                .get("/get_book_by_id/{id}")
                .then().statusCode(200).extract().jsonPath();

        Map<String, Object> responseMap = jp.getMap("$");

        Assert.assertEquals(dbData2, responseMap );

        DB_Util.destroy();
    }

    @Then("created user information should match with Database_RCH")
    public void created_user_information_should_match_with_database() {

        int id = jp.getInt("user_id"); //--> actual data from DB

        String query = DatabaseHelper.getUserByIdQuery(id);

        DB_Util.runQuery(query);
        Map<String, Object> actualData = DB_Util.getRowMap(1);

        //--> expected data from API
        String password = (String) randomData.remove("password"); // we need to remove password, because we have static password in random data.

        Assert.assertEquals(randomData, actualData);

        randomData.put("password", password); // after we need to add, because we gonna use password again on future.
    }

    @Then("created user should be able to login Library UI_RCH")
    public void created_user_should_be_able_to_login_library_ui() {

        LoginPage loginPage = new LoginPage();

        String email = (String) randomData.get("email");

        String password = (String) randomData.get("password");

        loginPage.login(email, password);

        BookPage bookPage = new BookPage();
        BrowserUtil.waitForVisibility(bookPage.accountHolderName, 10);
    }

    @Then("created user name should appear in Dashboard Page_RCH")
    public void created_user_name_should_appear_in_dashboard_page() {

        BookPage bookPage = new BookPage();

        String uiFullName = bookPage.accountHolderName.getText();

        String apiFullName = (String) randomData.get("full_name");

        Assert.assertEquals(apiFullName,uiFullName);

    }

    @Given("I logged Library api with credentials {string} and {string}_RCH")
    public void i_logged_library_api_with_credentials_and(String email, String password) {

        authToken = LibraryAPI_Util.getToken(email, password);
        System.out.println("My TOKEN ---> " + authToken);

        if (authToken == null || authToken.isEmpty()) {
            throw new RuntimeException("Failed to retrieve authentication token.");
        }
    }

    @Given("I send {string} information as request body_RCH")
    public void i_send_information_as_request_body(String token) {
        givenPart.formParam(token,authToken);

    }


}
