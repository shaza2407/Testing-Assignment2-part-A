package tests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import configuration.CSVUtils;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;

// Scenario 8:
// 1- Login by any valid user
// 2- Enter any name in "search" input box (Ex: "Mac")
// 3- Click on "Search"
// 4- All Mac products displayed (means all product's names contain "Mac")
// 5- Log out

//scenario 9:
//1- Login by any valid user
//2- Click on "Search" icon
//3- Enter "Apple" in Search Keyword
//4- Choose "components"
//5- No products found
//6- Check on "Search in subCatergories"
//7- "Apple Cinema 30" displayed
//8- Log out

public class SearchTests extends BaseTest {
    @DataProvider(name = "SearchData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("SearchData.csv");
    }
    @DataProvider(name = "SearchSubcategoryData")
    public Object[][] getSearchSubcategoryData() throws Exception {
        return CSVUtils.getTestData("SearchSubcategoryData.csv");
    }


    @Test(dataProvider = "SearchData")
    public void searchTest(String email, String password , String productName) {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);            //log in with email and password

        SearchPage search = new SearchPage(driver);
        search.search(productName);           //search for "Mac"

        Assert.assertTrue(search.allProductsContain(productName), "Not all products contain " + productName);  //check if all products contain "Mac"

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
        login.login(email, password);               //log in with a valid user

        search.search(searchTerm);              //click on search icon and enter search for search term
        search.selectCategory(category);        //select category
        search.searchButtonClick();             //click on search button

        Assert.assertEquals(search.getResultMessage(), expectedNoProductMsg);  //check if no products found

        search.subCategoryClick();              //click on "Search in subcategories"
        search.searchButtonClick();             //click on search button
       
        Assert.assertTrue(search.isProductDisplayed(expectedProduct), expectedProduct + " was not found on the page.");        //check if product is displayed

        account.logOut();           //logout
    }
}