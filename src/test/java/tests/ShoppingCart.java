package tests;

import PageObjects.*;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.AppOperations;

import java.io.IOException;
import java.net.MalformedURLException;

import static java.lang.Thread.sleep;

public class ShoppingCart extends TestBase {
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
        genericSetUp();
    }

    //======================================
    @AfterClass
    public void testTearDown() {
        tearDown();
    }

    //=======================================
    @Test(priority = 10)
    public void onboardingTest() throws InterruptedException {
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
//        sleep(1000);
    }

    @Test(priority = 20)
    public void addRandomItemToShoppingCart() throws IOException, InterruptedException {
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
        shoppingCartScreen.goToShoppingCart();
        shoppingCartScreen.removeItemFromCart();
//        sleep(1500);
    }

    @Test(priority = 30)
    public void verifyItemInfoBetweenDetailsAndCart() throws InterruptedException, IOException {
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
        String itemPriceFromDetails = itemDetailsScreen.getDefaultPackagePrice();
        itemDetailsScreen.addItemToCart();//Add item to Cart with the default properties (without for Ex. changing quantity)

        shoppingCartScreen.goToShoppingCart();
        String nonEmptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(nonEmptyCartContentDesc);
        shoppingCartScreen.goToShoppingCart();
        String itemNameFromCart = shoppingCartScreen.getFirstCartItemName();
        String itemPriceFromCart = shoppingCartScreen.getSubTotalText();

        Assert.assertTrue(nonEmptyCartContentDesc.contains("new notification"));//Make sure the Cart is not Empty now
        Assert.assertEquals(itemNameFromDetailsScreen, itemNameFromCart);
        Assert.assertEquals(itemPriceFromDetails, itemPriceFromCart);

        shoppingCartScreen.goToShoppingCart();
        shoppingCartScreen.removeItemFromCart();
//        sleep(1500);
    }

    @Test(priority = 40)
    public void chnageQuantityAndCheckPrice() throws InterruptedException, IOException {
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
        shoppingCartScreen.goToShoppingCart();
        String itemNameFromCart = shoppingCartScreen.getFirstCartItemName();

        Assert.assertTrue(nonEmptyCartContentDesc.contains("new notification"));//Make sure the Cart is not Empty now
        Assert.assertEquals(itemNameFromDetailsScreen,itemNameFromCart);
        Assert.assertTrue(shoppingCartScreen.validateSubtotal());

//        sleep(3000);
    }
}
