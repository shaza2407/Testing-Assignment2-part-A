package tests;
import base.BaseTest;
import configuration.CSVUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

//scenario 1 & 2
//1- Go to "My account" -->Register
//2-Fill in all required data (Ex: FN,LN,...)
//3-Accept the agreement
//4- Click on "Continue"
//5- Check on this message "Your Account Has Been Created!"
//6- Check on existense of "LogOut" in "My Account" menu
//7- Log out of the system

public class RegistrationTests extends BaseTest {
    @DataProvider(name = "RegisterData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("RegisterData.csv");
    }

    @Test(dataProvider = "RegisterData")
    public void registrationTest(String Fname , String Lname , String email , String telephone , String password , String expected) {
        HomePage home = new HomePage(driver);
        home.goToRegister();            //My Account -> Register
        RegisterPage register = new RegisterPage(driver);
        //fill all required data
        String mail = email + System.currentTimeMillis() + "@mail.com";
        register.enterFirstName(Fname);
        register.enterLastName(Lname);
        register.enterEmail(mail);
        register.enterTelephone(telephone);
        register.enterPassword(password);
        register.confirmPassword(password);

        register.acceptPolicy();        //accept agreement
        register.clickContinue();       //click continue
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(driver -> driver.getCurrentUrl().contains("success") || !driver.findElements(By.cssSelector(".text-danger")).isEmpty());

        if (expected == null || expected.isEmpty()) {        //right scenario , no errors expected
            AccountPage account = new AccountPage(driver);
            Assert.assertTrue(account.isRegistrationSuccessful());  //if success msg appeared,will assert true
            Assert.assertTrue(account.isLogoutDisplayed());         //logout appeared , so confirming login
            account.logOut();                                       //logout
        } else {
            List<String> errors = register.getAllFieldErrors();         //error msgs
            Assert.assertTrue(errors.stream().anyMatch(e -> e.contains(expected))); //assert true if the error msg = expected
        }
    }

}