package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import configuration.CSVUtils;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ShoppingCartPage;

public class ShoppingCartTests extends BaseTest {

    // 1. Set up the DataProvider to read from your CSV file
    @DataProvider(name = "ShoppingCartData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("ShoppingCartData.csv"); 
    }

    // 2. Pass dynamic variables into the test method instead of hardcoding
    @Test(dataProvider = "ShoppingCartData")
    public void outOfStockItemsCartE2ETest(String email, String password, String category1, String product1, 
                                           String category2, String product2, String deliveryDate) {

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ShoppingCartPage cart = new ShoppingCartPage(driver);
        AccountPage account = new AccountPage(driver); // Used for logging out at the end

        // Step 1: Login by any valid user
        home.goToLogin();
        login.login(email, password);

        // Step 2 & 3: Click on "Tablets" --> "Show all Tablets" and Add "Samsung Galaxy Tab 10.1"
        home.goToCategory(category1); 
        cart.addToCartAction(product1,category1,deliveryDate);
        // Note: For some items, addToCartAction immediately adds it. If a separate click is needed, add cart.addToCart();

        // Step 4: Info message "Success: You have added Samsung Galaxy Tab 10.1 to your shopping cart!"
        Assert.assertTrue(cart.getSuccessMsg().contains(product1));

        // Step 5: Open shopping cart and check on the item added & its price
        home.goToShoppingCart();
        String[] tabletDetails = cart.getProductDetails(product1);
        Assert.assertEquals(tabletDetails[0], product1);
        Assert.assertFalse(tabletDetails[1].isEmpty());

        // Step 6 & 7: Go to "Laptops" & Add "HP LP3065" laptop, Change delivery date & add to cart
        home.goToCategory(category2);
        cart.addToCartAction(product2,category2,deliveryDate);
        // cart.setDeliveryDate(deliveryDate);
        // cart.addToCart();

        // Step 8: Open the shopping cart to check on the item and its details (delivery date)
        home.goToShoppingCart();
        String retrievedDate = cart.getDeliveryDate(product2);
        Assert.assertEquals(retrievedDate, deliveryDate);
        
        String[] laptopDetails = cart.getProductDetails(product2);
        Assert.assertEquals(laptopDetails[0], product2);

        // Step 9: Check on the "Total" to be equal the total price of the items
        String totalPriceText = cart.getTotalPrice2(); // Assuming this gets the final total

        // Clean currency signs and calculate
        double tabletPrice = Double.parseDouble(tabletDetails[1].replace("$", "").replace(",", ""));
        double laptopPrice = Double.parseDouble(laptopDetails[1].replace("$", "").replace(",", ""));
        
        double expectedTotal = tabletPrice + laptopPrice;
        String finalTotal = "$" + String.format("%.2f", expectedTotal);

        Assert.assertEquals(totalPriceText, finalTotal);

        // Step 10: Log out
        account.logOut();
        Assert.assertTrue(account.isLogoutDisplayed() || driver.getCurrentUrl().contains("logout"));
    }
}