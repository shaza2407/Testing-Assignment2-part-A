package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
 
public class ShoppingCartPage {

    WebDriver driver;
    WebDriverWait wait;

    // ================= LOCATORS =================

    By successMessage = By.className("alert-success");
    By checkoutBtn = By.linkText("Checkout");
    By shippingNewAddressRadio = By.cssSelector("input[name='shipping_address'][value='new']");    
    By paymentNewAddressRadio = By.cssSelector("input[name='payment_address'][value='new']");    
    By commentArea = By.cssSelector("textarea[name='comment']");
    By termsBtn = By.cssSelector("input[type='checkbox'][name='agree']");
    By addToCartBtn = By.id("button-cart");
    // By flatShipping = By.xpath("//label[input[@name='shipping_method']]//input[@value='flat.flat']/parent::label");
    By confirmOrderBtn = By.id("button-confirm");
    By successHeader = By.cssSelector("#content h1");
    By emptyCartMessage = By.cssSelector("#content p");
    // By deliveryDateInput = By.xpath("//input[@id='input-option225']");
    By deliveryDateInput = By.id("input-option225");
    By continueBtn = By.cssSelector("a[href*='common/home']");
    // ================= CONSTRUCTOR =================

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private WebElement findProductCard(String productName) {

        for (WebElement product : driver.findElements(By.cssSelector(".product-thumb"))) {
            if (product.findElement(By.cssSelector("h4 a")).getText().equals(productName)) {
                return product;
            }
        }

        throw new RuntimeException("Product not found: " + productName);
    }

    // ================= ACTION METHODS =================


    public void addToCartAction(String productName, String category) {

        WebElement product = findProductCard(productName);
        wait.until(ExpectedConditions.elementToBeClickable(product.findElement(By.cssSelector("button[onclick*='cart.add']")))).click();

          switch (category) {
            case "Desktops" -> wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click(); 
            // case "MP3 Players" -> {}
            case "Laptops & Notebooks" -> wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click(); 
            // case "Tablets", "Phones & PDAs" -> {} // Just click category, no "Show All"
            // default -> throw new AssertionError("Unknown category: " + category);
        }
    }

