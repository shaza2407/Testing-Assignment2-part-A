package tests;

import base.BaseTest;
import configuration.CSVUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.time.Duration;
//scenario 3
//1- Go to "My account"--> Login
//2- Enter Valid Email/password
//3- "My Account" page opened
//4- The user can Log in to the system

//scenario 4
//1- Go to "My account"--> Login
//2- Enter wrong Email/password
//3- Error message displayed "No match for E-Mail Address and/or Password."
//4- The user couldn't Log in to the system
public class LoginTests extends BaseTest {
    @DataProvider(name = "loginData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("loginData.csv");
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password, String expected){
        HomePage home = new HomePage(driver);
        home.goToLogin();                          //go to My Account -> login

        LoginPage login = new LoginPage(driver);
        login.login(email, password);            //log in with email and password
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        if (expected.equals("valid")) {           //confirm user is logged in
            wait.until(ExpectedConditions.urlContains("account"));
            Assert.assertTrue(login.isLoggedIn());
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));
            Assert.assertTrue(login.getErrorMessage().contains("Warning: No match for E-Mail Address and/or Password."));
        }
    }
}

