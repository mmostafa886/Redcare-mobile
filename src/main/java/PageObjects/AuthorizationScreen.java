package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class AuthorizationScreen extends PageBase {

    public By skipAuthBtn;

    public AuthorizationScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeAuthElements();
    }

    public void intializeAuthElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            skipAuthBtn = new By.ById("btn_continue_as_guest");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void skipAuthorization() {
        click(skipAuthBtn);
    }
}
