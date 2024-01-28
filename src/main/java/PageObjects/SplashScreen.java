package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SplashScreen extends PageBase {

    public By splashScreen;

    public SplashScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeSplashElements();
    }

    public void intializeSplashElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            splashScreen = new By.ById("cl_splash");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void splashScreenInvisibility() {
        waitForInVisibility(splashScreen);
    }
}
