package SauceLabs.Tests;

import SauceLabs.Global.Variables;
import SauceLabs.Pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.Assert;
import java.util.concurrent.TimeUnit;

public class TestLoginValid {
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
    public void correctCredentials() {
        variable = new Variables();
        loginPage = new LoginPage(driver);
        loginPage.loginToSite(variable.usernameValid, variable.password);
        Assert.assertEquals(driver.getCurrentUrl(), variable.homepageURL);
    }

    @Test
    public void problemUsername() {
        loginPage = new LoginPage(driver);
        variable = new Variables();
        loginPage.loginToSite(variable.problemUser, variable.password);
        Assert.assertEquals(driver.getCurrentUrl(), variable.homepageURL);
    }

    @Test
    public void glitchUsername() {
        loginPage = new LoginPage(driver);
        variable = new Variables();
        loginPage.loginToSite(variable.glitchUser, variable.password);
        Assert.assertEquals(driver.getCurrentUrl(), variable.homepageURL);
    }

    @AfterMethod
    public void logout() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        homePage.clickLogout();
        loginPage.verifyLoginButton();
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}