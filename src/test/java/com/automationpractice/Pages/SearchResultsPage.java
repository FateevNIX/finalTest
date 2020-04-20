package com.automationpractice.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class SearchResultsPage {
    WebDriver driver;
    By firstItemInGrid = By.cssSelector("#center_column > ul > li:nth-child(1)");
    By moreButtonForFirstItem = By.cssSelector("li:nth-child(1) * a.button.lnk_view.btn.btn-default");

    public SearchResultsPage(WebDriver driver){
        this.driver = driver;
    }

    public SearchResultsPage moveToFirstProductInGrid(){ //кнопка "More" на продукте видна только после наведения на нее курсора. Правда не уверен стоило ли это в отдельный метод выносить.
        new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(firstItemInGrid));
        Actions action = new Actions(driver);
        WebElement elem = driver.findElement(firstItemInGrid);
        action.moveToElement(elem);
        return this;
    }
    public ProductDetailsPage clickOnFirstItem(){
        moveToFirstProductInGrid();
        new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(moreButtonForFirstItem));
        driver.findElement(moreButtonForFirstItem).click();
        return new ProductDetailsPage(driver);
    }
}
