package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import configuration.CSVUtils;
import pages.HomePage;
import pages.LoginPage;
import pages.ShoppingCartPage;

public class CheckoutTests extends BaseTest {

    // 1. Set up the DataProvider to read from your CSV file
    @DataProvider(name = "CheckoutData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("CheckoutData.csv"); 
    }

    // 2. Pass dynamic variables into the test method instead of hardcoding
    @Test(dataProvider = "CheckoutData")
    public void normalCheckoutProcessTest(String email, String password, String category, String productName, 
                                          String firstName, String lastName, String address, 
                                          String city, String country, String region, String comment) {

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ShoppingCartPage shoppingCart = new ShoppingCartPage(driver);

        // Step 1: Login by any valid user
        home.goToLogin();
        login.login(email, password); // Using dynamic data

        // Steps 2 & 3: Navigate to products and add to cart
        // home.goToDesktops(); 
        home.goToCategory(category);        
        shoppingCart.addToCartAction(productName,category);

        // shoppingCart.addToCart();

        // Step 4: Verify Success Message
        Assert.assertTrue(shoppingCart.getSuccessMsg().contains(productName));

        // Steps 5 & 6: Open shopping cart and check item details
        home.goToShoppingCart();
        String[] product = shoppingCart.getProductDetails(productName);
        Assert.assertEquals(product[0], productName);

        // Step 7: Click Checkout
        shoppingCart.clickCheckoutBtn();

        // Steps 8-11: Billing & Shipping Details
        String step = "payment";
        shoppingCart.clickContinue(step, "address"); // Assuming billing address exists, just continue

        step = "shipping";
        shoppingCart.clickShippingRadioBtn();
        
        // Fill shipping details dynamically from CSV
        shoppingCart.enterFirstName(firstName, step);
        shoppingCart.enterLastName(lastName, step);
        shoppingCart.enterAddress(address, step);
        shoppingCart.enterCity(city, step);
        shoppingCart.selectCountry(country, step);
        shoppingCart.selectRegion(region, step);
        shoppingCart.clickContinue(step, "address");

        // Steps 12 & 13: Delivery method and Comment
        shoppingCart.enterComment(comment);        
        String flatShippingRate = shoppingCart.getFlatShippingRate();

        shoppingCart.clickContinue(step, "method");

        // Step 14: Payment method and Terms
        shoppingCart.clickTermsBtn();
        shoppingCart.clickContinue("payment", "method");

        // Step 15: "Confirm order" section will appear with the same prices
        Assert.assertEquals(shoppingCart.getUnitPrice(productName), product[1]);

        // Step 16: Total price includes the "Flat shipping rate"
        String totalPrice = shoppingCart.getTotalPrice();
        
        // Clean strings and calculate math dynamically
        double productPrice = Double.parseDouble(product[1].replace("$", "").replace(",", ""));
        double flatShipping = Double.parseDouble(flatShippingRate.replace("$", "").replace(",", ""));
        double totalCalculated = productPrice + flatShipping;
        String finalTotal = "$" + String.format("%.2f", totalCalculated);
        
        Assert.assertEquals(totalPrice, finalTotal);

        // Steps 17 & 18: Click Confirm Order and check final message
        shoppingCart.clickConfirmOrderBtn();
        Assert.assertEquals(shoppingCart.getSuccessHeaderMessage(), "Your order has been placed!");
        
        // Optional Step (from your old code): Verify Cart is Empty after order
        shoppingCart.clickContinueBtn();
        home.goToShoppingCart();
        Assert.assertEquals(shoppingCart.getEmptyCartMessage(), "Your shopping cart is empty!");
    }
}