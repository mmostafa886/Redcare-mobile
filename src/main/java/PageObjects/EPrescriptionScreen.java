package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class EPrescriptionScreen extends PageBase {

    public By skipEPrescribeBtn;

    public EPrescriptionScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeEPrescripeElements();
    }

    public void intializeEPrescripeElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            skipEPrescribeBtn = new By.ById("btn_skip");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }


    public void skipEPrescripe() {
        click(skipEPrescribeBtn);
    }
}