    public void addToCartAction(String productName, String category, String deliveryDate) {

        WebElement product = findProductCard(productName);
        wait.until(ExpectedConditions.elementToBeClickable(product.findElement(By.cssSelector("button[onclick*='cart.add']")))).click();

        switch (category) {
            case "Desktops"  -> {
                if (deliveryDate != null && !deliveryDate.isEmpty()) {
                    setDeliveryDate(deliveryDate);
                }
                wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

            }
            case "Laptops & Notebooks" -> {
                if (deliveryDate != null && !deliveryDate.isEmpty()) {
                    setDeliveryDate(deliveryDate);
                }
                wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

            }
            // Other categories don't support delivery date
        }
    }
    
    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }

    public void clickCheckoutBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }

    public void clickShippingRadioBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(shippingNewAddressRadio));
        driver.findElement(shippingNewAddressRadio).click();
    }

    public boolean isPaymentNewAddressOptionAppeared() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(paymentNewAddressRadio));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickPaymentRadioBtn() {
        // First check if the option is visible/appeared
        if (isPaymentNewAddressOptionAppeared()) {
            wait.until(ExpectedConditions.elementToBeClickable(paymentNewAddressRadio)).click();
            return true;
        } else {
            return false;
        }
    }

    public void clickTermsBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(termsBtn)).click();
    }
    
    public void clickConfirmOrderBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderBtn)).click();
    }

    public void enterComment(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentArea)).sendKeys(comment);
    }

    public void clickContinueBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }

    // ================= INPUT METHODS =================

    public void enterFirstName(String fn, String step) {

        By firstName = By.id("input-" + step + "-firstname");

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(fn);
    }

    public void enterLastName(String ln, String step) {

        By lastName = By.id("input-" + step + "-lastname");

        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).sendKeys(ln);
    }

    public void enterAddress(String address, String step) {

        By addr = By.id("input-" + step + "-address-1");

        wait.until(ExpectedConditions.visibilityOfElementLocated(addr)).sendKeys(address);
    }

    public void enterCity(String city, String step) {

        By cityLocator = By.id("input-" + step + "-city");

        wait.until(ExpectedConditions.visibilityOfElementLocated(cityLocator)).sendKeys(city);
    }

    // ================= DROPDOWNS =================

    public void selectCountry(String country, String step) {

        By countryLocator = By.id("input-" + step + "-country");

        WebElement dropdown = wait.until(
            ExpectedConditions.elementToBeClickable(countryLocator)
        );

        new Select(dropdown).selectByVisibleText(country);
    }

    public void selectRegion(String region, String step) {

        By regionLocator = By.id("input-" + step + "-zone");

        WebElement dropdown = wait.until(
            ExpectedConditions.elementToBeClickable(regionLocator)
        );

        new Select(dropdown).selectByVisibleText(region);
    }

    // ================= CONTINUE BUTTON =================

    public void clickContinue(String step, String p) {

        By continueBtn = By.id("button-" + step + "-" + p);
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
        
        // Minor pause to allow the OpenCart accordion to slide open before looking for the next element
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    
    }

    // ================= VERIFICATION METHODS =================

    public String getSuccessMsg() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(successMessage)
        ).getText();
    }

    public String[] getProductDetails(String productName) {

        By tableLocator = By.cssSelector("div.table-responsive table.table-bordered");
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));

        WebElement table = driver.findElement(tableLocator);

        // find all rows
        for (WebElement row : table.findElements(By.cssSelector("tbody tr"))) {

            String name = row.findElement(By.cssSelector("td:nth-child(2) a")).getText().trim();

            if (name.equals(productName)) {
                String price = row.findElement(By.cssSelector("td:nth-child(5)")).getText().trim();
                return new String[]{name, price};
            }
        }

        throw new RuntimeException("Product not found: " + productName);
    }
    public String getUnitPrice(String productName) {

        WebElement table = driver.findElement(By.cssSelector("div.table-responsive table"));

        for (WebElement row : table.findElements(By.cssSelector("tbody tr"))) {

            String name = row.findElement(By.cssSelector("td:nth-child(1) a")).getText().trim();

            if (name.equals(productName)) {
                return row.findElement(By.cssSelector("td:nth-child(4)")).getText().trim();
            }
        }

        throw new RuntimeException("Product not found: " + productName);
    }    
    
    public String getFlatShippingRate() {

        By labelsLocator = By.cssSelector("label");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(labelsLocator));

        for (int i = 0; i < driver.findElements(labelsLocator).size(); i++) {

            WebElement label = driver.findElements(labelsLocator).get(i); // re-fetch each time

            if (!label.findElements(By.cssSelector("input[value='flat.flat']")).isEmpty()) {

                String text = label.getText().trim();
                return text.substring(text.lastIndexOf("-") + 1).trim();
            }
        }

        throw new RuntimeException("Flat Shipping Rate not found");
    }

    public String getTotalPrice() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("tfoot tr:last-child td:last-child")
            )
        ).getText().trim();
    }

    public String getTotalPrice2() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".col-sm-offset-8 table tbody tr:last-child td:last-child")
            )
        ).getText().trim();
    }

    public String getSuccessHeaderMessage(){
        wait.until(ExpectedConditions.urlContains("checkout/success"));
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(successHeader)
        ).getText().trim();
    }

    public String getEmptyCartMessage(){
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(emptyCartMessage)
        ).getText().trim();
    }

    public String getDeliveryDate(String productName) {
        WebElement table = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.table-responsive table")
            )
        );

        for (WebElement row : table.findElements(By.cssSelector("tbody tr"))) {

            String name = row.findElement(By.cssSelector("td:nth-child(2) a")).getText().trim();

            if (name.equals(productName)) {

                // get all <small> elements under product cell
                for (WebElement small : row.findElements(By.cssSelector("td:nth-child(2) small"))) {

                    String text = small.getText();

                    if (text.contains("Delivery Date")) {
                        return text.split(":")[1].trim();
                    }
                }
            }
        }

        throw new RuntimeException("Delivery date not found for: " + productName);
    }
    public void setDeliveryDate(String deliveryDate) {
        WebElement dateField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(deliveryDateInput)
        );
        dateField.clear(); 
        dateField.sendKeys(deliveryDate);    
    }

}