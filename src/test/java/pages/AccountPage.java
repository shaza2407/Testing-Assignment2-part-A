package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {

    WebDriver driver;
    By successHeader = By.cssSelector("#content h1");
    By logoutLink = By.linkText("Logout");

    public AccountPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean isRegistrationSuccessful(){
        return driver.findElement(successHeader).isDisplayed();
    }

    public boolean isLogoutDisplayed(){
        return driver.findElement(logoutLink).isDisplayed();
    }

    public void logOut() {
        driver.findElement(By.linkText("My Account")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
        driver.findElement(By.linkText("Logout")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));

    }
}