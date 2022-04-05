package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

/**
 * This class describes methods for filtering a product and comparing it
 */
public class Product {
    protected WebDriver chromeDriver;
    protected WebElement minPrice;
    protected WebElement maxPrice;
    protected WebElement point;
    protected WebElement firstProduct;
    protected WebElement secondProduct;
    protected WebElement countElements;
    protected WebElement searchElement;
    protected WebElement searchButton;

    public Product(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.minPrice = chromeDriver
                .findElement(By.xpath("(//div[@data-filter-id='glprice']//input[@type='text'])[1]"));
        this.maxPrice = chromeDriver
                .findElement(By.xpath("(//div[@data-filter-id='glprice']//input[@type='text'])[2]"));
    }

    /**
     * This method sets a filter on prices
     * @param minPrices This is the minimum price
     * @param maxPrices This is the maximum price
     */
    @Step("Установка цены от {query} до {query}")
    public void priceFilter(String minPrices, String maxPrices) {
        minPrice.click();
        minPrice.sendKeys(minPrices);
        minPrice.click();
        maxPrice.sendKeys(maxPrices);
    }

    /**
     * This method sets a filter on brands
     * @param firstBrand This is the first brand for filtration
     * @param secondBrand This is the second brand for filtration
     */
    @Step("Выбор производителя {query} и {query}")
    public void brandFilter(String firstBrand, String secondBrand) {
        point = chromeDriver.findElement(By.xpath("//*[text()='Производитель']"));
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].scrollIntoView();", point);
        firstProduct = chromeDriver.findElement(By.xpath("//*[@name='Производитель " + firstBrand + "']"));
        secondProduct = chromeDriver.findElement(By.xpath("//*[@name='Производитель " + secondBrand + "']"));
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].click();", firstProduct);
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].click();", secondProduct);
        WebElement greyWindow = chromeDriver.findElement(By.xpath("//*[@class='_2Lvbi _1oZmP']"));
        Boolean disappearance = (new WebDriverWait(chromeDriver,10)
                .until(ExpectedConditions.stalenessOf(greyWindow)));
    }

    /**
     * This method sets the number of elements to display on the page
     * @param count This is the number of display elements on the page
     */
    @Step("Отображение {query} элементов на странице")
    public void countElementsOnPage(int count) {
        countElements = chromeDriver.findElement(By.xpath("//*[@type='button'][@class='vLDMf']"));
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].scrollIntoView();", countElements);
        countElements.click();
        chromeDriver.findElement(By.xpath("//button[text()='Показывать по " + count + "']")).click();
        WebElement greyWindow = chromeDriver.findElement(By.xpath("//*[@class='_2Lvbi _1oZmP']"));
        Boolean disappearance = (new WebDriverWait(chromeDriver,10)
                .until(ExpectedConditions.stalenessOf(greyWindow)));
        int countElementsOnPage = chromeDriver.findElements(By.xpath("//h3[@data-zone-name='title']")).size();
        Assertions.assertTrue(countElementsOnPage == count,
                "\nКоличество элементов на странице не соответствует " + count);
    }

    /**
     * This method saves the first element of the filtered list,
     * enters the stored value and compare it with the first
     */
    @Step("Поиск и проверка на странице показов")
    public void searchAndCheckFirstElement() {
        List<WebElement> list = chromeDriver.findElements(By.xpath("//h3[@data-zone-name='title']/a"));
        String newList = list.stream().findFirst().get().getAttribute("title").toUpperCase();
        searchElement = chromeDriver.findElement(By.xpath("//input[@id='header-search']"));
        searchElement.click();
        searchElement.sendKeys(newList);
        searchButton = chromeDriver.findElement(By.xpath("//button[@data-r='search-button']"));
        searchButton.click();
        WebElement visability = (new WebDriverWait(chromeDriver,10)
                .until(ExpectedConditions.presenceOfElementLocated((By) By
                        .xpath("//div[@data-apiary-widget-name='@MarketNode/FeedbackForm']"))));
        List<WebElement> secondList = chromeDriver.findElements(By.xpath("//h3[@data-zone-name='title']/a"));
        String newSecondList = secondList.stream().findFirst().get().getAttribute("title").toUpperCase();
        Assertions.assertTrue((newList.equals(newSecondList)),
                "\nНаименование товара:\n" + newSecondList +"\nне соответствует сохраненному значению:\n" + newList);
    }
}
