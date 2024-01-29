package PageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.SwipeAndScroll;

import java.util.List;


public class ItemDetailsScreen extends PageBase {

    public By addToCartBtn;
    public By packageSizeCard;
    public By shoppingCartIcon;
    public String packageSizeResource;
    public String soldByResource;

    public By soldBy;
    public By goToCartBtn;
    public By itemDefaultPriceEur;
    public By itemDefaultPriceCent;
    public By itemTitle;
    public By quantityIncrease;
    public By quantityDecrease;
    public By currentQuantity;
    SwipeAndScroll swipeAndScroll;

    public ItemDetailsScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
        intializeItemDetailsElements();
    }

    public void intializeItemDetailsElements() {
        String platform = String.valueOf(driver.getCapabilities().getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            shoppingCartIcon = new By.ById("tab_bar_navigation_cart");
            addToCartBtn = new By.ById("btn_product_details_footer");
            packageSizeResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/sv_variants_container\")";
            packageSizeCard = new By.ById("cv_variant");
            soldByResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/tv_pdp_seller_sender_label\")";
            soldBy = new By.ById("tv_pdp_seller_sender_label");
            itemDefaultPriceEur = new By.ById("tv_price_euro_ab");
            itemDefaultPriceCent = new By.ById("tv_price_cent_ab");
            itemTitle = new By.ById("tv_pdp_name");
            goToCartBtn = new By.ById("cl_cart_message");
            quantityIncrease = new By.ById("btn_add");
            quantityDecrease = new By.ById("btn_subtract");
            currentQuantity = new By.ById("btn_quantity");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            //In case we want to use iOS, we need to provide the corresponding locators as done for "Android"
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void addItemToCart() {
        click(addToCartBtn);
        //This wait is mandatory to make sure that the Item is already added to the cart before proceeding to the next step
        wait.until(ExpectedConditions.presenceOfElementLocated(goToCartBtn));
    }

    public String getDefaultPackagePrice() {
        /*For every Item, there is a default package (Ex. 50 bills, 100 ml) card, in this method we are getting the price
        of this item. It requires processing as it is not placed in one element that can be located through the normal ways
         */
        //Getting a list of all the package/size cards
        List<WebElement> packageCards = driver.findElements(packageSizeCard);
        //The target of each of the following elements can be understood from the its names
        WebElement itemPriceEur;
        WebElement itemPriceCent;
        WebElement defualtPcakageCard = null;
        String itemPriceEurText;
        String itemPriceCentText;
        String itemCurrentPrice;
        //Here we need to scroll to a lower section of the item details screen in order to be able to the get price components
        swipeAndScroll = new SwipeAndScroll(driver);
//        wait.until(ExpectedConditions.elementToBeClickable(soldBy));
        swipeAndScroll.scrollToAnElementByAttribute(driver, packageSizeResource);
        /*The next piece of code (the for loop & its contents) gets the default selected card (each item has one)
        by getting the card with the property "checked" equals "true" (only one card has this property set true)
         */
        for (WebElement packageCard : packageCards) {
            if (packageCard.getAttribute("checked").equals("true")) {
                defualtPcakageCard = packageCard;
                break;
            }
        }
        // Use the default card as needed
        if (defualtPcakageCard != null) {
            // Element with checked=true is found, perform actions on it
            System.out.println("Element with checked=true found.");
        } else {
            // Element with checked=true is not found
            System.out.println("No element with checked=true found.");
        }
        //Here, we are locating the EUR part of the price provided in the default package size
        itemPriceEur = defualtPcakageCard.findElement(itemDefaultPriceEur);
        //get the EUR price element text for further processing
        itemPriceEurText = itemPriceEur.getText();
        //System.out.println(itemPriceEurText);

        //Here, we are locating the Cent part of the price provided in the default package size
        itemPriceCent = defualtPcakageCard.findElement(itemDefaultPriceCent);
        //get the Cent price element text for further processing
        itemPriceCentText = itemPriceCent.getText();
        //System.out.println(itemPriceCentText);

        //Format the Item price as "€ EUR,Cent"
        itemCurrentPrice = "€ " + itemPriceEurText + "," + itemPriceCentText;
        System.out.println(itemCurrentPrice);

        return itemCurrentPrice;
    }

    public String getItemTitle() {
        return getElementText(itemTitle);
    }

    public String getCurrentQuantity() {
        return getElementText(currentQuantity);
    }

    //Increase the quantity to be put in the Shopping Cart
    public void increaseQuantity() {
        int oldAmount = Integer.parseInt(getCurrentQuantity());
        System.out.println("Old Amount: "+oldAmount);
        click(quantityIncrease);
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(currentQuantity,String.valueOf(oldAmount+1)));

        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(currentQuantity, String.valueOf(oldAmount))));
        int currentAmount = Integer.parseInt(getCurrentQuantity());
        wait.until(ExpectedConditions.textToBePresentInElementLocated(currentQuantity,String.valueOf(currentAmount)));
        System.out.println("Current Amount: "+currentAmount);
        wait.until(ExpectedConditions.attributeContains(shoppingCartIcon,"content-desc", String.valueOf(currentAmount)));
    }
}
