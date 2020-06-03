package SauceLabs.Pages;

import SauceLabs.Global.Keywords;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage {
    WebDriver driver;
    Keywords keyword;

    //List of locators on home page
    public By hamburgerMenu = By.xpath("//*[contains(@class, 'bm-burger-button')]");
    public By logoutButton = By.id("logout_sidebar_link");
    public By addToCartButtonItem4 = By.xpath("//*[contains(text(), 'Sauce Labs Backpack')]//..//..//..//*[contains(@class, 'btn_primary btn_inventory')]");
    public By addItemsToCart = By.xpath("//*[contains(@class, 'btn_primary btn_inventory')]");
    public By itemList = By.id("inventory_container");
    public By cartItemCount = By.xpath("//*[contains(@class, 'fa-layers-counter shopping_cart_badge')]");
    public By removeButton = By.xpath("//*[contains(@class, 'btn_secondary btn_inventory')]");
    public By productSortDropdown = By.xpath("//*[contains(@class, 'product_sort_container')]");
    public By itemName = By.xpath("//*[contains(@class, 'inventory_item_name')]");
    public By itemPrice = By.xpath("//*[contains(@class, 'inventory_item_price')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLogout() {
        driver.findElement(hamburgerMenu).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        driver.findElement(logoutButton).click();
    }

    public void waitForItemsToLoad() {
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemList));
    }

    public void addItemsToCart(int itemCount) {
        System.out.println("Number of items to be added to cart: " + itemCount);
        List<WebElement> count = driver.findElements(addItemsToCart);
        //int itemCount = count.size();
        for (int i=0; i<itemCount; i++)
        {
            //add item to the cart
            count.get(i).click();
            List<WebElement> added = driver.findElements(removeButton);
            int addedItems = added.size();
            System.out.println("Current number if items added to cart: " + addedItems);
            //verify number of items added to cart
            Assert.assertEquals(addedItems, i+1);
        }
    }

    public String getItemCount() {
        return driver.findElement(cartItemCount).getText();
    }

    public void removeItemsToCart() {
        if (driver.findElements(cartItemCount).size() > 0) {
            List<WebElement> count = driver.findElements(removeButton);
            int itemCount = count.size();
            System.out.println("remove " + itemCount + " item/s in cart");
            for (int i = 1; i <= itemCount; i++) {
                count.get(i-1).click();
                List<WebElement> addedInCart = driver.findElements(removeButton);
                int remainingItems = addedInCart.size();
                //System.out.println("Current number if items added to cart: " + getItemCount());
                //verify number of items in cart is reduced
                int current = itemCount - i;
                Assert.assertEquals(current, remainingItems);
                System.out.println("current items in cart: " + remainingItems);
            }
        }
        else {
            System.out.println("No items added to the cart");
        }
    }

    public void selectOptionFromDropdown(String option) {
        Select sortOption = new Select(driver.findElement(productSortDropdown));
        sortOption.selectByValue(option);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println("Clicked " + option);
    }

    public void verifyAscendingSort(By sortedBy) {
        if(sortedBy == itemName) {
            ArrayList<String> itemList = new ArrayList<>();
            List<WebElement> elementList= driver.findElements(sortedBy);
            int itemCount = elementList.size();
            System.out.println("total items displayed: " + itemCount);
            //get and store either item name in the list
            for(WebElement we:elementList){
                itemList.add(we.getText());
            }
            System.out.println("List in the page: " + itemList);
            //sort via ascending order
            ArrayList<String> sortedList = new ArrayList<>();
            for(String s:itemList) {
                sortedList.add(s);
            }
            Collections.sort(sortedList);
            System.out.println("Ascending List: " + sortedList);
            Assert.assertTrue(sortedList.equals(itemList));
        }
        else if(sortedBy == itemPrice) {
            List<Integer> itemList = new ArrayList<>();
            List<WebElement> elementList= driver.findElements(sortedBy);
            int itemCount = elementList.size();
            System.out.println("total items displayed: " + itemCount);
            //get and store either item name in the list
            for(WebElement we:elementList){
                //String price$ = we.getText();
                //int price = Integer.parseInt(price$);
                String itemPrice = we.getText().replaceAll("$", "");
                int price = Integer.parseInt(itemPrice);
                itemList.add(price);
                //itemList.add(we.getText());
            }
            System.out.println("List in the page: " + itemList);
            //sort via ascending order
            List<Integer> sortedList = new ArrayList<>();
            for(Integer s:sortedList) {
                sortedList.add(s);
            }
            Collections.sort(sortedList);
            System.out.println("Ascending List: " + sortedList);
            Assert.assertTrue(sortedList.equals(itemList));
        }
    }

    public void verifyDescendingSort(By sortedBy) {
        ArrayList<String> itemList = new ArrayList<>();
        List<WebElement> elementList= driver.findElements(sortedBy);
        int itemCount = elementList.size();
        System.out.println("total items displayed: " + itemCount);
        //get and store either item name or item price in the list
        for(WebElement we:elementList){
            itemList.add(we.getText());
        }
        System.out.println(itemList);
        //sort via ascending order
        ArrayList<String> sortedList = new ArrayList<>();
        for(String s:itemList){
            sortedList.add(s);
        }
        Collections.sort(sortedList);
        Collections.reverse(sortedList);
        System.out.println(sortedList);
        Assert.assertTrue(sortedList.equals(itemList));
    }

    public void clickItemName(String id) {
        String xpath = "//*[@id='" + id +"']/*[contains(@class, 'inventory_item_name')]";
        System.out.println(xpath);
        driver.findElement(By.xpath(xpath)).click();
    }

    public void verifyCanOnlyAddOnePerItem(String id) {
        String xpath = "//*[@id='" + id + "']//..//..//*[contains(@class, 'btn_primary btn_inventory')]";
        System.out.println(xpath);
        driver.findElement(By.xpath(xpath)).click();
        keyword = new Keywords(driver);
        String added = "//*[@id='" + id + "']//..//..//*[contains(@class, 'btn_secondary btn_inventory')]";
        By addedItem = By.xpath(added);
        keyword.isElementPresent(addedItem);
    }
}
