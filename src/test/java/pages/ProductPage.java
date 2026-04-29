package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    By Desktops = By.cssSelector("a.dropdown-toggle[href*='path=20']");
    By allDesktops = By.linkText("Show AllDesktops");
    By breadcrumb = By.cssSelector("ul.breadcrumb");
    By desktops = By.cssSelector("ul.breadcrumb li:nth-child(2) a");
    By Tablets =By.cssSelector("a[href*='path=57']");
    By sidebarActive = By.cssSelector(".list-group-item.active");
    By inputSort = By.id("input-sort");

    public ProductPage(WebDriver driver) {
        this.driver = driver;        
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public void goToDesktops() {
        driver.findElement(Desktops).click();
        driver.findElement(allDesktops).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("ul.breadcrumb"), "Desktops"));
    }

    public void goToTablets(){
        driver.findElement(Tablets).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("ul.breadcrumb"), "Tablets"));
    }

    public String getHighlightedSidebarLink() {
        return driver.findElement(sidebarActive).getText().trim();
    }

    public void selectSortByDynamic(String visibleText){
        wait.until(ExpectedConditions.elementToBeClickable(inputSort));
        Select dropdown = new Select(driver.findElement(inputSort));
        dropdown.selectByVisibleText(visibleText);
    }

    public String selectedOption(){
        Select select = new Select(driver.findElement(inputSort));
        return select.getFirstSelectedOption().getText();
    }

    
}
