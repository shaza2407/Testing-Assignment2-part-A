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

public class SortingTests extends BaseTest {

    // 1. DataProvider to read sorting configurations
    @DataProvider(name = "SortingData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("SortingData.csv"); 
    }

    // 2. Data-driven test passing all values dynamically
    @Test(dataProvider = "SortingData")
    public void sortProductsTest(String email, String password, String category, String sortOptionVisibleText) {

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ProductPage product = new ProductPage(driver);
        AccountPage account = new AccountPage(driver);
        // Step 1: Login
        home.goToLogin();
        login.login(email, password);

        // Step 2: Navigate dynamically to the category (e.g., Phones & PDAs, Desktops)
        home.goToCategory(category);

        // Step 3: Select the sort option dynamically based on CSV data
        product.selectSortByDynamic(sortOptionVisibleText);

        // Step 4: Verify the selected option matches what we passed
        Assert.assertEquals(product.selectedOption(), sortOptionVisibleText);

        // Step 5: Log out to ensure a clean state for the next row of data
        account.logOut();
    }
}