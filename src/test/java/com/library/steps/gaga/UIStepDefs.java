package com.library.steps.gaga;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UIStepDefs {


    private static final Logger log = LoggerFactory.getLogger(UIStepDefs.class);
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();

    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String role) {
        loginPage.login(role);

    }
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String uiPage) {
        bookPage.navigateModule(uiPage);
    }

    @And("created user should be able to login Library UI")
    public void createdUserShouldBeAbleToLoginLibraryUI() {
        Map<String, Object> userResponseMap = APIStepDefs.userResponseMap;


    }

    @And("created user name should appear in Dashboard Page")
    public void createdUserNameShouldAppearInDashboardPage() {

        BrowserUtil.verifyElementDisplayed(bookPage.accountHolderName);

    }
}
