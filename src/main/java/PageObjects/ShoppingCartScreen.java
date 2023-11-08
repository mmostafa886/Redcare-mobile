package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.SwipeAndScroll;


public class ShoppingCartScreen extends PageBase {

    public By shoppingCartIcon;
    public By itemTitle;
    public By subTotalAmount;
    public String subTotalAmountResource;
    public By cartScreenToolBar;
    public By cartScreenTitle;
    public By itemPriceEur;
    public By itemPriceCent;
    public By removeItem;
    public By addItem;
    public By currentAmount;
    public By emptyCardLabel;
    SwipeAndScroll swipeAndScroll;

    public ShoppingCartScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeItemDetailsElements();
    }

    public void intializeItemDetailsElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
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
            addItem = new By.ById("btn_subtract");
            currentAmount = new By.ById("btn_quantity");
            emptyCardLabel = new By.ById("tv_message");
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
        swipeAndScroll = new SwipeAndScroll(driver);
        swipeAndScroll.scrollToAnElementByAttribute(driver, subTotalAmountResource);
        return getElementText(subTotalAmount);
    }

    public void goToShoppingCart() {
        click(shoppingCartIcon);
        WebElement screenToobarElement = customLocate(cartScreenToolBar);
        WebElement screenTitle = screenToobarElement.findElement(cartScreenTitle);
        wait.until(ExpectedConditions.visibilityOf(screenTitle));
    }

    public void removeItemFromCart() {
        click(removeItem);
        wait.until(ExpectedConditions.presenceOfElementLocated(emptyCardLabel));
    }

    public boolean validateSubtotal() {
        int quantity = Integer.parseInt(getElementText(currentAmount));
        String itemEur = getElementText(itemPriceEur);
        String itemCent = getElementText(itemPriceCent);
        float itemPrice = Float.parseFloat(itemEur + "." + itemCent);
        float totalItemPrice = quantity * itemPrice;
        System.out.println("The total price of the item is: " + totalItemPrice);

        String subTotalText = getSubTotalText().replaceAll("[^\\d.,]", "").replace(",", ".");
        float subTotalAmount = Float.parseFloat(subTotalText);
        System.out.println("The Sub-total is: " + subTotalAmount);

        return subTotalAmount == totalItemPrice; //True in case of equal & False if not
    }
}
