package tests;

import PageObjects.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.AppOperations;

import java.net.MalformedURLException;


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

    SoftAssert softAssert;

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
    public void onboardingTest() {
        splashScreen = new SplashScreen(getDriver());
        splashScreen.splashScreenInvisibility();

        countrySelectScreen = new CountrySelectScreen(getDriver());
        countrySelectScreen.SelectCountry();

        languageSelectScreen = new LanguageSelectScreen(getDriver());
        languageSelectScreen.SelectLanguage();

        pShoppingScreen = new PersonalizedShoppingScreen(getDriver());
        pShoppingScreen.PShoppingAgree();

        authorizationScreen = new AuthorizationScreen(getDriver());
        authorizationScreen.skipAuthorization();

        ePrescriptionScreen = new EPrescriptionScreen(getDriver());
        ePrescriptionScreen.skipEPrescripe();

        homeScreen = new HomeScreen(getDriver());
        String actualText = homeScreen.getWelcomeMessageText();
        Assert.assertTrue(actualText.contains("Welcome to Shop Apotheke"));//Verify the WelcomeText
        //        sleep(1000);
    }

    @Test(priority = 20)
    public void addRandomItemToShoppingCart(){
        appOperations = new AppOperations();
        appOperations.restartApp();

        homeScreen = new HomeScreen(getDriver());
        homeScreen.bringCCToDisplay();
        homeScreen.selectRandomCards();

        shoppingCartScreen = new ShoppingCartScreen(getDriver());
        String emptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(emptyCartContentDesc);
        Assert.assertEquals(emptyCartContentDesc, "Cart");//Make sure the Cart is Empty

        itemDetailsScreen = new ItemDetailsScreen(getDriver());
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

        homeScreen = new HomeScreen(getDriver());
        homeScreen.bringCCToDisplay();
        homeScreen.selectRandomCards();

        shoppingCartScreen = new ShoppingCartScreen(getDriver());
        String emptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(emptyCartContentDesc);
        softAssert = new SoftAssert();
        softAssert.assertEquals(emptyCartContentDesc, "Cart");//Make sure the Cart is Empty

        itemDetailsScreen = new ItemDetailsScreen(getDriver());
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

        homeScreen = new HomeScreen(getDriver());
        homeScreen.bringCCToDisplay();
        homeScreen.selectRandomCards();

        shoppingCartScreen = new ShoppingCartScreen(getDriver());
        String emptyCartContentDesc = shoppingCartScreen.getCartContentDesc();
        System.out.println(emptyCartContentDesc);
        Assert.assertEquals(emptyCartContentDesc, "Cart");//Make sure the Cart is Empty

        itemDetailsScreen = new ItemDetailsScreen(getDriver());
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
