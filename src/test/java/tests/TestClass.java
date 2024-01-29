package tests;

import PageObjects.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.java.Log;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.AppOperations;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import static java.lang.Thread.sleep;

/**
 * This is not the main testing class
 * but rather a class that is used for trying the TestCases/Methods before being added to the Test Classes
 */
@Log
public class TestClass {

    private AppiumDriver driver;
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

    SoftAssert softAssert;

    @BeforeClass
    public void testSetup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        //Android 12L, 13 & 14 are working fine
        caps.setCapability("avd", "Pixel7ProA13");
//        caps.setCapability("avd", "RedcareA12"); //Android 12 causes a problem
//        caps.setCapability("avd", "Pixel7A14");
//        caps.setCapability("avd", "Pixel7A12L");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("platformVersion", "13.0"); //This is commented only for the CI, but it can be used normally when testing locally
        caps.setCapability("app", System.getProperty("user.dir") + "/apps/ShopApotheke.apk");
/*        caps.setCapability("appPackage", "shop.shop_apotheke.com.shopapotheke");
        caps.setCapability("appActivity", "com.shopapotheke.activities.main.MainActivity");*/
        caps.setCapability("noReset", false);
        caps.setCapability("fullReset", true);
        caps.setCapability("appWaitDuration", 60000); // Set the timeout to 60 seconds (adjust as needed)
        driver = new AndroidDriver(new URL("http://localhost:4723"), caps);

    }

    //======================================
    @AfterClass
    public void classTearDown() {
        String[] command = {"adb", "emu", "kill"};
        if (null != driver) {
            driver.quit();
        }
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process launchProcess = processBuilder.start();
            // You can use the process object to interact with the subprocess if needed
            // Wait for the process to complete (optional)
            int exitCode = launchProcess.waitFor();
            System.out.println("Process exited with code: " + exitCode);
        } catch (Exception e) {
            log.log(Level.ALL, e.getMessage());
//            e.printStackTrace(); // Handle exceptions as needed
        }
    }

    //=======================================
    @Test(priority = 10)
    public void testOnboardingTC() throws Exception {
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
    public void addRandomItemToShoppingCart(){
        appOperations = new AppOperations();
        appOperations.restartApp();

        homeScreen = new HomeScreen(driver);
        homeScreen.bringCCToDisplay();
        homeScreen.selectRandomCards();

        shoppingCartScreen = new ShoppingCartScreen(driver);
        String emptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(emptyCartContentDesc);
        Assert.assertEquals(emptyCartContentDesc, "Cart");//Make sure the Cart is Empty

        itemDetailsScreen = new ItemDetailsScreen(driver);
        itemDetailsScreen.addItemToCart();//Add item to Cart with the default properties (without for Ex. changing quantity)

        shoppingCartScreen.goToShoppingCart();
        String nonEmptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(nonEmptyCartContentDesc);
        Assert.assertTrue(nonEmptyCartContentDesc.contains("new notification"));//Make sure the Cart is not Empty now
        shoppingCartScreen.bringAddRemoveToDisplay();
        shoppingCartScreen.removeItemFromCart();
//        sleep(1000);
    }

    @Test(priority = 30)
    public void verifyItemInfoBetweenDetailsAndCart(){
        appOperations = new AppOperations();
        appOperations.restartApp();

        homeScreen = new HomeScreen(driver);
        homeScreen.bringCCToDisplay();
        homeScreen.selectRandomCards();

        shoppingCartScreen = new ShoppingCartScreen(driver);
        String emptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(emptyCartContentDesc);
        softAssert = new SoftAssert();
        softAssert.assertEquals(emptyCartContentDesc, "Cart");//Make sure the Cart is Empty

        itemDetailsScreen = new ItemDetailsScreen(driver);
        String itemNameFromDetailsScreen = itemDetailsScreen.getItemTitle();
        String itemPriceFromDetails = itemDetailsScreen.getDefaultPackagePrice();
        itemDetailsScreen.addItemToCart();//Add item to Cart with the default properties (without for Ex. changing quantity)

        shoppingCartScreen.goToShoppingCart();
        String nonEmptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(nonEmptyCartContentDesc);
        String itemNameFromCart = shoppingCartScreen.getFirstCartItemName();
        String itemPriceFromCart = shoppingCartScreen.getSubTotalText();

        Assert.assertTrue(nonEmptyCartContentDesc.contains("new notification"));//Make sure the Cart is not Empty now
        Assert.assertEquals(itemNameFromDetailsScreen, itemNameFromCart);
        Assert.assertEquals(itemPriceFromDetails, itemPriceFromCart);

        shoppingCartScreen.bringAddRemoveToDisplay();
        shoppingCartScreen.removeItemFromCart();
//        sleep(1500);
    }
    @Test(priority = 40)
    public void changeQuantityAndCheckPrice() {
        appOperations = new AppOperations();
        appOperations.restartApp();

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

        shoppingCartScreen.goToShoppingCart();
        String nonEmptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(nonEmptyCartContentDesc);
        String itemNameFromCart = shoppingCartScreen.getFirstCartItemName();

        Assert.assertTrue(nonEmptyCartContentDesc.contains("new notification"));//Make sure the Cart is not Empty now
        Assert.assertEquals(itemNameFromDetailsScreen, itemNameFromCart);
        Assert.assertTrue(shoppingCartScreen.validateSubtotal());

//        sleep(3000);
    }

}
