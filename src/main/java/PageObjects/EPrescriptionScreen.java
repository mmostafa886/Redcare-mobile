package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class EPrescriptionScreen extends PageBase {

    private By skipEPrescribeBtn;

    public EPrescriptionScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeEPrescripeElements();
    }

    private void intializeEPrescripeElements() {
        String platform = String.valueOf(getDriver().getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            skipEPrescribeBtn = new By.ById("btn_skip");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }


    public void skipEPrescripe() {
        click(skipEPrescribeBtn);
    }
}
