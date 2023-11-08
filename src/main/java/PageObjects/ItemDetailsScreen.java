package PageObjects;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.SwipeAndScroll;

import java.util.List;


public class ItemDetailsScreen extends PageBase {

    public By addToCartBtn;
    public By packageSizeCard;
    public String soldByResource;
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
            addToCartBtn = new By.ById("btn_product_details_footer");
            packageSizeCard = new By.ById("cv_variant");
            soldByResource = "resourceIdMatches(\"shop.shop_apotheke.com.shopapotheke:id/tv_pdp_seller_sender_label\")";
            itemDefaultPriceEur = new By.ById("tv_price_euro_ab");
            itemDefaultPriceCent = new By.ById("tv_price_cent_ab");
            itemTitle = new By.ById("tv_pdp_name");
            goToCartBtn = new By.ById("cl_cart_message");
            quantityIncrease = new By.ById("btn_add");
            quantityDecrease = new By.ById("btn_subtract");
            currentQuantity = new By.ById("btn_quantity");
        } else if ("iOS".equalsIgnoreCase(platform)) {
            System.out.println("The Provided Config is for iOS");
        } else {
            throw new IllegalArgumentException("Driver is not initialized properly.");
        }
    }

    public void addItemToCart() {
        click(addToCartBtn);
        wait.until(ExpectedConditions.presenceOfElementLocated(goToCartBtn));
    }

    public String getDefaultPackagePrice() {
        List<WebElement> packageCards = driver.findElements(packageSizeCard);
        WebElement itemPriceEur;
        WebElement itemPriceCent;
        WebElement defualtPcakageCard = null;
        String itemPriceEurText;
        String itemPriceCentText;
        String itemCurrentPrice;
        swipeAndScroll = new SwipeAndScroll(driver);
        swipeAndScroll.scrollToAnElementByAttribute(driver, soldByResource);
        for (WebElement packageCard : packageCards) {
            if (packageCard.getAttribute("checked").equals("true")) {
                defualtPcakageCard = packageCard;
                break;
            }
        }
        // Use the found element as needed
        if (defualtPcakageCard != null) {
            // Element with checked=true is found, perform actions on it
            System.out.println("Element with checked=true found.");
        } else {
            // Element with checked=true is not found
            System.out.println("No element with checked=true found.");
        }
        itemPriceEur = defualtPcakageCard.findElement(itemDefaultPriceEur);
        itemPriceEurText = itemPriceEur.getText();
//    System.out.println(itemPriceEurText);

        itemPriceCent = defualtPcakageCard.findElement(itemDefaultPriceCent);
        itemPriceCentText = itemPriceCent.getText();
//    System.out.println(itemPriceCentText);

        itemCurrentPrice = "€ "+ itemPriceEurText + "," + itemPriceCentText;
        System.out.println(itemCurrentPrice);

        return itemCurrentPrice;
    }

    public String getItemTitle() {
        return getElementText(itemTitle);
    }

    public String getCurrentQuantity(){
        return getElementText(currentQuantity);
    }

    public void increaseQuantity(){
        int oldAmount = Integer.parseInt(getCurrentQuantity());
        click(quantityIncrease);
        int currentAmount = Integer.parseInt(getCurrentQuantity());
        Assert.assertEquals(currentAmount, oldAmount+1);
    }
}
