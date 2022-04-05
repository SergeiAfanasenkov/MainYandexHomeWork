package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * This class describes the method to navigate to the catalog
 */
public class MarketPage {

    protected WebDriver chromeDriver;
    protected String catalogId = "catalogPopupButton";
    protected String notePath = "catalogPopup";

    public MarketPage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    /**
     * This method describes the transition to the directory and selection laptops
     */
    @Step("Переход в каталог и в раздел компьютеры -> ноутбуки")
    public void goToCatalog()  {
        chromeDriver.findElement(By.id(catalogId)).click();
        chromeDriver.findElement(By.id(notePath));
        Actions action = new Actions(chromeDriver);
        WebElement chapterPath = chromeDriver
                .findElement(By.xpath("//span[contains(text(),'Компьютеры')]"));
        action.moveToElement(chapterPath).build().perform();
        chromeDriver.findElement(By.xpath("//div[@id = 'catalogPopup']//*[text()='Ноутбуки']")).click();
    }
}

