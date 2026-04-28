package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class CurrencyPage {

    WebDriver driver;

    By currency = By.cssSelector("button.btn.btn-link.dropdown-toggle");
    By euro = By.cssSelector("button.currency-select[name='EUR']");
    By euroSign =By.cssSelector("button.btn.btn-link.dropdown-toggle strong");


    public CurrencyPage(WebDriver driver){
        this.driver = driver;
    }

    public void changeCurrency() {
        driver.findElement(currency).click();
        driver.findElement(euro).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("button.btn.btn-link.dropdown-toggle strong"), "€"
        ));
    }

    public boolean isEuroSignDisplayed() {
        return driver.findElement(euroSign).getText().contains("€");
    }
}
