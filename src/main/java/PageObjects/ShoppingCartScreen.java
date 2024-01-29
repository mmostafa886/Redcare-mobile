package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.SwipeAndScroll;

public class ShoppingCartScreen extends PageBase {

    private By shoppingCartIcon;
    private By itemTitle;
    private By subTotalAmount;
    private String subTotalAmountResource;
    private By cartScreenToolBar;
    private By cartScreenTitle;
    private By itemPriceEur;
    private By itemPriceCent;
    private By removeItem;
    private By itemCard;
    private  String cardItemResource;
    private By addItem;
    private By currentAmount;
    private By emptyCardLabel;
    private By scrollElement;
    SwipeAndScroll swipeAndScroll;

    public ShoppingCartScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeItemDetailsElements();
    }

    private void intializeItemDetailsElements() {
        String platform = String.valueOf(getDriver().getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            shoppingCartIcon = new By.ById("tab_bar_navigation_cart");
            itemTitle = new By.ById("tv_name");
            subTotalAmount = new By.ById("tv_subtotal_amount");
            subTotalAmountResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/tv_subtotal_amount\")";
            cartScreenToolBar = new By.ById("tb_toolbar");
            cartScreenTitle = new By.ByXPath("//*[@text='Cart']");
            itemPriceEur = new By.ById("tv_price_euro_ab");
            itemPriceCent = new By.ById("tv_price_cent_ab");
            removeItem = new By.ById("btn_subtract");
            itemCard = new By.ById("rv_sc_prodlist");
            cardItemResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/rv_sc_prodlist\")";
            addItem = new By.ById("btn_add");
            currentAmount = new By.ById("btn_quantity");
            emptyCardLabel = new By.ById("tv_message");
            scrollElement = By.id("vg_content_marketplace_cart");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public String getCartContentDesc() {
        return getElementAttribute(shoppingCartIcon, "content-desc");
    }

    public String getFirstCartItemName() {
        return getElementText(itemTitle);
    }

    public String getSubTotalText() {
        swipeAndScroll = new SwipeAndScroll(getDriver());
//        swipeAndScroll.scrollToAnElementByAttribute(driver, subTotalAmountResource);
        swipeAndScroll.scrollVerticallyGest(scrollElement, subTotalAmountResource);
        return getElementText(subTotalAmount);
    }

    public void goToShoppingCart() {
        click(shoppingCartIcon);
        WebElement screenToobarElement = customLocate(cartScreenToolBar);
        WebElement screenTitle = screenToobarElement.findElement(cartScreenTitle);
        getWait().until(ExpectedConditions.visibilityOf(screenTitle));
    }


    public void bringAddRemoveToDisplay(){
        swipeAndScroll = new SwipeAndScroll(getDriver());
//        It is better to use the "UiScrollable" here rather than the "Gestures" as it includes possibility both up & down
//        While using the Gestures allows only scrolling in one direction (either up or down)
        swipeAndScroll.scrollToAnElementByAttribute(getDriver(), cardItemResource);
//        swipeAndScroll.scrollVerticallyGest(scrollElement,cardItemResource);
    }
/*
    public void bringSubTotalToDisplay(){
        swipeAndScroll = new SwipeAndScroll(driver);
        swipeAndScroll.scrollToAnElementByAttribute(driver, cardItemResource);
    }*/
    public void removeItemFromCart() {
        click(removeItem);
        getWait().until(ExpectedConditions.presenceOfElementLocated(emptyCardLabel));
    }

    public boolean validateSubtotal() {
        int quantity = Integer.parseInt(getElementText(currentAmount));
        String itemEur = getElementText(itemPriceEur);
        String itemCent = getElementText(itemPriceCent);
        float itemPrice = Float.parseFloat(itemEur + "." + itemCent);
        System.out.println("The Item price is: " + itemPrice);
        float totalItemPrice = quantity * itemPrice;
        System.out.println("The total price of the item is: " + totalItemPrice);

        String subTotalText = getSubTotalText().replaceAll("[^\\d.,]", "").replace(",", ".");
        float subTotalAmount = Float.parseFloat(subTotalText);
        System.out.println("The Sub-total is: " + subTotalAmount);

        return subTotalAmount == totalItemPrice; //True in case of equal & False if not
    }
}
