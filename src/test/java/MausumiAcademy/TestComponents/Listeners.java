package MausumiAcademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import mausumiacademy.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	//#143, #177
	
	ExtentTest testObj;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> testThreadsafe = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestStart(result);
		testObj = extent.createTest(result.getMethod().getMethodName());
		testThreadsafe.set(testObj) ; //set the test object thus making it thread safe 
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestSuccess(result);
		//test.log(Status.PASS, "Test Passed");
		testThreadsafe.get().log(Status.PASS, "Test Passed"); //get the test for this thread do the action.
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestFailure(result); //auto-generated
		//making thread safe to avoid concurrency issues
		
		//test.fail(result.getThrowable());
		testThreadsafe.get().fail(result.getThrowable()); //get the test for this thread do the action.
		
		try {
			//get driver - its a bit tricky
			driver = (WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//*************Lesson 178***************: TAKE FAilure screenshot
		String filePath=null;
		try {
			filePath = getScreenshotPath(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //testcase name is needed
		
		//178 (attach screenshot to report, 
		//commented out to make it threadsafe, addScreenCaptureFromPath --> this is extent report method
		//test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); //filepath, Methodname (of how its to show in report
		testThreadsafe.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); //**********#179 - make it threadsafe
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestSkipped(result);
		String methodName=result.getMethod().getMethodName();
        String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " - SKIPPED"+"</b>";     
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
        testThreadsafe.get().skip(markup);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		//ITestListener.super.onFinish(context);
		//Once all your tests are complete, you call the flush() method on the ExtentReports object.
		//This signals the library to compile all the logged information and generate the final HTML report
		extent.flush();
	}

	
	
}
