package crm.vtiger.opportunities;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateOpportunities {
	public static void main(String[] args) throws InterruptedException, IOException {
//		opening browser		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

//		step 1:> create a Java Rep. Object of the physical file
		FileInputStream fis = new FileInputStream("./src/test/resources/cd.properties");

//		step 2:> by using load(), load all the keys
		Properties pObj = new Properties();
		pObj.load(fis);

//		step 3:> by using getProperty("key") get the value by passing "key"
		String URL = pObj.getProperty("url");
		String USERNAME = pObj.getProperty("un");
		String PASSWORD = pObj.getProperty("pwd");

		System.out.println(URL);
		System.out.println(USERNAME);
		System.out.println(PASSWORD);

//		login
		driver.get(URL);

		WebElement un = driver.findElement(By.name("user_name"));
		un.sendKeys(USERNAME);

		WebElement pwd = driver.findElement(By.name("user_password"));
		pwd.sendKeys(PASSWORD);
		WebElement loginBtn = driver.findElement(By.id("submitButton"));
		loginBtn.click();

//		Click Opportunities
		driver.findElement(By.linkText("Opportunities")).click();

		// Create Opportunity
		driver.findElement(By.cssSelector("img[alt='Create Opportunity...']")).click();

//		fill form
		WebElement opporField = driver.findElement(By.name("potentialname"));
		
		FileInputStream fis2 = new FileInputStream("./src/test/resources/testScriptData.xlsx");

		// Get the Access of Workbook
		Workbook wb = WorkbookFactory.create(fis2);
		
		// Get access of Sheet
		Sheet sheet = wb.getSheet("testdata");
		
		// Get access of Row
		Row row = sheet.getRow(1);
		
		// Get access of Cell
		Cell cell = row.getCell(0);

		// Get the data
		String data = cell.getStringCellValue();

		System.out.println(data);

		// To close the workbook
		wb.close();

		// Store the String Opportunity Name
		String opporName = data;
		opporField.sendKeys(opporName);

		// Need to Change Window to select Link

		String parentWindow = driver.getWindowHandle();

		WebElement relatedField = driver.findElement(By.cssSelector("img[alt='Select']"));

		relatedField.click();

		Set<String> allWindows = driver.getWindowHandles();

		for (String i : allWindows) {

			driver.switchTo().window(i);

		}

		for (String windowHandle : allWindows) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}

		// Interact In Popup
		FileInputStream fis3 = new FileInputStream("./src/test/resources/testScriptData.xlsx");

		// Get the Access of Workbook
		Workbook wb2 = WorkbookFactory.create(fis3);
		
		// Get access of Sheet
		Sheet sheet2 = wb2.getSheet("testdata");
		
		// Get access of Row
		Row row2 = sheet2.getRow(2);
		
		// Get access of Cell
		Cell cell2 = row2.getCell(1);

		// Get the data
		String data2 = cell2.getStringCellValue();

		System.out.println(data2);

		// To close the workbook
		wb2.close();
		
		driver.findElement(By.linkText(data2)).click();

		Thread.sleep(3000);

		// Switch back to parent
		driver.switchTo().window(parentWindow);

		// Assigned To Radio Button
		driver.findElement(By.xpath("//input[@type='radio'][1]"));

		// To select Specific Value from Sales Stage Dropdown
		WebElement salesStage = driver.findElement(By.name("sales_stage"));
		
		FileInputStream fis4 = new FileInputStream("./src/test/resources/testScriptData.xlsx");

		// Get the Access of Workbook
		Workbook wb3 = WorkbookFactory.create(fis4);
		
		// Get access of Sheet
		Sheet sheet3 = wb3.getSheet("testdata");
		
		// Get access of Row
		Row row3 = sheet3.getRow(2);
		
		// Get access of Cell
		Cell cell3 = row3.getCell(4);

		// Get the data
		String data3 = cell3.getStringCellValue();

		System.out.println(data3);

		// To close the workbook
		wb.close();

		Select sel = new Select(salesStage);

		sel.selectByValue(data3);

		// To Click on Save Button
		driver.findElement(By.cssSelector("input[type='submit'][value='  Save  ']")).click();

		// verify product
		String actOpporName = driver.findElement(By.id("dtlview_Opportunity Name")).getText();

		if (actOpporName.equals(opporName)) {
			System.out.println("Opportunity created successfullyy !!!!");
		} else {
			System.out.println("Better luck next time... Dingeshhh");
		}

		// logout
		WebElement profileIcon = driver.findElement(By.cssSelector("img[src='themes/softed/images/user.PNG']"));
		profileIcon.click();

		driver.findElement(By.linkText("Sign Out")).click();

		// browser close
		Thread.sleep(3000);
		driver.quit();
	}
}
