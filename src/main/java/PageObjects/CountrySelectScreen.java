package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class CountrySelectScreen extends PageBase {

    public By selectCountryBtn;

    public CountrySelectScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeCoutrySelectElements();
    }

    public void intializeCoutrySelectElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            selectCountryBtn = By.xpath("//*[@text='Germany']");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void SelectCountry() {
        click(selectCountryBtn);
    }


}
