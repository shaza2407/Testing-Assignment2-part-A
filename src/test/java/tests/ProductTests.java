package tests;

import base.BaseTest;
import configuration.CSVUtils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;

import java.time.Duration;
//scenario 6
//1- Login by any valid user
//2- Click on "Tablets"
//3- The latest Link in Bread crumb is "Tablets"
//4- The highlighted link in right handside is "Tablets"
//5- Log out
public class ProductTests extends BaseTest {
    @DataProvider(name = "validUser")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("validUser.csv");
    }

    @Test(dataProvider = "validUser" )
    public void testLogin(String email, String password) {
        HomePage home = new HomePage(driver);
        home.goToLogin();                          //go to My Account -> login

        LoginPage login = new LoginPage(driver);
        login.login(email, password);            //log in with email and password

        ProductPage product = new ProductPage(driver);
        product.goToTablets();                      //go to all tablets

        String activeSidebar = product.getHighlightedSidebarLink();     //get highlighted sidebar link
        Assert.assertTrue(activeSidebar.contains("Tablets"), "Sidebar active link should contain 'Tablets'");   //assert true if sidebar link contains "Tablets"

        AccountPage account = new AccountPage(driver);
        account.logOut();           //logout

    }
}
