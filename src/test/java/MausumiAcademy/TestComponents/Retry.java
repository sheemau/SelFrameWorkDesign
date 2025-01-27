package MausumiAcademy.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	
	//------------------------Lesson #180
	int count=0;
	int maxRetry = 1;

	@Override
	public boolean retry(ITestResult result) {
		
		//write logic to rerun or not.In this case, logic is to do 1 retry max.
		if(count < maxRetry) {
			count++;
			return true; //as long as method returns true, test will retry again and again
			
		}
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
