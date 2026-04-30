package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    WebDriver driver;
    By searchInput  = By.name("search");
    By searchButtonIcon = By.cssSelector("button.btn.btn-default.btn-lg");
    By productNames = By.cssSelector(".product-thumb h4 a");
    By searchButton = By.id("button-search");
    By categoryDropdown = By.name("category_id");
    By keywords = By.cssSelector("#input-search");
    By noResultsMsg = By.cssSelector("#content h2 + p");
    By resultProduct = By.linkText("Apple Cinema 30\"");
    By subCategoryCheckbox = By.name("sub_category");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }



    public void search(String keyword) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        driver.findElement(searchButtonIcon).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Products meeting the search criteria')]")));
    }
    public void searchButtonClick(){
        driver.findElement(searchButton).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("ul.breadcrumb"), "Search"));
    }
    public void searchButtonIconClick(){
        driver.findElement(searchButtonIcon).click();
    }

    public void selectCategory(String category){
        Select select = new Select(driver.findElement(categoryDropdown));
        select.selectByVisibleText(category);
    }

    public void keywordsInputClick(String value){
        driver.findElement(keywords).sendKeys(value);
    }
    
    public void subCategoryClick(){
        driver.findElement(subCategoryCheckbox).click();
    }

    public boolean allProductsContain(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNames));

        List<WebElement> products = driver.findElements(productNames);
        for (WebElement product : products) {
            if (!product.getText().toLowerCase().contains(keyword.toLowerCase())) {
                return false;
            }
        }
        return !products.isEmpty(); // fail if no products found
    }

     public String getResultMessage(){
        return driver.findElement(noResultsMsg).getText();
    }

     public boolean isProductDisplayed(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(resultProduct));
            return product.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}