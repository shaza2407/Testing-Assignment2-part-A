package tests;

import base.BaseTest;
import configuration.CSVUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

import java.time.Duration;

// Scenario 7:
// 1- Login by any valid user
// 2- Enter any name in "search" input box (Ex: "Mac")
// 3- Click on "Search"
// 4- All Mac products displayed (means all product's names contain "Mac")
// 5- Log out

//scenario 8
//1- Login by any valid user
//2- Click on "Search" icon
//3- Enter "Apple" in Search Keyword
//4- Choose "components"
//5- No products found
//6- Check on "Search in subCatergories"
//7- "Apple Cinema 30" displayed
//8- Log out

public class SearchTests extends BaseTest {
    @DataProvider(name = "validUser")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("validUser.csv");
    }

    @Test(dataProvider = "validUser")
    public void searchTest(String email, String password) {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);            //log in with email and password
        login.login(email, password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("account"));      //wait until logged in

        SearchPage search = new SearchPage(driver);
        search.search("Mac");           //search for "Mac"

        Assert.assertTrue(search.allProductsContain("Mac"), "Not all products contain 'Mac'");  //check if all products contain "Mac"

        AccountPage account = new AccountPage(driver);          //logout
        account.logOut();
    }


}