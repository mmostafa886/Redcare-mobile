package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class AuthorizationScreen extends PageBase {

    private By skipAuthBtn;

    public AuthorizationScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeAuthElements();
    }

    private void intializeAuthElements() {
        String platform = String.valueOf(getDriver().getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            skipAuthBtn = new By.ById("btn_continue_as_guest");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void skipAuthorization() {
        click(skipAuthBtn);
    }
}
