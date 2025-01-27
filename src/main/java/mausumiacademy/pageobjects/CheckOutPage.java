package mausumiacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mausumiacademy.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent {

	WebDriver driver;
	
	@FindBy(css=".form-group input")
	WebElement countryTextBox;
	
	By aa = By.cssSelector(".ta-results");
	
	@FindBy(xpath="//button[contains(@class,'ta-item')])[2])")
	WebElement countryDropdown;
	
	@FindBy(css=".btnn")
	WebElement placeOrderButton;
	
	public CheckOutPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/* 
	 * below code from standalone page refactored below
	 * a.sendKeys(driver.findElement(By.cssSelector(".form-group input")),
			 * "IND").build().perform(); WebDriverWait wait = new WebDriverWait(driver,
			 * Duration.ofSeconds(5));
			 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector
			 * (".ta-results"))); //choose  country from dropdown
			 * driver.findElement(By.xpath("//button[contains(@class,'ta-item')])[2])")).
			 * click(); //click the Place Order button
			 * driver.findElement(By.cssSelector(".btnn")).click();
	*/
	
	public void SelectCountry(String countryName) {
		
		Actions a = new Actions(driver);
		a.sendKeys(countryTextBox, countryName).build().perform();
		waitForElementToAppear(aa);
		countryDropdown.click();
		
		
	}
	
	public ConformationPage submitOrder() {
		placeOrderButton.click();
		return new ConformationPage(driver);
	}
}
