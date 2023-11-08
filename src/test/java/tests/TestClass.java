package tests;

import PageObjects.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.AppOperations;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

/**This is not the main testing class
 * but rather a class that is used for trying the TestCases/Methods before being added to the Test Classes
 */
public class TestClass extends TestBase {
    SplashScreen splashScreen;
    CountrySelectScreen countrySelectScreen;
    LanguageSelectScreen languageSelectScreen;
    PersonalizedShoppingScreen pShoppingScreen;
    AuthorizationScreen authorizationScreen;
    EPrescriptionScreen ePrescriptionScreen;
    HomeScreen homeScreen;
    AppOperations appOperations;
    ShoppingCartScreen shoppingCartScreen;
    ItemDetailsScreen itemDetailsScreen;

    @BeforeClass
    public void testSetup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("avd", "RedcareA12");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("platformVersion", "12.0"); //This is commented only for the CI, but it can be used normally when testing locally
        caps.setCapability("app", System.getProperty("user.dir") + "/apps/ShopApotheke.apk");
        caps.setCapability("noReset", true);
        driver = new AndroidDriver(new URL("http://localhost:4723"), caps);

    }

    //======================================
    @AfterClass
    public void classTearDown() {
        String command = "adb emu kill";
        if (null != driver) {
            driver.quit();
        }
        try {
            // Execute the command
            Process process = Runtime.getRuntime().exec(command);
            // Wait for the process to complete
            process.waitFor();
            System.out.println("Test Finished.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //=======================================
    @Test(priority = 10)
    public void testOnboardingTC() throws InterruptedException {
        splashScreen = new SplashScreen(driver);
        splashScreen.splashScreenInvisibility();

        countrySelectScreen = new CountrySelectScreen(driver);
        countrySelectScreen.SelectCountry();

        languageSelectScreen = new LanguageSelectScreen(driver);
        languageSelectScreen.SelectLanguage();

        pShoppingScreen = new PersonalizedShoppingScreen(driver);
        pShoppingScreen.PShoppingAgree();

        authorizationScreen = new AuthorizationScreen(driver);
        authorizationScreen.skipAuthorization();

        ePrescriptionScreen = new EPrescriptionScreen(driver);
        ePrescriptionScreen.skipEPrescripe();

        homeScreen = new HomeScreen(driver);
        String actualText = homeScreen.getWelcomeMessageText();
        Assert.assertTrue(actualText.contains("Welcome to Shop Apotheke"));//Verify the WelcomeText
        sleep(1000);
    }

    @Test(priority = 20)
    public void addRandomItemToShoppingCart() throws InterruptedException {
        homeScreen = new HomeScreen(driver);
        homeScreen.bringCCToDisplay();
        homeScreen.selectRandomCards();

        shoppingCartScreen = new ShoppingCartScreen(driver);
        String emptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(emptyCartContentDesc);
        Assert.assertEquals(emptyCartContentDesc, "Cart");//Make sure the Cart is Empty

        itemDetailsScreen = new ItemDetailsScreen(driver);
        String itemNameFromDetailsScreen = itemDetailsScreen.getItemTitle();
        itemDetailsScreen.addItemToCart();//Add item to Cart with the default properties (without for Ex. changing quantity)
        itemDetailsScreen.increaseQuantity();

        String nonEmptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(nonEmptyCartContentDesc);
        shoppingCartScreen.goToShoppingCart();
        String itemNameFromCart = shoppingCartScreen.getFirstCartItemName();

        Assert.assertTrue(nonEmptyCartContentDesc.contains("new notification"));//Make sure the Cart is not Empty now
        Assert.assertEquals(itemNameFromDetailsScreen, itemNameFromCart);
        Assert.assertTrue(shoppingCartScreen.validateSubtotal());

        sleep(3000);
    }

}
