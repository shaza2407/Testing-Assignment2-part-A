package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    WebDriver driver;
    By email = By.id("input-email");
    By password = By.id("input-password");
    By loginBtn = By.cssSelector("input.btn.btn-primary[type='submit']");
    By errorMessage = By.cssSelector(".alert.alert-danger");
    By logoutBtn = By.cssSelector("a[href*='route=account/logout']");
    By myAccount = By.linkText("My Account");

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void login(String user,String pass){
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Wish List")));
    }

    public boolean isLoggedIn() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement accountBtn = wait.until(ExpectedConditions.elementToBeClickable(myAccount));
            accountBtn.click();
            return driver.findElement(logoutBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

}

