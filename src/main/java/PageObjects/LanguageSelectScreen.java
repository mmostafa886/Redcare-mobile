package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class LanguageSelectScreen extends PageBase {

    private By englishLanguage;
    private By languageFrame;


    public LanguageSelectScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
//       wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT), Duration.ofMillis(200));
        intializeLanguageSelectElements();
    }

    private void intializeLanguageSelectElements() {
        String platform = String.valueOf(getDriver().getCapabilities().getPlatformName());
        //Based on the platform retrieved from the current test, The locator is retrieved (whether Android or iOS)
        if ("Android".equalsIgnoreCase(platform)) {
            englishLanguage = By.xpath("//*[@text='English']");
            languageFrame = new By.ById("bottom_sheet");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void SelectLanguage() {
        click(englishLanguage);
    }
}