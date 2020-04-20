package com.automationpractice;

import com.automationpractice.Pages.HomePage;
import com.automationpractice.Pages.ProductDetailsPage;
import com.automationpractice.Pages.SearchResultsPage;
import com.automationpractice.Pages.ShoppingCartPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Tests {

        private WebDriver driver;
        int[] quantity = {10, 1};
        String[] products = {"Printed Dress", "blouse"};
        char[] size = {'M', 'S'};
        List<String> fullProductNames = new ArrayList<String>();
        List<Double> fullPriceAtPopup = new ArrayList<Double>();

        @BeforeClass
        public static void setupClass() {
            WebDriverManager.chromedriver().setup(); //нашел в иннете WebDriverManager который добавляешь в dependencies и можно не обновлять вручную ChromeDriver и прописывать путь к нему
        }
        @Before
        public void setupTest() {
            driver = new ChromeDriver();
        }
        @After
        public void teardown() {
                if (driver != null) {
                    driver.quit();
                }
            }

        @Test
        public void testsPageObject() {
            HomePage homePage = new HomePage(driver);
            SearchResultsPage searchPage = new SearchResultsPage(driver);
            ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
            ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

            homePage.goToHomePage();
            homePage.enterProductName(products[0]);
            homePage.clickSubmitButton();
            searchPage.clickOnFirstItem();
            productDetailsPage.selectSize(size[0]);
            productDetailsPage.setQuantityTo(String.valueOf(quantity[0]));
            productDetailsPage.clickAddToCard();
            productDetailsPage.checkThatAllDataMatchTheSelectedValues(products[0], quantity[0], size[0]);
            fullProductNames.add(productDetailsPage.getProductName());
            fullPriceAtPopup.add(productDetailsPage.getPriceAtPopup());
            productDetailsPage.clickContinueShoppingAtPopup();
            productDetailsPage.enterProductName(products[1]);
            productDetailsPage.clickSubmitButton();
            searchPage.clickOnFirstItem();
            productDetailsPage.selectSize(size[1]);
            productDetailsPage.clickAddToCard();
            productDetailsPage.checkThatAllDataMatchTheSelectedValues(products[1], quantity[1], size[1]);
            fullProductNames.add(productDetailsPage.getProductName());
            fullPriceAtPopup.add(productDetailsPage.getPriceAtPopup());
            productDetailsPage.clickContinueShoppingAtPopup();
            productDetailsPage.clickShoppingCart();
            shoppingCartPage.checkAllDataMatchPreviouslySelectedValues(fullProductNames, size, quantity, fullPriceAtPopup);
            shoppingCartPage.deletefirstProductFromCart();
            shoppingCartPage.checkFirstproductWasDeleted(fullProductNames);
            shoppingCartPage.checkSecondProductIsInList(fullProductNames);

            System.out.println("Completed");//индикатор что тест прошел успешно
        }
    }


        /*    1. Open home page
            2. Search for the "Printed Dress"
            3. Select first result
            4. Select size M
            5. Select quantity 10
            6. Click add to card
            7. On popup verify that all data match the selected values and total price is correct
            8. Continue shopping
            9. Search for blouse
            10. Select size S
            11. Click add to card
            12. On popup verify that all data match the selected values and total price is correct
            13. Continue shopping
            14. Open shopping card
            15. Verify that shopping card contains both products with correct data
            16. Remove Printed Dress
            17. Verify that product has been removed and the second product still displayed on the card*/