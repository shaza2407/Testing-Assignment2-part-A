package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import configuration.CSVUtils;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
//scenario 7
//1- Login by any valid user
//2- Click on "Phones & PDAs"
//3- Sort by name "A--Z"
//4- The items sorted alphapitcaly ascending
//5- Sort by name "Z--A"
//6-The items sorted alphapitcaly descending
//7- Log out
public class SortingTests extends BaseTest {
    @DataProvider(name = "SortingData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("SortingData.csv"); 
    }

    @Test(dataProvider = "SortingData")
    public void sortProductsTest(String email, String password, String category, String sortOptionVisibleText) {

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ProductPage product = new ProductPage(driver);
        AccountPage account = new AccountPage(driver);

        home.goToLogin();
        login.login(email, password);           //log in with email and password

        product.goToCategory(category);            // Click on category ->"Phones & PDAs"

        product.selectSortByDynamic(sortOptionVisibleText);     //sort by sorting choice, A-Z or Z-A

        Assert.assertEquals(product.selectedOption(), sortOptionVisibleText);   // assert if sorting option is selected is true

        account.logOut();       //logout
    }
}