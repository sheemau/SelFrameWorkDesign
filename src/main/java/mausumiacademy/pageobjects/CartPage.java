package mausumiacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mausumiacademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	
	WebDriver driver;
	
	//PageFactory
	//driver.findElement(By.cssSelector(".totalRow button"))
	@FindBy(css=".totalRow button")
	WebElement checkoutEle;
	
	
	
	//List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	@FindBy(css=".cartSection h3")
	private List<WebElement> cartProducts;
	
	public CartPage(WebDriver driver) {
		//initialize at the begining before any processing happens. 
		//here we need to initialize driver - bring driver to landing page
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean VerifyProductDisplay(String productName) {
		boolean match=cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckOutPage goToCheckout() {
		checkoutEle.click();
		return new CheckOutPage(driver);
	}

}
