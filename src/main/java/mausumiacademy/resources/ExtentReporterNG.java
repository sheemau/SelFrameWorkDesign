package mausumiacademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {
	
	//#177
	public static ExtentReports getReportObject() { //declare as static, so that we can directly call it.
		
		String filepath = System.getProperty("user.dir")+"\\reports\\index.html";
		System.out.println("filepath=" + filepath);
		ExtentSparkReporter reporter = new ExtentSparkReporter(filepath); //used for creating an HTML file, and it accepts a file path as a parameter.
		//ExtentSparkReporter is also used to customize the extent reports. It allows many configurations to be made through the config() method. 
		//Some of the configurations that can be made are described below.
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		reporter.config().setTheme(Theme.DARK);
		//htmlReporter.config().setTheme(Theme.STANDARD);
		reporter.config().setEncoding("utf-8");
		
				
		ExtentReports extent = new ExtentReports(); //using this extent obj, we are going to create entry for  each and every test
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Mausumi Shee");
		extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Build no", "B-12673");
		//extent.createTest(filepath); //create in @Test 
		return extent;
	}

}
