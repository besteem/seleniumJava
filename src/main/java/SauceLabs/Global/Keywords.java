package SauceLabs.Global;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Keywords {
    WebDriver driver;

    public Keywords(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isElementPresent(By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementVisible(By locatorKey){
        return driver.findElement(locatorKey).isDisplayed();
    }
}
