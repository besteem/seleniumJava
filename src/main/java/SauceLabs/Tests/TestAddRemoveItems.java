package SauceLabs.Tests;
import SauceLabs.Global.Keywords;
import SauceLabs.Global.Variables;
import SauceLabs.Pages.DetailsPage;
import SauceLabs.Pages.HomePage;
import SauceLabs.Pages.LoginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class TestAddRemoveItems {
    public WebDriver driver ;
    Variables variable;
    LoginPage loginPage;
    HomePage homePage;
    DetailsPage detailsPage;
    Keywords keyword;

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
    public void emptyItemsInCart() {
        homePage = new HomePage(driver);
        variable = new Variables();
        driver.get(variable.productListURL);
        homePage.waitForItemsToLoad();
        homePage.removeItemsToCart();
    }

    @Test
    public void addAnItemToCart() {
        homePage = new HomePage(driver);
        homePage.addItemsToCart(1);
        System.out.println("Item in cart: " + homePage.getItemCount());
    }

    @Test
    public void addMultipleItemsToCart() {
        homePage = new HomePage(driver);
        homePage.addItemsToCart(6);
        System.out.println("Item in cart: " + homePage.getItemCount());
    }

    @Test
    public void detailsPageAddItemInCart() {
        homePage = new HomePage(driver);
        homePage.clickItemName("item_3_title_link");
        detailsPage = new DetailsPage(driver);
        detailsPage.clickAddToCartButton();
        detailsPage.verifyItemInCart();
    }

    @Test
    public void detailsPageCanOnlyAddMaxOfOnePerItem() {
        homePage = new HomePage(driver);
        homePage.clickItemName("item_3_title_link");
        detailsPage = new DetailsPage(driver);
        detailsPage.clickAddToCartButton();
        detailsPage.verifyCanOnlyAddOneInCart();
    }

    @Test
    public void addedItemInDetailsPageCannotBeAddedInListPage() {
        homePage = new HomePage(driver);
        homePage.clickItemName("item_3_title_link");
        detailsPage = new DetailsPage(driver);
        detailsPage.clickAddToCartButton();
        detailsPage.verifyItemInCart();
        detailsPage.clickBackButton();
        Assert.assertEquals(driver.getCurrentUrl(), variable.homepageURL);
        keyword = new Keywords(driver);
        keyword.isElementPresent(homePage.removeButton);
    }

    //in progress
    @Test
    public void listPageCanOnlyAddMaxOfOnePerItem() {
        homePage = new HomePage(driver);
        homePage.verifyCanOnlyAddOnePerItem("item_5_title_link");

    }

    @AfterMethod
    public void removeItemsInCart() {
        homePage = new HomePage(driver);
        variable = new Variables();
        driver.get(variable.homepageURL);
        homePage.removeItemsToCart();
    }

    @AfterTest
    public void logoutAndCloseBrowser() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        homePage.clickLogout();
        loginPage.verifyLoginButton();
        driver.quit();
    }
}
