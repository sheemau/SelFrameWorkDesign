package mausumiacademy.pageobjects;

import java.util.List;
import java.util.Collection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mausumiacademy.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent{
	
	WebDriver driver;
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> productNames;
	
	public OrderPage(WebDriver driver) {
		//initialize at the begining before any processing happens. 
		//here we need to initialize driver - bring driver to landing page
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean VerifyOrderDisplay(String productName) {
		
		//we have to get the list of all products(4th column which has prod_name) in the grid in 
		//the order history page, iterate through them and
		//make sure 'ZARA COAT 3' is present.
		
		
		boolean match=productNames.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}

}
