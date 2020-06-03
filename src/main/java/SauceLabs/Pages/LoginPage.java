package SauceLabs.Pages;
import SauceLabs.Global.Variables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LoginPage {
    WebDriver driver;
    Variables variable;

    //Locators used for login page
    public By usernameTextField = By.id("user-name");
    public By passwordTextField = By.id("password");
    public By loginLogo = By.xpath("//*[contains(@class, 'login_logo')]");
    public By loginButton = By.xpath("//*[contains(@class, 'btn_action')]");
    public By loginErrorMessage = By.xpath("//*[@data-test = 'error']");
    public By errorButton = By.xpath("//*[@class='error-button']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputUsername(String username){
        driver.findElement(usernameTextField).sendKeys(username);
    }

    public void inputPassword(String password){
        driver.findElement(passwordTextField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void loginToSite(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickLoginButton();
    }

    public void verifyPageTitle() {
        variable = new Variables();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //System.out.println("OK implicit wait");
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertEquals(driver.getTitle(), variable.loginpageTitle);
    }

    public void verifyLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLogo));
    }

    public String getLoginErrorMessage() {
        return driver.findElement(loginErrorMessage).getText();
    }

    public void clickErrorButton() {
        driver.findElement(errorButton).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginErrorMessage));
    }

}
