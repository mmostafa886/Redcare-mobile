package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageBase {
    public static AppiumDriver driver;
    public WebDriverWait wait;
    public static final long WAIT = 15;

    public PageBase(AppiumDriver appiumDriver) {
        driver = appiumDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT), Duration.ofMillis(200));
    }

    public void waitForVisibility(By byObject) {
        elementPresence(byObject);
        wait.until(ExpectedConditions.visibilityOf(customLocate(byObject)));
    }

    public void waitForInVisibility(By byObject) {
        elementPresence(byObject);
        wait.until(ExpectedConditions.invisibilityOf(customLocate(byObject)));
    }

    public void clear(By byObject) {
        waitForVisibility(byObject);
        customLocate(byObject).clear();
    }

    public void click(By byObject) {
        waitForVisibility(byObject);
        customLocate(byObject).click();
    }

    public String getElementText(By byObject) {
        waitForVisibility(byObject);
        return customLocate(byObject).getText();
    }

    public void sendText(By byObject, String text) {
        waitForVisibility(byObject);
        customLocate(byObject).sendKeys(text);
    }

    public String getElementAttribute(By byObject, String attribute) {
        waitForVisibility(byObject);
        return customLocate(byObject).getAttribute(attribute);
    }

    public void elementPresence(By byObject) {
        wait.until(ExpectedConditions.presenceOfElementLocated(byObject));
    }

    public static WebElement customLocate(By byObject) {
        return driver.findElement(byObject);
    }

}
