package mausumiacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mausumiacademy.AbstractComponents.AbstractComponent;

public class ConformationPage extends AbstractComponent {
	

	WebDriver driver;
	public ConformationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//String message = driver.findElement(By.cssSelector(".hero-primary")).getText();
	
	@FindBy(css=".hero-primary")
	WebElement confirm;
	
	public String getConfirmationPage( ) {
		return confirm.getText();
		
	}
	
	

}
