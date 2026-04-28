package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    WebDriver driver;
    By Desktops = By.cssSelector("a.dropdown-toggle[href*='path=20']");
    By allDesktops = By.linkText("Show AllDesktops");
    By breadcrumb = By.cssSelector("ul.breadcrumb");
    By desktops = By.cssSelector("ul.breadcrumb li:nth-child(2) a");
    By Tablets =By.cssSelector("a[href*='path=57']");
    By sidebarActive = By.cssSelector(".list-group-item.active");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToDesktops() {
        driver.findElement(Desktops).click();
        driver.findElement(allDesktops).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("ul.breadcrumb"), "Desktops"));
    }

    public void goToTablets(){
        driver.findElement(Tablets).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("ul.breadcrumb"), "Tablets"));
    }

    public String getHighlightedSidebarLink() {
        return driver.findElement(sidebarActive).getText().trim();
    }

}
