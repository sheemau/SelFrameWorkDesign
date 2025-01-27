package mausumiacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mausumiacademy.AbstractComponents.AbstractComponent;

public class ProductCatalog  extends AbstractComponent {
	
	WebDriver driver;
	
	public ProductCatalog(WebDriver driver) {
		//initialize at the begining before any processing happens. 
		//here we need to initialize driver - bring driver to landing page
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	
	//WebElement userPassowrd = driver.findElement(By.id("userPassword"));
	//WebElement submitButton = driver.findElement(By.id("login"));
	
	//PageFactory
	//List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(id="userPassword")
	WebElement userPassowrd;
	
	@FindBy(css=".ng-animated")
	WebElement ele;
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage= By.id("toast-container");
	
	//Action Method.
	public List<WebElement> getProductList() {
		
		waitForElementToAppear(productsBy);
		
		return products;
		
	}
	
	public WebElement getProductByName(String productName) {
		//WebElement prod = products.stream().filter(product-> 
		//product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null); //scan element
		
		//products on the above line is  replaced by getProductList()
		WebElement prod = getProductList().stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null); //scan element
		System.out.println("got webelement");
		return prod;
	}
	
	public CartPage addProuctToCart(String productName) {
		//note if it is driver.findElement - we can apply pageFactory FindBy - but its prod.findElement(scope of search is prod)
		//driver.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		//so create a PageFactory for it.
		//prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		////wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animated"))));
		waitForElementToDisappear(ele);
		CartPage cartPage = new CartPage(driver);
		return cartPage;
		
	}
	

}
