package utils;

import PageObjects.PageBase;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * A set of methods to handle the Swipe/Scroll in all directions instead of re-use the same block of code several times
 * whenever needed
 */
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
//        RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(sliderElement));
        RemoteWebElement scrollView = (RemoteWebElement) getWait().until(ExpectedConditions.elementToBeClickable(sliderElement));
        getDriver().executeScript("gesture: swipe", Map.of("elementId", scrollView.getId(), "percentage", 25, "direction", "left"));
    }

    public void scrollVertically(By sliderElement) {
        RemoteWebElement scrollView = (RemoteWebElement) getWait().until(presenceOfElementLocated(sliderElement));
        getDriver().executeScript("gesture: swipe", Map.of("elementId", scrollView.getId(),
                "percentage", 25,
                "direction", "up"));
    }

    public void scrollHorizontalAndSelectItem(By sliderElement, String selectorValue) {
        RemoteWebElement scrollView = (RemoteWebElement) getWait().until(presenceOfElementLocated(sliderElement));
        getDriver().executeScript("gesture: scrollElementIntoView", ImmutableMap.of("scrollableView", scrollView.getId(),
                "strategy", "xpath",
                "selector", selectorValue,
                "percentage", 30,
                "direction", "up",
                "maxCount", 3));//The max count of swipes that the script will do before failing to find the element
        getDriver().findElement(By.xpath(selectorValue)).click();
    }

    public void scrollVerticallyGest(By sliderElement, String selectorValue) {
        RemoteWebElement scrollView = (RemoteWebElement) getWait().until(presenceOfElementLocated(sliderElement));
        getDriver().executeScript("gesture: scrollElementIntoView", ImmutableMap.of("scrollableView", scrollView.getId(),
                "strategy", "id",
                "selector", selectorValue,
                "percentage", 30,
                "direction", "up",
                "maxCount", 3));
    }




//The lines below is a trial to convert the Gesture scroll method to a dynamic one
    //Need optimization in case we need to scroll up(direction: down)
  /*  public void scrollVerticallyGest(By sliderElement, String selectorValue, int maxCount) {
        for (int count = 0; count < maxCount; count++) {
            RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(sliderElement));
            boolean found = scrollElementIntoView(scrollView, selectorValue, "up", maxCount);
            // If the element is not found in the 'up' direction, try scrolling 'down'
            if (!found) {
                found = scrollElementIntoView(scrollView, selectorValue, "down", maxCount);
                break;
            }
            // Break the loop if the element is found
            if (found) {
                break;
            }
        }
    }

    private boolean scrollElementIntoView(RemoteWebElement scrollView, String selectorValue, String direction, int maxCount) {
        try {
            driver.executeScript("gesture: scrollElementIntoView", ImmutableMap.of("scrollableView", scrollView.getId(),
                    "strategy", "id",
                    "selector", selectorValue,
                    "percentage", 30,
                    "direction", direction,
                    "maxCount", maxCount));
            return true;  // Element found after scroll
        } catch (Exception e) {
            return false; // Element not found after scroll
        }
    }*/
}
