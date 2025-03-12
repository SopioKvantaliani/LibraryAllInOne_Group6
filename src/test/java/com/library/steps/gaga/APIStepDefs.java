package com.library.steps.gaga;

import com.library.pages.BookPage;
import com.library.utility.DB_Util;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class APIStepDefs {

    public static Map<String, Object> userResponseMap;

    RequestSpecification givenPart = given().log().uri();
    Response response;
    JsonPath jsonPath;
    String expectedPathParamValue;
    BookPage bookPage = new BookPage();

    Map<String, Object> randomBookMap;




    @Given("I logged Library api as a {string}_DN")
    public void i_logged_library_api_as_a(String role) {
        String token = LibraryAPI_Util.getToken(role);
        givenPart.header("x-library-token", token); //authorization
    }
    @Given("Accept header is {string}_DN")
    public void accept_header_is(String acceptHeader) {
        givenPart.accept(acceptHeader);
    }
    @When("I send GET request to {string} endpoint_DN")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(endpoint);
        response.prettyPeek();
    }
    @Then("status code should be {int}_DN")
    public void status_code_should_be(Integer expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }
    @Then("Response Content type is {string}_DN")
    public void response_content_type_is(String contentType) {
         jsonPath = response.then().contentType(contentType).extract().jsonPath();
    }
    @Then("Each {string} field should not be null_DN")
    public void each_field_should_not_be_null(String path) {

        List<String> list = jsonPath.getList(path);

        for (String each : list){
            Assert.assertNotNull(each);
        }
    }


    @Given("Path param {string} is {string}")
    public void path_param_is(String pathParamKey, String pathParamValue) {
        givenPart.pathParam(pathParamKey, pathParamValue);
        expectedPathParamValue = pathParamValue;
    }
    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String path
    ) {
        String actualPath = jsonPath.getString(path);
        System.out.println("actualPath = " + actualPath);

        Assert.assertEquals(expectedPathParamValue,actualPath);

    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> dataTable) {

        for (String each : dataTable){
            Assert.assertNotNull(jsonPath.getString(each));
        }

    }



    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
       givenPart.given().contentType(contentType);
    }
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String randomData) {

        Map<String, Object> randomBookMap = LibraryAPI_Util.getRandomBookMap();
        System.out.println("randomBookMap = " + randomBookMap);

    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        response=givenPart.post(endpoint);

    }
    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String message, String messageText) {
        response.then().body(message, is(messageText));

    }
    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        Assert.assertNotNull(jsonPath.get(path));
    }


    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {

        String bookId = jsonPath.getString("book_id");

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
        jsonPath =given()
                .accept(ContentType.JSON)
                .header("x-library-token", LibraryAPI_Util.getToken("librarian"))
                .pathParams("id", bookId)
                .when()
                .get("/get_book_by_id/{id}")
                .then().statusCode(200).extract().jsonPath();

        Map<String, Object> responseMap = jsonPath.getMap("$");

        Assert.assertEquals(dbData2, responseMap );

    }



    @And("created user information should match with Database")
    public void createdUserInformationShouldMatchWithDatabase() {


    }

    @Given("I logged Library api with credentials {string} and {string}")
    public void i_logged_library_api_with_credentials_and(String email, String password) {
       LibraryAPI_Util.getToken(email,password);
    }

    @Given("I send {string} information as request body")
    public void i_send_information_as_request_body(String string) {



    }
}
