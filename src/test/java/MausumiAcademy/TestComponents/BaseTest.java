package MausumiAcademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import mausumiacademy.pageobjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver; 
	public LandingPage landingPage;
	
	//below method needs to be invoked everytime. #167 (section 22)
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo(); 
		return landingPage;
	}
	
	//#167 (section 22)
	public WebDriver initializeDriver() throws IOException {
		
		// setup a global property for all drivers for multiple browsers in a global properties file.
		// java has a properties class. - it can read the global properties. It can decide at runtime  which browser to instantiate.
		// Create a new package under src/main/java and create a new file - 
		// GlobalData.properties. (remember that properties file needs to have a extension .properties)
		// 
		/** BEGIN Read from Global Properties file **/
		Properties prop = new Properties();
		// java has a class that can convert your file into inputstream object
		//FileInputStream fis = new FileInputStream(
				//"C:\\Users\\shant\\eclipse-workspace\\SelFrameworkDesign\\src\\main\\java\\mausumiacademy\\resources\\GlobalData.properties");
		
		//now the above path is too long and its a local path. So there is a System property called "user.dir" until the Project level dir
		//which is "C:\\Users\\shant\\eclipse-workspace\\SelFrameworkDesign" - replace this.
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir")+"//src//main//java//mausumiacademy//resources//GlobalData.properties");
		prop.load(fis); // argument type is InputStream
		//** END Read from Global Properties file **/
		
		//Lesson 182: commenting out below - to make this dynamic to get value from maven or properties file using Java Ternary operator
		//String browserName = prop.getProperty("browser"); // read from Property file
		
		// if System.getProperty("browser") !=null --> then take browser name from maven else - take from prop file.
		//maven command: c:>mvn -Dbrowser=Firefox (-D option)
		String browserName = System.getProperty("browser") !=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		
		
		/* ***********************commented as we added HEADLESS mode logic (add chromeheadless as browsername in Jenkins*******
		 * Chrome browser is not invoked. Chrome runs its Engine APIs to handle this.
		 * if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup(); // chrome driver will automatically be downloaded
			 driver = new ChromeDriver(); */
		
		if (browserName.contains("chrome")) {
			
			WebDriverManager.chromedriver().setup(); // chrome driver will automatically be downloaded
			
			if(!browserName.contains("headless")) {
				driver = new ChromeDriver();
			} else {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				//sometimes browsers wont open fully and elements wont be found and hence test will fail. So the
				//driver.manage().window().maximize(); was used at the end. BUt when running in headless mode it wont take much effect.
				//Hence set the window size as below: if we see any flakiness in the test, add this.
				
				driver = new ChromeDriver();
				driver.manage().window().setSize(new Dimension(1440,900));
			}
		} else if (browserName.equals("firefox")) {
			// firefox
			System.setProperty("webdriver.gecko.driver", "/Users/rahulshetty//documents/geckodriver") ;
			driver = new FirefoxDriver();
		} else if (browserName.equals("edge")) {
			// edge
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	//#167 (section 22)
	@AfterMethod(alwaysRun=true)
	public void  tearDown() throws IOException {
		driver.close(); 
	}
	
	
public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		
		System.out.println("**************filePath***************=" +filePath );
		//Java file - it will read a JSON file, scan through an entire Json and convert to String.
		String jsonContent = FileUtils.readFileToString(new File("C://Users//shant//eclipse-workspace//SelFrameworkDesign//src//test//java//MausumiAcademy//data//PurchaseOrder.json"), StandardCharsets.UTF_8);
	
		//convert String to HashMap - get new dependency called jackson databind
		ObjectMapper mapper = new ObjectMapper();
		
		//this says take the json string, return me a list of hashmaps
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		
		return data;
	}

//this Utility is taking the screenshot and returning the path where the screenshot is stored
//call this method only when test is failed and attach to html report using Extent Reports Utility
//Used in #177 also.
	public String getScreenshotPath(String testCaseName, WebDriver driver) throws IOException {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver; // cast TakesScreenshot to driver, so it can take screenshot
			File sourceFile = ts.getScreenshotAs(OutputType.FILE); // take screenshot as file format.
			// store in local workspace
			File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
			FileUtils.copyFile(sourceFile, file);

			System.out.println("Successfully captured a screenshot");
		} catch (IOException e) {
			System.out.println("Exception while taking screenshot " + e.getMessage());

		}
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	

}
