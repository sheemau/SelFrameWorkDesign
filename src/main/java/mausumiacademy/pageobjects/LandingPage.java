package mausumiacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mausumiacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
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
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassowrd;
	
	@FindBy(id="login")
	WebElement submitButton;
	
	@FindBy(css="")
	WebElement ErrorMessageToaster;
	
	//Action Method.
	//public void loginApplication(String email, String passwrd ) {
	public ProductCatalog loginApplication(String email, String passwrd ) {
		userEmail.sendKeys(email);
		userPassowrd.sendKeys(passwrd);
		submitButton.click();
		ProductCatalog productCatalog = new ProductCatalog(driver);
		return productCatalog;
	}
	
	public void goTo() {
		driver.get("http://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage() {
		
		return ErrorMessageToaster.getText();
	}

}
