package SauceLabs.Tests;

import SauceLabs.Global.Variables;
import SauceLabs.Pages.HomePage;
import SauceLabs.Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class TestLoginInvalid {
    public WebDriver driver ;
    Variables variable;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeTest
    public void openBrowser() {
        variable = new Variables();
        System.out.println("launching firefox browser");
        System.setProperty("webdriver.gecko.driver", variable.driverPathFirefox);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void navigateToLoginPage() {
        variable = new Variables();
        driver.get(variable.loginpageURL);
        //Verify page title
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Assert.assertEquals(driver.getTitle(), variable.loginpageTitle);
    }

    @Test
    public void blankUsername() {
        variable = new Variables();
        loginPage = new LoginPage(driver);
        loginPage.loginToSite("", variable.password);
        Assert.assertEquals(loginPage.getLoginErrorMessage(), variable.blankUsernameMessage);
    }

    @Test
    public void blankPassword() {
        variable = new Variables();
        loginPage = new LoginPage(driver);
        loginPage.loginToSite(variable.usernameValid, "");
        Assert.assertEquals(loginPage.getLoginErrorMessage(), variable.blankPasswordMessage);
    }

    @Test
    public void invalidUsername() {
        variable = new Variables();
        loginPage = new LoginPage(driver);
        loginPage.loginToSite(variable.invalidUsername, variable.password);
        Assert.assertEquals(loginPage.getLoginErrorMessage(), variable.usernamePasswordNotMatch);
    }

    @Test
    public void invalidPassword() {
        variable = new Variables();
        loginPage = new LoginPage(driver);
        loginPage.loginToSite(variable.usernameValid, variable.incorrectPassword);
        Assert.assertEquals(loginPage.getLoginErrorMessage(), variable.usernamePasswordNotMatch);
    }

    @Test
    public void lockedOutUser() {
        variable = new Variables();
        loginPage = new LoginPage(driver);
        loginPage.loginToSite(variable.userLockedOut, variable.password);
        Assert.assertEquals(loginPage.getLoginErrorMessage(), variable.lockedOutUserMessage);
    }

    @AfterMethod
    public void clickErrorButton(){
        loginPage = new LoginPage(driver);
        loginPage.clickErrorButton();
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}
