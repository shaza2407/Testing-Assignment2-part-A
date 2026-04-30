package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By myAccount = By.cssSelector("a[title='My Account']");
    By register = By.linkText("Register");
    By login = By.linkText("Login");
    By logout = By.linkText("Logout");


    public void goToRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccount));
        driver.findElement(myAccount).click();
        wait.until(ExpectedConditions.elementToBeClickable(register));
        driver.findElement(register).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
    }

    public void goToLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccount));
        driver.findElement(myAccount).click();
        wait.until(ExpectedConditions.elementToBeClickable(login));
        driver.findElement(login).click();
    }


}