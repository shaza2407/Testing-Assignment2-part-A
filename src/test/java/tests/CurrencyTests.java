package tests;
import base.BaseTest;
import configuration.CSVUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.time.Duration;

//scenario 5
//1- Login by any valid user
//2- Click on "Desktops" ,"Show all Desktops"
//3- By default the Prices will be displayed in "$ dollar"
//4- Change the Currency from the upper Right handSide to "€ Euro"
//5- The Prices changed accordingly
//6- Logout

public class CurrencyTests extends BaseTest{
    @DataProvider(name = "validUser")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("validUser.csv");
    }
    @Test(dataProvider = "validUser")
    public void testLogin(String email, String password) {
        HomePage home = new HomePage(driver);
        home.goToLogin();                          //go to My Account -> login

        LoginPage login = new LoginPage(driver);
        login.login(email, password);            //log in with email and password

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.urlContains("account"));

        ProductPage product = new ProductPage(driver);
        product.goToDesktops();                     //go to all desktops

        CurrencyPage currency = new CurrencyPage(driver);
        currency.changeCurrency();

        Assert.assertTrue(currency.isEuroSignDisplayed());

         AccountPage account = new AccountPage(driver);
         account.logOut();
    }

}
