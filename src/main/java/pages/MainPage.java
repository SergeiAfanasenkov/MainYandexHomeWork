package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class describes the transition and tab switching method
 */
public class MainPage {

    protected WebDriver chromeDriver;
    protected WebElement marketButton;

    public MainPage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.marketButton = chromeDriver.findElement(By.xpath("//*[text() = 'Маркет']"));
    }

    /**
     * This method describes going to the market and switching tabs
     */
    @Step("Переход на яндекс маркет")
    public void goToMarket() {
        marketButton.click();
        String originalWindow = chromeDriver.getWindowHandle();
        for (String windowHandle : chromeDriver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                chromeDriver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}






