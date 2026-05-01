package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    By Desktops = By.cssSelector("a.dropdown-toggle[href*='path=20']");
    By allDesktops = By.linkText("Show AllDesktops");
    By breadcrumb = By.cssSelector("ul.breadcrumb");
    By Tablets =By.cssSelector("a[href*='path=57']");
    By sidebarActive = By.cssSelector(".list-group-item.active");
    By inputSort = By.id("input-sort");
    By showAllDesktops = By.linkText("Show AllDesktops");
    By showAllMp3 = By.linkText("Show AllMP3 Players");
    By showAllLaptops = By.linkText("Show AllLaptops & Notebooks");
    By shoppingCart = By.linkText("Shopping Cart");

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

    // ================= NAVIGATION METHODS =================

    public void goToCategory(String category){
        By categoryLocator = By.linkText(category);
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();
        switch (category) {
            case "Desktops" -> wait.until(ExpectedConditions.elementToBeClickable(showAllDesktops)).click();
            case "MP3 Players" -> wait.until(ExpectedConditions.elementToBeClickable(showAllMp3)).click();
            case "Laptops & Notebooks" -> wait.until(ExpectedConditions.elementToBeClickable(showAllLaptops)).click();
            case "Tablets" -> {} // No "Show All" for Tablets, just click category;
            case "Phones & PDAs" -> {} // No "Show All" for Phones, just click category;
            default -> throw new AssertionError();
        }
        wait.until(ExpectedConditions.textToBePresentInElementLocated(breadcrumb, category));
    }

    public void goToProduct(String productName) {
        By productLocator = By.linkText(productName);
        wait.until(ExpectedConditions.elementToBeClickable(productLocator)).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("ul.breadcrumb"), productName));

    }


    public void goToShoppingCart() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCart));
        driver.findElement(shoppingCart).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("ul.breadcrumb"), "Shopping Cart"));
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
