	package MausumiAcademy.tests;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTestBackup {
	
	public static void main(String[] args) {
		
		//#153
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup(); //chrome driver will automatically be downloaded
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("http://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("sheema05@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Tapsium@1");
		driver.findElement(By.id("login")).click();
		System.out.println("After loGIN");
		//wait strategy
		WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3")); //get product list
		
		//#154: Java Streams - iterate through products to have a match for Zara Coat 3
		//Note here that we are doing product.findElement() and not driver.findElement()
		//After inspecting, we find the product (entire product box is highlighted. It has the text 'ZARA COAT 3'
		//few more steps down. so if we do driver.findElement() - it will scan entire page.
		//doing product.findElement() - will scan in the product code section only and we can check easily.
		WebElement prod = products.stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null); //scan element
		
		//Add to Cart
		System.out.println("got webelement");
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click(); //note prod.findElement
		
		System.out.println("Clicked Cart");
		
		System.out.println("END");
		
		//#155 Once we click 'Add to Button', some loading icon is happening and we get a Toast message - 'Product added to Cart' 
		//we have to ensure the loading icon disappears and toast messge comes and goes.
		//WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(5)); //moved above
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("toast-container")));
		
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animated"))); //taking long
		
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animated"))));
		//find cart
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//#156: 	
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
		
		//we have to see if the item is there in the cart page - use Java Streams --> anyMatch
		boolean match=cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		//click checkout button
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//#157: on the Place Order page - Autosuggestive dropdown -- see solution by instructor below
		driver.findElement(By.cssSelector(".form-group input")).sendKeys("IND");
		driver.findElement(By.cssSelector(".form-group input")).sendKeys(Keys.DOWN);
		driver.findElement(By.cssSelector(".form-group input")).sendKeys(Keys.DOWN);
		//click the Place Order button
		driver.findElement(By.cssSelector(".btnn")).click();
		
		//grab the order id:
		String orderId = driver.findElement(By.cssSelector(".ng-star-inserted")).getText().split("|")[1].trim();
		System.out.println("orderId=" + orderId);
		
		//----------------------alternate way -- by instructor
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector(".form-group input")), "IND").build().perform(); //find webelement, type country name (IND)
		
		//its taking a few seconds for autosuggestive dropdown to load: so use wait until it shows up.
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results")));
		//click country
		driver.findElement(By.xpath("//button[contains(@class,'ta-item')])[2])")).click(); //since we know India comes as second option, we are choosing [2]
		//click the Place Order button
		driver.findElement(By.cssSelector(".btnn")).click();
		
		//verify conf message
		String message = driver.findElement(By.cssSelector(".hero-primary")).getText();
		
		Assert.assertTrue(message.equalsIgnoreCase("THANK YOU FOR YOUR ORDER."));
		driver.close();
		
		
		
		
	}

}
