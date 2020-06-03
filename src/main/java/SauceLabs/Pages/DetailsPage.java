package SauceLabs.Pages;

import SauceLabs.Global.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class DetailsPage {
    WebDriver driver;
    Keywords keyword;

    //List of locators on home page
    public By addToCartButton = By.xpath("//*[contains(@class, 'btn_primary btn_inventory')]");
    public By cartItemCount = By.xpath("//*[contains(@class, 'fa-layers-counter shopping_cart_badge')]");
    public By backButton = By.xpath("//*[contains(@class, 'inventory_details_back_button')]");
    public By removeButton = By.xpath("//*[contains(@class, 'btn_secondary btn_inventory')]");

    public DetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAddToCartButton() {
        driver.findElement(addToCartButton).click();
    }

    public void verifyItemInCart() {
        int itemCount = Integer.parseInt(getItemCount());
        Assert.assertEquals(itemCount, 1);
    }

    public String getItemCount() {
        return driver.findElement(cartItemCount).getText();
    }

    public void clickBackButton() {
        driver.findElement(backButton).click();
    }

    public void verifyCanOnlyAddOneInCart() {
        verifyItemInCart();
        keyword = new Keywords(driver);
        keyword.isElementPresent(removeButton);
    }
}
