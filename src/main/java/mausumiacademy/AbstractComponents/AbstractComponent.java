package mausumiacademy.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import mausumiacademy.pageobjects.OrderPage;

public class AbstractComponent {
	
	//we need wait method
	WebDriver driver;
	//driver.findElement(By.cssSelector("[routerlink*='cart']"))
	@FindBy(css="[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(css="myorders")
	WebElement orderHeader;
	
	public AbstractComponent(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void waitForElementToAppear(By findBy) {
		
		WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(5));
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(findBy));
	}
	
public void waitForElementToDisappear(WebElement ele) {
		
		WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(5));
		//wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animated"))));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}

public void goToCartPage() {
	cartHeader.click();
	
}

public OrderPage goToOrdersPage() {
	orderHeader.click();
	return new OrderPage(driver);
	
	
}

}
