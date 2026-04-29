package tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import configuration.CSVUtils;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;

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
    @DataProvider(name = "SearchSubcategoryData")
    public Object[][] getSearchSubcategoryData() throws Exception {
        return CSVUtils.getTestData("SearchSubcategoryData.csv");
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

    @Test(dataProvider = "SearchSubcategoryData")
    public void searchInSubcategoriesTest(String email, String password, String searchTerm, 
                                          String category, String expectedNoProductMsg, String expectedProduct) {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        SearchPage search = new SearchPage(driver);
        AccountPage account = new AccountPage(driver);

        home.goToLogin();
        login.login(email, password);

        search.searchButtonIconClick();

        search.keywordsInputClick(searchTerm);
        search.selectCategory(category);
        
        search.searchButtonClick();

        Assert.assertEquals(search.getResultMessage(), expectedNoProductMsg);

        search.subCategoryClick();
        search.searchButtonClick();
       
        Assert.assertTrue(search.isProductDisplayed(), expectedProduct + " was not found on the page.");

        account.logOut();
    }

    


}