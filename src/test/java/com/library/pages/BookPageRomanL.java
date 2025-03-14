package com.library.pages;

import com.library.utility.BrowserUtil;
import com.library.utility.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookPageRomanL extends BasePage {
    @FindBy(xpath = "//table/tbody/tr")
    public List<WebElement> allRows;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement search;

    @FindBy(id = "book_categories")
    public WebElement mainCategoryElement;

    @FindBy(name = "name")
    public WebElement bookName;


    @FindBy(xpath = "(//input[@type='text'])[4]")
    public WebElement author;

    @FindBy(xpath = "//div[@class='portlet-title']//a")
    public WebElement addBook;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveChanges;

    @FindBy(xpath = "//div[@class='toast-message']")
    public WebElement toastMessage;

    @FindBy(name = "year")
    public WebElement year;

    @FindBy(name = "isbn")
    public WebElement isbn;

    @FindBy(id = "book_group_id")
    public WebElement categoryDropdown;


    @FindBy(id = "description")
    public WebElement description;


    public WebElement editBook(String book) {
        String xpath = "//td[3][.='" + book + "']/../td/a";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }

    public WebElement borrowBook(String book) {
        String xpath = "//td[3][.='" + book + "']/../td/a";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }

    public Map<String, Object> bookInfoMap(String newBookName) {
        Map<String, Object> bookInfo = new LinkedHashMap<>();
        BrowserUtil.waitFor(2);
        search.sendKeys(newBookName);

        BrowserUtil.waitFor(2);
        bookInfo.put("book_name", bookName.getText());
        BrowserUtil.waitFor(2);
        bookInfo.put("author", author.getText());
        BrowserUtil.waitFor(2);
        bookInfo.put("year", year.getText());
        BrowserUtil.waitFor(2);
        editBook(newBookName).click();
        BrowserUtil.waitFor(2);
        bookInfo.put("isbn", isbn.getText());
        Select select = new Select(categoryDropdown);
        bookInfo.put("book_category", select.getFirstSelectedOption().getText());
        BrowserUtil.waitFor(2);
        bookInfo.put("description", description.getText());
        return bookInfo;
    }


}
