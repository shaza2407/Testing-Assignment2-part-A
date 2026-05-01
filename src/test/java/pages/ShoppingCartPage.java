package pages;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
 
public class ShoppingCartPage {

    WebDriver driver;
    WebDriverWait wait;

    // ================= LOCATORS =================

    By successMessage = By.className("alert-success");
    By checkoutBtn = By.linkText("Checkout");
    By commentArea = By.cssSelector("textarea[name='comment']");
    By termsBtn = By.cssSelector("input[type='checkbox'][name='agree']");
    By addToCartBtn = By.id("button-cart");
    By confirmOrderBtn = By.id("button-confirm");
    By successHeader = By.cssSelector("#content h1");
    By emptyCartMessage = By.cssSelector("#content p");
    By deliveryDateInput = By.id("input-option225");
    By continueBtn = By.cssSelector("a[href*='common/home']");
    By newAddressRadioBtn = By.cssSelector("input[name='payment_address'][value='new']");
    By firstName = By.id("input-payment-firstname");
    By lastName = By.id("input-payment-lastname");
    By addr = By.id("input-payment-address-1");
    By countryLocator = By.id("input-payment-country");
    By cityLocator = By.id("input-payment-city");
    By regionLocator = By.id("input-payment-zone");



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


    public void addToCart() {

        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }

    public void clickCheckoutBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        driver.findElement(checkoutBtn).click();
    }

    public void clickShippingRadioBtn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(newAddressRadioBtn));
        radio.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-payment-firstname")));
    }

    public void clickCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-inverse.btn-block.btn-lg.dropdown-toggle")));
        cart.click();
    }
    public void clickViewCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View Cart")));
        cart.click();
    }

    public void clickTermsBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(termsBtn)).click();
    }

    public void clickConfirmOrderBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderBtn));
        driver.findElement(confirmOrderBtn).click();
    }

    public void enterComment(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentArea));
        driver.findElement(commentArea).sendKeys(comment);
    }

    public void clickContinueBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }

    // ================= INPUT METHODS =================

    public void enterFirstName(String fn) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(fn);
    }

    public void enterLastName(String ln) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).sendKeys(ln);
    }

    public void enterAddress(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addr)).sendKeys(address);
    }

    public void enterCity(String city) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityLocator)).sendKeys(city);
    }

    // ================= DROPDOWNS =================

    public void selectCountry(String country) {

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(countryLocator));
        new Select(dropdown).selectByVisibleText(country);
    }

    public void selectRegion(String region) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(regionLocator, region));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(regionLocator));
        new Select(dropdown).selectByVisibleText(region);
    }

    // ================= CONTINUE BUTTON =================

public void clickContinue(String section, String type) {
    By continueBtn = By.id("button-" + section + "-" + type);
    wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
    driver.findElement(continueBtn).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(continueBtn));
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
        By tableLocator = By.cssSelector("div.table-responsive table.table-bordered");
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));

        WebElement table = driver.findElement(tableLocator);
        for (WebElement row : table.findElements(By.cssSelector("tbody tr"))) {
            String name = row.findElement(By.cssSelector("td:nth-child(1) a")).getText().trim();
            if (name.equals(productName)) {
                return row.findElement(By.cssSelector("td:nth-child(4)")).getText().trim();
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public String getFlatShippingRate() {
        By flatRateLabel = By.xpath("/html/body/div[2]/div/div/div/div[4]/div[2]/div/div[1]/label");
        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(flatRateLabel));
        String text = label.getText().trim();
        return text.substring(text.lastIndexOf("-") + 1).trim();
    }

    public String getTotalPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tfoot tr:last-child td:last-child"))).getText().trim();
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
        dateField.click();
        dateField.clear();
        dateField.sendKeys(deliveryDate);
        dateField.sendKeys(Keys.TAB);
    }

    public void selectExistingShippingAddress(String firstName, String lastName,String city) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"shipping-existing\"]/select")));
        dropdown.click();
        for (WebElement option : new Select(dropdown).getOptions()) {
            if (option.getText().contains(firstName) &&
                    option.getText().contains(lastName) &&
                    option.getText().contains(city)) {
                option.click();
                break;
            }
        }
    }
}