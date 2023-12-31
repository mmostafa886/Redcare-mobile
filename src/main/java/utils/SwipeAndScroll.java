package utils;

import PageObjects.PageBase;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SwipeAndScroll extends PageBase {

    public SwipeAndScroll(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public WebElement scrollToAnElementByAttribute(AppiumDriver driver, String attribute) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable("
                + "new UiSelector().scrollable(true)).scrollIntoView("
                + "new UiSelector()." + attribute + ");"));
    }

    public void scrollHorizontally(By sliderElement) {
        /*The locator AppiumBy.id("android:id/list") is used to locate either the slider (which didn't work in our case)
         * Or in our case the 'list' containing all the elements
         * The reason for the need to do this is that: the element that we want to tab is not present in the current view till scrolling down*/
        RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(sliderElement));
        driver.executeScript("gesture: swipe", Map.of("elementId", scrollView.getId(),
                "percentage", 25,
                "direction", "left"));
    }

    public void scrollVertically(By sliderElement) {
        RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(sliderElement));
        driver.executeScript("gesture: swipe", Map.of("elementId", scrollView.getId(),
                "percentage", 25,
                "direction", "up"));
    }

    public void scrollHorizontalAndSelectItem(By sliderElement, String selectorValue) {
        RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(sliderElement));
        driver.executeScript("gesture: scrollElementIntoView", ImmutableMap.of("scrollableView", scrollView.getId(),
                "strategy", "xpath",
                "selector", selectorValue,
                "percentage", 30,
                "direction", "up",
                "maxCount", 3));//The max count of swipes that the script will do before failing to find the element
        driver.findElement(By.xpath(selectorValue)).click();
    }

}
