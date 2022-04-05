package ru.yandex;

/**
 * @author Afanasenkov Sergei
 */

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.MainPage;
import pages.MarketPage;
import pages.Product;

/**
 * This class runs tests
 */
public class Tests extends BaseTest{

    /**
     * This is the main test
     * @param minPrice This is the minimum price
     * @param maxPrice This is the maximum price
     * @param firstBrand This is the first brand for filtration
     * @param secondBrand This is the second brand for filtration
     * @param countElements This is the number of display elements on the page
     */
    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка соответствия найденного товара и сохраненного значения")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @CsvSource({"10000, 900000, HP, Lenovo, 12"})
    public void mainTest(String minPrice, String maxPrice,
                         String firstBrand, String secondBrand, int countElements ) {
        chromeDriver.get("https://yandex.ru/");
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.goToMarket();
        MarketPage marketPage = new MarketPage(chromeDriver);
        marketPage.goToCatalog();
        Product product = new Product(chromeDriver);
        product.priceFilter(minPrice, maxPrice);
        product.brandFilter(firstBrand, secondBrand);
        product.countElementsOnPage(countElements);
        product.searchAndCheckFirstElement();
    }
}
