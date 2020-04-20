package com.automationpractice.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Arrays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductDetailsPage {
    WebDriver driver;
    By addToCartButton = By.id("add_to_cart");
    By selectSizeS = By.cssSelector("#group_1 > option:nth-child(1)");
    By selectSizeM = By.cssSelector("#group_1 > option:nth-child(2)");
    By selectSizeL = By.cssSelector("#group_1 > option:nth-child(3)");
    By quantityTextBox = By.id("quantity_wanted");
    By productNameAtPopup = By.id("layer_cart_product_title");
    By colourAndSizeAtPopup = By.id("layer_cart_product_attributes");
    By quantityAtPopup = By.id("layer_cart_product_quantity");
    By continueShoppingButton = By.cssSelector("div.button-container > span");
    By priceOnPage = By.id("our_price_display");
    By priceOnPopup = By.id("layer_cart_product_price");
    By submitButton = By.cssSelector("#searchbox > button");
    By searchTextBox = By.id("search_query_top");
    By shoppingCartIcon = By.xpath("//div[@class='shopping_cart']/a");


    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductDetailsPage selectSize(char size) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(selectSizeM));
        if (size == 'S') {
            driver.findElement(selectSizeS).click();
        } else if (size == 'M') {
            driver.findElement(selectSizeM).click();
        } else if (size == 'L') {
            driver.findElement(selectSizeL).click();
        }
        return this;
    }

    public ProductDetailsPage setQuantityTo(String quantity) {

        driver.findElement(quantityTextBox).clear();
        driver.findElement(quantityTextBox).sendKeys(quantity);
        return this;
    }

    public ProductDetailsPage clickAddToCard() {
        driver.findElement(addToCartButton).click();
        return this;
    }

    public ProductDetailsPage clickContinueShoppingAtPopup() {
        driver.findElement(continueShoppingButton).click();
        return this;
    }

    public String getProductName() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameAtPopup));
        return driver.findElement(productNameAtPopup).getText();
    }

    public int getQuantityAtPopup() {
        int quantityOnPopup = Integer.parseInt(driver.findElement(quantityAtPopup).getText());
        return quantityOnPopup;
    }

    public char getSizeAtPopup() {
        WebElement elem = driver.findElement(colourAndSizeAtPopup);
        int lastChar = elem.getText().length() - 1;
        char size = elem.getText().charAt(lastChar); //в строке находится цвет и размер. Я забираю только размер
        return size;
    }

    public double getPriceAtPage() {
        double priceAtPage = Double.parseDouble(driver.findElement(priceOnPage).getText().replace("$", ""));
        return priceAtPage;
    }

    public double getPriceAtPopup() {
        double priceAtPopup = Double.parseDouble(driver.findElement(priceOnPopup).getText().replace("$", ""));
        return priceAtPopup;
    }

    public ProductDetailsPage checkThatAllDataMatchTheSelectedValues(String productName, int quantity, char size) {

        assertThat(getProductName().toLowerCase(), stringContainsInOrder(Arrays.asList(productName.toLowerCase().split(" "))));
        assertThat(getQuantityAtPopup(), equalTo(quantity));
        assertThat(getSizeAtPopup(),equalTo(size));
        double totalPrice = getPriceAtPage() * quantity; //сумма в попапе равняется сумме на странице умноженной на количество товара
        assertThat(getPriceAtPopup(), equalTo(totalPrice));
        return this;
    }

    public ProductDetailsPage enterProductName(String text) {
        driver.findElement(searchTextBox).clear(); //текст остается в строке поиска, потому добавлена эта строка для очистки поля перед вводом
        driver.findElement(searchTextBox).sendKeys(text);
        return this;
    }

    public SearchResultsPage clickSubmitButton() {
        driver.findElement(submitButton).click();
        return new SearchResultsPage(driver);
    }

    public ShoppingCartPage clickShoppingCart(){
        driver.findElement(shoppingCartIcon).click();
        return new ShoppingCartPage(driver);
    }
}