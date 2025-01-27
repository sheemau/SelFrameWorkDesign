import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.util.NumberToTextConverter;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import graphql.Assert;

public class UploadDownLoad {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//#235 upload and download excel file.
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
		//download
		driver.findElement(By.id("downloadButton")).click();
				
		String filepath = "C://Users//shant//Downloads//download.xlsx";
		//#238 EDIT EXCEL
		//get the rownum for Apple and column num for Price and update Excel 
		int col = getColumnNumber(filepath, "Price");
		int row = getRowNumber(filepath, "Apple");
		updateCell(filepath, row, col, "599");
		
		//upload  
		// when we click "Choose File" - it will open a dialog on windows machine. 
		//But Selenium cannot handle windows. Its not a webbrowser. So you cannot select anything on File Explorer.
		//Solution: when we inspect - if we have type=File attribute - then its easy to handle
		WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
		upload.sendKeys(filepath); //send the path where the file is sitting
		
		//#236 - wait for success message to show up and wait for disappear
		By toastLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2)");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(toastLocator)); 
		String toastText = driver.findElement(toastLocator).getText();
				
		Assert.assertEquals("Updated Excel Data successfully.", toastText);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator)); 
		
		//#237: now element is visible verify excel data is updated in the web table
		//#237: code to get price of Apple. (write smart xpath to find the table row column cell based on the conditions
		//xpath: "//div[@class='sc-jsEeTM itluUR rdt_TableRow'][2]/child::div[4]"
		//to get to the Price column, go up the parent until entire column containing apple is highkighted.-->div[text() = 'Apple']/parent::div/parent::div
		//then wrtie xpath to go to the child: /div[@id='cell-4-undefined' 
		//4 - is hard coded - make it dynamic - get the column where Price is and use it: //div[text()='Price']).getAttribute("data-column-id") --> this will give 4
		
		//-- full xpath "//div[text()='Apple']/parent::div/parent::div/div[@id='cell-4-undefined']"-- smart xpath 
		String priceColumn = driver.findElement(By.xpath("//div[text()='Price'])")).getAttribute("data-column-id");
		String actualPrice = driver.findElement(By.xpath("//div[text()='Apple']/parent::div/parent::div/div[@id='cell-"+priceColumn+"-undefined']")).getText();
		Assert.assertEquals("350", actualPrice);
	}

	private static void updateCell(String filepath, int row, int col, String newPricestr) throws IOException {
		// #239: We get the worksheet, get the row and the cell based on params sent and set the new Price value in that cell
		//update the excel using workbook.write(fos)
		FileInputStream fis = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		
		Row newRow = sheet.getRow(row-1);
		Cell c = newRow.getCell(col-1);
		
		c.setCellFormula(newPricestr);
		//Write in the excel file - need FileOutputStream
		FileOutputStream fos = new FileOutputStream(filepath);
		workbook.write(fos);
		fis.close();
		workbook.close();
		
	}

	private static int getRowNumber(String filepath, String rowName) throws IOException {
		// TODO Auto-generated method stub
		//#238 get the rownum for Apple and column num for Price
		//Get the excel data sheet using POI library
		FileInputStream fis = new FileInputStream(filepath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				//get the sheet
				int numWorksheets = workbook.getNumberOfSheets();
				int fruitIndex = 1 ; //because we already have iterated over first row.
				int priceIndex = 0;
				int fruitCol=0;
				int priceCol=0;
				//get the first row - which is header
				for (int i=0;i<numWorksheets;i++) {
					
					if(workbook.getSheetName(i).equals("Sheet1")) {
						XSSFSheet sheet = workbook.getSheetAt(i);
						
						//sheet is made of rows - need to go to first row and traverse the columns to get 'Fruit' column
						Iterator<Row> rowsIterator = sheet.iterator();
						Row firstRow = rowsIterator.next();
						
						Iterator<Cell> cellIterator = firstRow.iterator();
						
						while (cellIterator.hasNext()) {
							fruitIndex++;
							priceIndex++;
							Cell cv = cellIterator.next();
							if (cv.getStringCellValue().equals("Fruit Name")) {
								fruitCol=fruitIndex;
							}
							if (cv.getStringCellValue().equals("Price")) {
								priceCol=priceIndex;
							}
						}
						Row xRows;
						//now iterate through all rows of fruit column(fruitCol) only and get 'Apple'
						while (rowsIterator.hasNext()) {
							xRows = rowsIterator.next();
							if(xRows.getCell(fruitCol).getStringCellValue().equalsIgnoreCase(rowName)) {
								
								//this row is apple  - get the price from priceCol
								String price = xRows.getCell(priceCol).getStringCellValue();
							}
							
						}
					}
					
					
				}
		return fruitCol;
	}

	private static int getColumnNumber(String filepath, String col) throws IOException {
		//#238 - getColumnNumber
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		//get the sheet
		int numWorksheets = workbook.getNumberOfSheets();
		int fruitIndex = 1 ; //because we already have iterated over first row.
		int priceIndex = 0;
		int fruitCol=0;
		int priceCol=0;
		//get the first row - which is header
		for (int i=0;i<numWorksheets;i++) {
			
			if(workbook.getSheetName(i).equals("Sheet1")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				
				//sheet is made of rows - need to go to first row and traverse the columns to get 'Fruit' column
				Iterator<Row> rowsIterator = sheet.iterator();
				Row firstRow = rowsIterator.next();
				
				Iterator<Cell> cellIterator = firstRow.iterator();
				
				while (cellIterator.hasNext()) {
					//fruitIndex++;
					
					Cell cv = cellIterator.next();
					//if (cv.getStringCellValue().equals("Fruit Name")) {
						//fruitCol=fruitIndex;
					//}
					if (cv.getCellType() == CellType.STRING && cv.getStringCellValue().equals(col)) {
						priceCol=priceIndex;
					}
					priceIndex++;
				}
				//This part gives the price value
				/*Row xRows;
				//now iterate through all rows of fruit column(fruitCol) only and get 'Apple'
				while (rowsIterator.hasNext()) {
					xRows = rowsIterator.next();
					if(xRows.getCell(fruitCol).getStringCellValue().equalsIgnoreCase(rowName)) {
						
						//this row is apple  - get the price from priceCol
						String price = xRows.getCell(priceCol).getStringCellValue();
					}
					
				}*/
			}
			
			
		}
		return priceCol;
	}

}
