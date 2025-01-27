package MausumiAcademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import MausumiAcademy.TestComponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mausumiacademy.pageobjects.CartPage;
import mausumiacademy.pageobjects.CheckOutPage;
import mausumiacademy.pageobjects.ConformationPage;
import mausumiacademy.pageobjects.LandingPage;
import mausumiacademy.pageobjects.ProductCatalog;

public class StepDefinitionImpl extends BaseTest{
	// #196
	public LandingPage landingPage;
	public ProductCatalog productCatalog ;
	public ConformationPage conformationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_page() throws IOException  {
		landingPage = launchApplication();
	}
	
	//(.+) ==> is regexp, ^ and $ also are for regexp.
	//We cannot use <> here. Also, the different sets of username and password will be coming, 
	//so give regular expression (.+)
	//whenever we are giving regexp start with ^ and end with $
	@Given("^Loggged in with username (.+) and password (.+)$") 
	public void Logged_in_with_username_and_password (String username, String password) { //coming from Feature file
		productCatalog = landingPage.loginApplication(username, password);
		
	}
	
	//#197
	
	@When("^I add the (.+) to Cart$")
	public void i_add_the_product_to_Cart (String productName) { //coming from Feature file
		List<WebElement> products = productCatalog.getProductList();
		CartPage cartPage = productCatalog.addProuctToCart(productName);
		
	}
	
	@When("^Checkout (.+) and submit the Order") //and   is also equivalent to When
	public void checkout_and_submit_the_order (String productName) { //coming from Feature file
		CartPage cartPage = productCatalog.addProuctToCart(productName);

		productCatalog.goToCartPage();

		
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match); // should be in test class and not in Page Obj. Page Obj should only have
									// actions
		// click checkout button
		CheckOutPage checkoutPage = cartPage.goToCheckout();

		String countryName = "INDIA";
		String expectedConfirmationMessage = "THANK YOU FOR YOUR ORDER.";
		checkoutPage.SelectCountry(countryName);
		conformationPage = checkoutPage.submitOrder();
		
	}
	
	@Then("verify the {string} is displayed on Confirmation page") //"TAHNK YOU .." --> its dynamic, but already retrieved. We don;t have to get it from elsewhere
	public void verify_the_message_is_displayed_on_Confirmation_page (String message) {
		String actualConfirmationMessage = conformationPage.getConfirmationPage();
		Assert.assertTrue(actualConfirmationMessage.equalsIgnoreCase(message));
	}
	//#199 - new feature file was added.
	@Then(" {string} message is displayed") //"TAHNK YOU .." --> its dynamic, but already retrieved. We don;t have to get it from elsewhere
	public void message_is_displayed (String message) {
		
		Assert.assertEquals(message, landingPage.getErrorMessage());
		driver.close();
	}
}
