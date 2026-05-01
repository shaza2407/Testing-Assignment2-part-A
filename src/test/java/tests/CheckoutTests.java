package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import configuration.CSVUtils;
import pages.*;
//scenario 11
//1- Login by any valid user
//2- Click on "MP3 Players" -->"Show all MP3 Players"
//3- Add "ipod shuffle" to the cart
//4- Info message "Success: You have added "ipod shuffle" to your shopping cart!"
//5- Open shopping cart and check on the item added & it's price
//6- Click on "View Cart" to check on that  "ipod shuffle " added
//7- Click on "Checkout" button
//8- Fill billing details by new address
//9- Check on Address drop down filled by new address
//10-Click "continue"
//11- Shipping details  section will open so choose  the new details added and Click "continue"
//12- Delivery method section will open
//13- Add Comment & Click on "Continue"
//14-Payment method section will appear so check on "Terms & conditions" Click on "Continue" button
//15- "Confirm order" section will appear  with the same prices as in shopping cart
//16- Total price includes the "Flat shipping rate"
//17- Click on "Confirm Order" button
//18- "Your order has been placed!" message displayed & 0 items found in the small Shopping cart
//19- Log out

//test data cause failure: shazaaa@test.com,1234,MP3 Players,iPod Shuffle,John,Doe,Cairo,Cairo,Egypt,Al Dumyat,Test order 1
public class CheckoutTests extends BaseTest {

    @DataProvider(name = "CheckoutData")
    public Object[][] getData() throws Exception {
        return CSVUtils.getTestData("CheckoutData.csv"); 
    }
    @Test(dataProvider = "CheckoutData")
    public void normalCheckoutProcessTest(String email, String password, String category, String productName, 
                                          String firstName, String lastName, String address, 
                                          String city, String country, String region, String comment) {

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ProductPage pr = new ProductPage(driver);
        ShoppingCartPage shoppingCart = new ShoppingCartPage(driver);
        AccountPage account = new AccountPage(driver);

        home.goToLogin();                   //log in with email and password
        login.login(email, password);

        pr.goToCategory(category);          // Click on category ->"MP3 Players"
        shoppingCart.addToCartAction(productName,category);   //add product to cart

        Assert.assertTrue(shoppingCart.getSuccessMsg().contains(productName));      //check if success message is displayed

        //steps 5
        pr.goToShoppingCart();
        String[] product = shoppingCart.getProductDetails(productName);
        Assert.assertEquals(product[0], productName);

        //steps 6:click on "View Cart" to check on that product added
        shoppingCart.clickCart();
        shoppingCart.clickViewCart();
        String[] products = shoppingCart.getProductDetails(productName);
        Assert.assertEquals(products[0], productName);


        //step 7:click Checkout
        shoppingCart.clickCheckoutBtn();

        //steps 8-11:billing &shipping Details
        shoppingCart.clickShippingRadioBtn();
        // Fill payment details
        shoppingCart.enterFirstName(firstName);
        shoppingCart.enterLastName(lastName);
        shoppingCart.enterAddress(address);
        shoppingCart.enterCity(city);
        shoppingCart.selectCountry(country);
        shoppingCart.selectRegion(region);
        shoppingCart.clickContinue("payment", "address");

        shoppingCart.selectExistingShippingAddress(firstName, lastName,city);
        shoppingCart.clickContinue("shipping", "address");

        //steps 12 & 13: Delivery method and Comment
        shoppingCart.enterComment(comment);
        String flatShippingRate = shoppingCart.getFlatShippingRate();
        shoppingCart.clickContinue("shipping", "method");

        //step 14:payment method and Terms
        shoppingCart.clickTermsBtn();
        shoppingCart.clickContinue("payment", "method");

        //step 15: "Confirm order" section will appear with the same prices
        Assert.assertEquals(shoppingCart.getUnitPrice(productName), product[1]);

        //step 16:total price includes the "Flat shipping rate"
        String totalPrice = shoppingCart.getTotalPrice();
        
        //clean strings and calculate math dynamically
        double productPrice = Double.parseDouble(product[1].replace("$", "").replace(",", ""));
        double flatShipping = Double.parseDouble(flatShippingRate.replace("$", "").replace(",", ""));
        double totalCalculated = productPrice + flatShipping;
        String finalTotal = "$" + String.format("%.2f", totalCalculated);
        
        Assert.assertEquals(totalPrice, finalTotal);            //check if total price is equal to calculated total price

        //steps 17 & 18: Click Confirm Order and check final message
        shoppingCart.clickConfirmOrderBtn();
        Assert.assertEquals(shoppingCart.getSuccessHeaderMessage(), "Your order has been placed!");
        //verify Cart is Empty after order
        shoppingCart.clickContinueBtn();
        pr.goToShoppingCart();
        Assert.assertEquals(shoppingCart.getEmptyCartMessage(), "Your shopping cart is empty!");
        //step 19: log out
        account.logOut();

    }
}