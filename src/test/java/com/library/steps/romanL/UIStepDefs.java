package com.library.steps.romanL;

import com.library.pages.BookPageRomanL;
import com.library.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class UIStepDefs {
    LoginPage loginPage = new LoginPage();
    BookPageRomanL bookPageRomanL = new BookPageRomanL();





    @Given("I logged in Library UI as {string}_RL")
    public void i_logged_in_library_ui_as(String userType) {
        loginPage.login(userType);
    }
    @Given("I navigate to {string} page_RL")
    public void i_navigate_to_page(String page) {
        bookPageRomanL.navigateModule(page);

    }

    @Then("created user should be able to login Library UI_RL")
    public void created_user_should_be_able_to_login_library_ui() {
        loginPage.login(APIStepDefs.randomData.get("email").toString(),APIStepDefs.randomData.get("password").toString());

    }
    @Then("created user name should appear in Dashboard Page_RL")
    public void created_user_name_should_appear_in_dashboard_page() {
        Assert.assertEquals(bookPageRomanL.accountHolderName.getText(),APIStepDefs.randomData.get("full_name").toString());
    }
}
