package SauceLabs.Tests;

import SauceLabs.Global.Variables;
import SauceLabs.Pages.HomePage;
import SauceLabs.Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class TestFiltering {
    public WebDriver driver ;
    Variables variable;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeTest
    public void openBrowserAndLogin() {
        variable = new Variables();
        System.out.println("launching firefox browser");
        System.setProperty("webdriver.gecko.driver", variable.driverPathFirefox);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(variable.loginpageURL);
        //Verify correct landing page
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Assert.assertEquals(driver.getTitle(), variable.loginpageTitle);
        //login to site
        loginPage = new LoginPage(driver);
        loginPage.loginToSite(variable.usernameValid, variable.password);
        Assert.assertEquals(driver.getCurrentUrl(), variable.homepageURL);
    }

    @BeforeMethod
    public void refreshPage() {
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void sortNameAscending() {
        homePage = new HomePage(driver);
        variable = new Variables();
        homePage.selectOptionFromDropdown(variable.nameAscendingOption);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homePage.verifyAscendingSort(homePage.itemName);
    }

    @Test
    public void sortNameDescending() {
        homePage = new HomePage(driver);
        variable = new Variables();
        homePage.selectOptionFromDropdown(variable.nameDescendingOption);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homePage.verifyDescendingSort(homePage.itemName);
    }

    /* Test failing --- need to identify how to sort item price
    @Test
    public void sortPriceAscending() {
        homePage = new HomePage(driver);
        variable = new Variables();
        homePage.selectOptionFromDropdown(variable.priceAscendingOption);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homePage.verifyAscendingSort(homePage.itemPrice);
    }

    @Test
    public void sortPriceDescending() {
        homePage = new HomePage(driver);
        variable = new Variables();
        homePage.selectOptionFromDropdown(variable.priceDescendingOption);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homePage.verifyDescendingSort(homePage.itemPrice);
    } */

    @AfterTest
    public void logoutAndCloseBrowser() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        homePage.clickLogout();
        loginPage.verifyLoginButton();
        driver.quit();
    }
}
