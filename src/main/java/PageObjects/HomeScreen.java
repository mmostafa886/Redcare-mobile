package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.SwipeAndScroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeScreen extends PageBase {

    SwipeAndScroll swipeAndScroll;
    public By welcomeMessage;
    public By recommendationCardsList;
    public String recommndCListResource;
    public String contentCardTitleResource;
    public By contentCardTitle;
    public String sampleElement;

    public HomeScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializePShoppingElements();
    }

    public void intializePShoppingElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            welcomeMessage = By.id("tv_home_welcome_title");
            recommendationCardsList = By.id("rv_list");
            recommndCListResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/rv_list\")";
            contentCardTitle = By.id("tv_product_name");
            contentCardTitleResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/tv_product_name\")";
            sampleElement = "description(\"//*[@text='Grippostad C® bei Erkältung und grippalen Infekten']\")";
            //Full Locator
        } else if ("iOS".equalsIgnoreCase(platform)) {
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public String getWelcomeMessageText() {
        return getElementText(welcomeMessage);
    }

    public void bringCCToDisplay() {
        swipeAndScroll = new SwipeAndScroll(driver);
        swipeAndScroll.scrollToAnElementByAttribute(driver, recommndCListResource);
    }

    //    The problem I faced with this function is that, once the screen is swiped, the content card is not present in the DOManymore
//    So, this doesn't guarantee that the selected random card will be present in the DOM
    public void selectRandomCards_Old() {
        List<WebElement> contentCards = new ArrayList<>();
        boolean cardsFound = true;
        WebElement newVisibleCards = null;
        while (cardsFound) {
            elementPresence(contentCardTitle);
            WebElement visibleCards = driver.findElements(contentCardTitle).get(0);

            swipeAndScroll = new SwipeAndScroll(driver);
            swipeAndScroll.scrollHorizontally(recommendationCardsList);
            int cardsLength = contentCards.size();
//            Get the last WebElement from the contentCards list
            if (cardsLength != 0) {
                newVisibleCards = contentCards.get(cardsLength - 1);
            }
            if (visibleCards.equals(newVisibleCards)) {
                cardsFound = false;
                System.out.println(contentCards.size());
            }
            contentCards.add(visibleCards);
        }
        Random random = new Random();
        int randomIndex = random.nextInt(contentCards.size());
        String itemTitle = contentCards.get(randomIndex).getText();
        System.out.println(itemTitle);
        swipeAndScroll.scrollHorizontalAndSelectItem(recommendationCardsList, "//*[@text='" + itemTitle + "']");

    }


    public void selectRandomCards() {
        int numberOfCards = 10;
        Random random = new Random();
        int randomCardIndex = random.nextInt(numberOfCards);

        for (int i = 0; i < randomCardIndex; i++) {
            swipeAndScroll.scrollHorizontally(recommendationCardsList);
        }
        System.out.println("The Item index is: " + randomCardIndex + " and the Item Title is: " + getElementText(contentCardTitle));
        click(contentCardTitle);
    }
}
