package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class PersonalizedShoppingScreen extends PageBase {

    public By pShoppoingAgreeBtn;

    public PersonalizedShoppingScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializePShoppingElements();
    }

    public void intializePShoppingElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            pShoppoingAgreeBtn = By.id("btn_agreed");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void PShoppingAgree() {
        click(pShoppoingAgreeBtn);
    }


}
