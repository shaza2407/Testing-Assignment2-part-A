package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import configuration.CSVUtils;
import pages.*;

//scenario 10
//1- Login by any valid user
//2- Click on "Tablets"
//3- Add "Samsung Galaxy Tab 10.1" to the cart
//4- Info message "Success: You have added Samsung Galaxy Tab 10.1 to your shopping cart!"
//5- Open shopping cart and check on the item added & it's price
//6- Go to "Laptops" & Add "HP LP3065" laptop
//7- Change the delivery date & add it to the shopping cart
//8- Open the shopping cart to check on the item and its details (delivery date)
//9- check on the "Total" to be equal the total price of the items
//10- Log out
public class ShoppingCartTests extends BaseTest {
    @DataProvider(name = "ShoppingCartData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("ShoppingCartData.csv"); 
    }

    @Test(dataProvider = "ShoppingCartData")
    public void shoppingCartTest(String email, String password, String category1, String product1,
                                           String category2, String product2, String deliveryDate) {

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ShoppingCartPage cart = new ShoppingCartPage(driver);
        ProductPage product = new ProductPage(driver);
        AccountPage account = new AccountPage(driver);

        home.goToLogin();
        login.login(email, password);           //log in with email and password

        product.goToCategory(category1);        // Click on category ->"Tablets"
        cart.addToCartAction(product1, category1);  // Add "Samsung Galaxy Tab 10.1" to the cart

        Assert.assertTrue(cart.getSuccessMsg().contains(product1));  // Check if success message is displayed

        //open shopping cart and check on the item added & its price
        product.goToShoppingCart();
        String[] Details = cart.getProductDetails(product1);
        Assert.assertEquals(Details[0], product1);
        Assert.assertFalse(Details[1].isEmpty());

        product.goToCategory(category2);     // Go to "Laptops" & Add "HP LP3065" laptop
        product.goToProduct(product2);

         cart.setDeliveryDate(deliveryDate);        // Change the delivery date & add it to the shopping cart
         cart.addToCart();

        product.goToShoppingCart();         // Open the shopping cart to check on the item
        String retrievedDate = cart.getDeliveryDate(product2);          // Check on the delivery date
        Assert.assertEquals(retrievedDate, deliveryDate);
        
        String[] laptopDetails = cart.getProductDetails(product2);
        Assert.assertEquals(laptopDetails[0], product2);

        String totalPriceText = cart.getTotalPrice2(); // Assuming this gets the final total

        //clean currency signs and calculate
        double tabletPrice = Double.parseDouble(Details[1].replace("$", "").replace(",", ""));
        double laptopPrice = Double.parseDouble(laptopDetails[1].replace("$", "").replace(",", ""));

        double expectedTotal = tabletPrice + laptopPrice;
        String finalTotal = "$" + String.format("%.2f", expectedTotal);

        Assert.assertEquals(totalPriceText, finalTotal);    // Check on the "Total" to be equal total price of the items

        account.logOut();       //logout
    }
}