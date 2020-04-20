package com.automationpractice.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;
    By searchTextBox = By.id("search_query_top");
    By submitButton = By.cssSelector("#searchbox > button");

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public  HomePage goToHomePage(){
        driver.get("http://automationpractice.com");
        return this;
    }

    public HomePage enterProductName(String text){
        driver.findElement(searchTextBox).clear(); //текст остается в строке поиска, потому добавлена эта строка для очистки поля перед вводом
        driver.findElement(searchTextBox).sendKeys(text);
        return this;
    }

    public SearchResultsPage clickSubmitButton(){
        driver.findElement(submitButton).click();
        return new SearchResultsPage(driver);
    }

}
