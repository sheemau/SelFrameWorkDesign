package MausumiAcademy.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import mausumiacademy.pageobjects.CartPage;
import mausumiacademy.pageobjects.CheckOutPage;
import mausumiacademy.pageobjects.ConformationPage;
import mausumiacademy.pageobjects.LandingPage;
import mausumiacademy.pageobjects.ProductCatalog;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import MausumiAcademy.TestComponents.BaseTest;

public class StandAloneTestBackup1 extends BaseTest {
	
	//public static void main(String[] args) { //made is a Test class using testng annotation @Test
	
	@Test
	public void SubmitOrder() throws IOException {
		String productName = "ZARA COAT 3";
		String email = "sheema05@gmail.com";
		String password = "Tapsium@1";
		/*
		 * WebDriverManager.chromedriver().setup(); //chrome driver will automatically be downloaded 
		 * WebDriver driver = new ChromeDriver();
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 * driver.manage().window().maximize();
		 */
		
		//driver.get("http://rahulshettyacademy.com/client");
		//driver.findElement(By.id("userEmail")).sendKeys("sheema05@gmail.com");
		//driver.findElement(By.id("userPassword")).sendKeys("Tapsium@1");
		//driver.findElement(By.id("login")).click();
		//above 4 lines have been replaced by below 2 using POM.
		
		//adding to basetest class as its generic
		//LandingPage landingPage = new LandingPage(driver);
		//landingPage.goTo();
		//LandingPage landingPage = launchApplication(); //3rd time: around commenting this out as we have to launch application always, put it in @BeforeMethod
		//LandingPage landingPage
		
		////OOP - Encapsulation - now we are creating too many objects of the pages.
		//in Landing page action method - loginApplication - we know that next page is catalog.
		//create an instance of it and directly go to the catalog page.
		
		//landingPage.loginApplication(email, password);
		ProductCatalog productCatalog = landingPage.loginApplication(email, password); //3rd time:make the LandingPage object public in BaseTest Class
		System.out.println("After loGIN");
		//moved to Prod Catalog and wrote an action method, create Prod Catalog object and call the method
		//WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(5));
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3"))); //reusable step. no need to write in every page
		
		
		
		//ProductCatalog productCatalog = new ProductCatalog(driver);
		List<WebElement> products = productCatalog.getProductList();
		
		
		//List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//Java Streams - iterate through products to have a match - moving to Product catalog (creating an action 
		//method there
		/*
		 * WebElement prod = products.stream().filter(product->
		 * product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).
		 * findFirst().orElse(null); //scan element
		 * System.out.println("got webelement");
		 */
		//moving below line to prod catalog class - writing another action item to addtocart
		//prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//productCatalog.addProuctToCart(productName);
		CartPage cartPage = productCatalog.addProuctToCart(productName);
		
		System.out.println("Clicked Cart");
		
		System.out.println("END");
		
		//WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(5));
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("toast-container"))); //moved to ProdCatalog
		
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animated"))); //taking long
		
		//wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animated"))));  //moved to ProdCatalog
		
		//find cart on top of page and click
		//driver.findElement(By.cssSelector("[routerlink*='cart']")).click(); //moved to AbstractComponent as its common to all
		productCatalog.goToCartPage();
		
		//List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3")); //moved to CartPage
		
		
		//we have to see if the item is there in the cart page - use Java Streams --> anyMatch
		//boolean match=cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		//CartPage cartPage = new CartPage(driver); //encapsulation - declared in Prod Catalog AddToCart method
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match); //should be in test class and not in Page Obj. Page Obj should only have actions
		
		//click checkout button
		
		//driver.findElement(By.cssSelector(".totalRow button")).click(); //moved tp cart page
		//cartPage.goToCheckout(); //encapsulated as below
		CheckOutPage checkoutPage = cartPage.goToCheckout();
		
		
		//on the Checkout page
		/*
		 * driver.findElement(By.cssSelector(".form-group input")).sendKeys("IND");
		 * driver.findElement(By.cssSelector(".form-group input")).sendKeys(Keys.DOWN);
		 * driver.findElement(By.cssSelector(".form-group input")).sendKeys(Keys.DOWN);
		 * //click the Place Order button
		 * driver.findElement(By.cssSelector(".btnn")).click();
		 * 
		 * //grab the order id: String orderId =
		 * driver.findElement(By.cssSelector(".ng-star-inserted")).getText().split("|")[
		 * 1].trim(); System.out.println("orderId=" + orderId);
		 */
		
		//----------------------alternate way - on the Checkout page
		/* ---moving to check out page
		 * Actions a = new Actions(driver);
		 * a.sendKeys(driver.findElement(By.cssSelector(".form-group input")),"IND").build().perform(); 
		 * 
		 * WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		 * 
		 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results"))); //click country from dropdown
		 * 
		 * driver.findElement(By.xpath("//button[contains(@class,'ta-item')])[2])")).click(); //click the Place Order button
		 * 
		 * driver.findElement(By.cssSelector(".btnn")).click();
		 */
		//above encapsulated in CheckOutPage.java
		
		String countryName="INDIA";
		String expectedConfirmationMessage="INDIA";
		checkoutPage.SelectCountry(countryName);
		ConformationPage conformationPage = checkoutPage.submitOrder();
		
		//verify conf message on Confirmation page
		//String message = driver.findElement(By.cssSelector(".hero-primary")).getText();
		String actualConfirmationMessage = conformationPage.getConfirmationPage();
		
		Assert.assertTrue(actualConfirmationMessage.equalsIgnoreCase(expectedConfirmationMessage));
		//driver.close(); //3rd Time: put it in @AfterMethod in BaseTest class
		
		//tearDown(); don't have to give as its @BeforeMethod and will be executed at the end of method
		
		
	}

}
