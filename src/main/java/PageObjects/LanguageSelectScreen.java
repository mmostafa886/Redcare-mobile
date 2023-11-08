package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LanguageSelectScreen extends PageBase {

    public By englishLanguage;
    public By languageFrame;


    public LanguageSelectScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT),Duration.ofMillis(200));
        intializeLanguageSelectElements();
    }

    public void intializeLanguageSelectElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        //Based on the platform retrieved from the current test, The locator is retrieved (whether Android or iOS)
        if ("Android".equalsIgnoreCase(platform)) {
            englishLanguage = By.xpath("//*[@text='English']");
            languageFrame = new By.ById("bottom_sheet");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void SelectLanguage(){
//        wait.until(ExpectedConditions.presenceOfElementLocated(englishLanguage));
        click(englishLanguage);
    }
}
