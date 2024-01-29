package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.SwipeAndScroll;

import java.util.Random;

public class HomeScreen extends PageBase {

    SwipeAndScroll swipeAndScroll;
    public By welcomeMessage;
    public By recommendationCardsList;
    public String recommndCListResource;
    public String contentCardTitleResource;

    public By contentCardTitle;
    public String sampleElement;

    private By webPopUp;

    private By sliderElement;

    public HomeScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializePShoppingElements();
    }

    private void intializePShoppingElements() {
        String platform = String.valueOf(getDriver().getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            welcomeMessage = By.id("tv_home_welcome_title");
            recommendationCardsList = By.id("rv_list");
            recommndCListResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/rv_list\")";
            contentCardTitle = By.id("tv_product_name");
            contentCardTitleResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/tv_product_name\")";
            sampleElement = "description(\"//*[@text='Grippostad C® bei Erkältung und grippalen Infekten']\")";
            webPopUp = By.className("android.webkit.WebView");
            sliderElement = By.id("nsv_scroll_view");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            System.out.println("The Provided Config is for iOS");
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public String getWelcomeMessageText(){
       removeWebPopUp(webPopUp);
        return getElementText(welcomeMessage);
    }

    public void bringCCToDisplay() {
        //Scroll down till reaching the "Recommendation" content cards section
        swipeAndScroll = new SwipeAndScroll(getDriver());
//        swipeAndScroll.scrollToAnElementByAttribute(driver, recommndCListResource);
        swipeAndScroll.scrollVerticallyGest(sliderElement,"tv_product_name");
        System.out.println("ContentCards in Display");
    }

    //    The problem I faced with this function is that, once the screen is swiped, the content card is not present in the DOManymore
    //    So, this doesn't guarantee that the selected random card will be present in the DOM
    public void selectRandomCards() {
        //The max numberOfCards displayed in the app home screen needs to be provided here
        int numberOfCards = 10;
        //Getting a random number in the range between 0 and numberOfCards
        Random random = new Random();
        int randomCardIndex = random.nextInt(numberOfCards);
        //Perform a number of swipes equals to the random number generated in the previous step
        for (int i = 0; i < randomCardIndex; i++) {
            swipeAndScroll.scrollHorizontally(recommendationCardsList);
        }
        //Print out the index(# Swipes)
        System.out.println("The Item index is: " + randomCardIndex + " and the Item Title is: " + getElementText(contentCardTitle));
        //Click here means select this card in order to be redirected to its details
        click(contentCardTitle);
    }
}
