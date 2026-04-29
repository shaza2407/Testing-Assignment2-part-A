package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By myAccount = By.cssSelector("a[title='My Account']");
    By register = By.linkText("Register");
    By login = By.linkText("Login");
    By logout = By.linkText("Logout");

    // By desktops = By.linkText("Desktops");
    By showAllDesktops = By.linkText("Show AllDesktops");

    // By phones = By.linkText("Phones & PDAs");

    // By mp3Players = By.linkText("MP3 Players");
    By showAllMp3 = By.linkText("Show AllMP3 Players");

    By tablets = By.linkText("Tablets");

    By laptops = By.linkText("Laptops & Notebooks");
    By showAllLaptops = By.linkText("Show AllLaptops & Notebooks");

    By shoppingCart = By.linkText("Shopping Cart");

    public void goToRegister(){
        wait.until(ExpectedConditions.elementToBeClickable(myAccount));
        driver.findElement(myAccount).click();
        wait.until(ExpectedConditions.elementToBeClickable(register));
        driver.findElement(register).click();
    }

    public void goToLogin(){
        wait.until(ExpectedConditions.elementToBeClickable(myAccount));
        driver.findElement(myAccount).click();
        wait.until(ExpectedConditions.elementToBeClickable(login));
        driver.findElement(login).click();
    }

    

    // ================= NAVIGATION METHODS =================

    // public void goToDesktops(String category) {
    //     wait.until(ExpectedConditions.elementToBeClickable(desktops));
    //     driver.findElement(desktops).click();
    //     wait.until(ExpectedConditions.elementToBeClickable(showAllDesktops));
    //     driver.findElement(showAllDesktops).click();
    // }

    // public void goToMP3Players() {
    //     wait.until(ExpectedConditions.elementToBeClickable(mp3Players));
    //     driver.findElement(mp3Players).click();
    //     wait.until(ExpectedConditions.elementToBeClickable(showAllMp3));
    //     driver.findElement(showAllMp3).click();
    // }
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

    }

    // public void goToPhones() {
    //     wait.until(ExpectedConditions.elementToBeClickable(phones)).click();
    // }

    // public void goToTablets() {
    //     wait.until(ExpectedConditions.elementToBeClickable(tablets)).click();
    // }

    // public void goToLaptops() {
    //     wait.until(ExpectedConditions.elementToBeClickable(laptops)).click();
    //     wait.until(ExpectedConditions.elementToBeClickable(showAllLaptops)).click();
        
    // }

    public void goToShoppingCart() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCart));
        driver.findElement(shoppingCart).click();
    }

}