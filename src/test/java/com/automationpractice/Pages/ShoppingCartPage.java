package com.automationpractice.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;


public class ShoppingCartPage {
    WebDriver driver;
    By totalPrice = By.xpath("//span[contains(@id, 'total_product_price')]");
    By productName = By.xpath("//td[2]/p/a");
    By quantityOfProduct = By.xpath("//td[5]/input[2]");
    By colourAndSize = By.xpath("//small[2]/a");
    By trashcanButton = By.xpath("//a[@class='cart_quantity_delete']/i");
    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getProductNames() {
        List<WebElement> resultList = driver.findElements(productName);
        List<String> titlesOfProducts = new ArrayList<String>();
        for (WebElement resultItem : resultList) {
            titlesOfProducts.add(resultItem.getText());
        }
        return titlesOfProducts;
    }

    public List<Integer> getProductQuantity() {
        List<WebElement> resultList = driver.findElements(quantityOfProduct);
        List<Integer> quantityOfProducts = new ArrayList<Integer>();
        for (WebElement resultItem : resultList) {
            quantityOfProducts.add(Integer.parseInt(resultItem.getAttribute("value")));
        }
        return quantityOfProducts;
    }

    public List<Double> getProductPrice() {
        List<WebElement> resultList = driver.findElements(totalPrice);
        List<Double> productPrices = new ArrayList<Double>();
        for (WebElement resultItem : resultList) {
            productPrices.add(Double.parseDouble(resultItem.getText().replace("$", "")));
        }
        return productPrices;
    }
    public char[] getProductSize() {
        List<WebElement> resultList = driver.findElements(colourAndSize);
        char[] sizeOfProduct = new char[resultList.size()];
        int n = 0;
        for (WebElement resultItem : resultList) {
            int lastChar = resultItem.getText().length() - 1;
            sizeOfProduct[n] = resultItem.getText().charAt(lastChar);
            n++;
        }
        return sizeOfProduct;
    }

    public ShoppingCartPage checkAllDataMatchPreviouslySelectedValues(List<String> productName, char[] size, int[] quantity, List<Double> productPrice) {
        int i = 0;
        List<Integer> quantityOfProducts = getProductQuantity();
        char[] productSizes = getProductSize();
        while (i < getProductNames().size()) {
            assertThat(getProductNames().get(i).toLowerCase(), equalTo(productName.get(i).toLowerCase()));
            assertThat(productSizes[i], equalTo(size[i]));
            assertThat(quantityOfProducts.get(i), equalTo(quantity[i]));
            assertThat(getProductPrice().get(i), equalTo(productPrice.get(i)));
            i++;
        }
        return this;
    }
    public ShoppingCartPage deletefirstProductFromCart(){
        driver.findElement(trashcanButton).click();
        return this;
    }
    public ShoppingCartPage checkFirstproductWasDeleted(List<String> productsName){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(productName,2));
        assertThat(getProductNames().get(0), not(productsName.get(0)));
        return this;
    }
    public ShoppingCartPage checkSecondProductIsInList(List<String> productsName){
        assertThat(getProductNames().get(0).toLowerCase(), equalTo(productsName.get(1).toLowerCase()));
        return this;
    }
}