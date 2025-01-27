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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import MausumiAcademy.TestComponents.BaseTest;
import MausumiAcademy.TestComponents.Retry;

public class ErrorValidationsTest extends BaseTest {
	
	//public static void main(String[] args) { //made is a Test class using testng annotation @Test
	
	//#176
	
	@Test(groups= {"ErrorHandling"}, retryAnalyzer=Retry.class) //retry mechanism #180
	//@Test(groups= {"ErrorHandling"}) 
	
	public void LoginErrorValidation() throws IOException {
		String productName = "ZARA COAT 3";
		String email = "sheema05@gmail.com";
		String password = "abcddd";
		landingPage.loginApplication(email, password);
		String ExpecteErrordMessage = "Incorrect Email or password.";
		
		String actualErrorMessage = landingPage.getErrorMessage();
		
		Assert.assertEquals( ExpecteErrordMessage,actualErrorMessage);

	}
	
	
	@Test
	public void ProductErrorValidation() throws IOException {
		String productName = "ZARA COAT 3";
		String email = "sheema05@gmail.com";
		String password = "abcddd";
		landingPage.loginApplication(email, password);
		String ExpecteErrordMessage = "Incorrect Email or password.";
		
		String actualErrorMessage = landingPage.getErrorMessage();
		
		Assert.assertEquals( ExpecteErrordMessage,actualErrorMessage);

	}

}
