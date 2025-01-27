package MausumiAcademy.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import mausumiacademy.pageobjects.CartPage;
import mausumiacademy.pageobjects.CheckOutPage;
import mausumiacademy.pageobjects.ConformationPage;
import mausumiacademy.pageobjects.LandingPage;
import mausumiacademy.pageobjects.OrderPage;
import mausumiacademy.pageobjects.ProductCatalog;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import MausumiAcademy.TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
	//StandAloneTest is named as SubmitOrderTest in Teacher's code

	// public static void main(String[] args) { //made is a Test class using testng
	// annotation @Test
	String productName1 = "ZARA COAT 3";
	String email1 = "sheema05@gmail.com";
	String password1 = "Tapsium@1";

	// Data Driven Testing using dataProvider. getData() is the method that provides
	// the data.
	@Test(dataProvider = "getData", groups = { "Purchase" })
	// public void SubmitOrder(String email, String password, String productName)
	// throws IOException {
	public void SubmitOrder(HashMap<Object, Object> input) throws IOException {

		System.out.println("in FIRST test - SubmitOrder");
		// ProductCatalog productCatalog = landingPage.loginApplication(email,
		// password); // 3rd time:make the LandingPage
		// object public in BaseTest
		//-----------------------STEP 1 : LOGIN ---------------------------------------
		ProductCatalog productCatalog = landingPage.loginApplication((String) input.get("email"),
				(String) input.get("password")); // Class
		List<WebElement> products = productCatalog.getProductList();

		// CartPage cartPage = productCatalog.addProuctToCart(productName);
		// //commented-below like showing @DataProvider
		//---------------------- STEP 2 : ADD PRODUCT TO CART ---------------------------------------
		CartPage cartPage = productCatalog.addProuctToCart((String) input.get("productName"));

		productCatalog.goToCartPage();

		// Boolean match = cartPage.VerifyProductDisplay(productName);
		// ////commented-below like showing @DataProvider
		Boolean match = cartPage.VerifyProductDisplay((String) input.get("productName"));
		Assert.assertTrue(match); // should be in test class and not in Page Obj. Page Obj should only have
									// actions
		//---------------------- STEP 3 : CHECKOUT ---------------------------------------
		// click checkout button
		CheckOutPage checkoutPage = cartPage.goToCheckout();

		String countryName = "INDIA";
		String expectedConfirmationMessage = "THANK YOU FOR YOUR ORDER.";
		checkoutPage.SelectCountry(countryName);
		
		//---------------------- STEP 4 : SUBMIT  ---------------------------------------
		
		ConformationPage conformationPage = checkoutPage.submitOrder();

		//---------------------- STEP 5 : VERIFY CONF MESSAGE  ---------------------------------------
		// verify conf message on Confirmation page
		String actualConfirmationMessage = conformationPage.getConfirmationPage();
		Assert.assertTrue(actualConfirmationMessage.equalsIgnoreCase(expectedConfirmationMessage));
	}

	// this test running depends on SubmitOrder Test. Only if it passes, it will
	// run. Else it won't.
	@Test(dependsOnMethods = { "SubmitOrder" })
	public void OrderHistoryTest() {
		//
		System.out.println("in dependent test - OrderHistoryTest");
		ProductCatalog productCatalog = landingPage.loginApplication(email1, password1);
		OrderPage orderPage = productCatalog.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay("ZARA COAT 3"));
	}

	/*
	 * @DataProvider public Object[][] getData() {
	 * 
	 * //Create a 2D array with multiple rows and colums. the number of rows denotes
	 * the number //of tests that will run with different datasets, from a
	 * single @Test class.
	 * 
	 * return new Object[][] {
	 * {email1,password1,productName1},{"rahulshetty","ink1000","ADIDAS 1000"}};
	 * 
	 * }
	 */

	// DataSet coming from HashMap. commented this as we are now getting data from JSON as shown below.
	/*
	 * @DataProvider public Object[][] getData() {
	 * 
	 * 
	 * HashMap<String, String> map = new HashMap<String, String>(); map.put("email",
	 * "sheema05@gmail.com"); map.put("password", "Tapsium@1");
	 * map.put("productName", "ZARA COAT 3.");
	 * 
	 * HashMap<String, String> map1 = new HashMap<String, String>();
	 * map.put("email", "anshika@gmail.com"); map.put("password", "IamKing@000");
	 * map.put("productName", "ADIDAS 1000.");
	 * 
	 * 
	 * //map.put("anshika@gmail.com", "IamKing@000");
	 * 
	 * return new Object[][] { {map},{map1}};
	 * 
	 * }
	 */

	// DataSet coming from JSON /src/test/java/MausumiAcademy/data/PurchaseOrder.json
	@DataProvider
	public Object[][] getData() throws IOException {

		//call the json method
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//MausumiAcademy//data//PurchaseOrder.json");
		
		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}
	
	

}
