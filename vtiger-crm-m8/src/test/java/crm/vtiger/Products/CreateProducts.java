package crm.vtiger.Products;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateProducts {
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

//		Click Product
		driver.findElement(By.linkText("Products")).click();

		// Create Products
		driver.findElement(By.cssSelector("img[alt='Create Product...']")).click();

//		fill form
		WebElement prodField = driver.findElement(By.name("productname"));
		
		FileInputStream fis2 = new FileInputStream("./src/test/resources/testScriptData.xlsx");

		// Get the Access of Workbook
		Workbook wb = WorkbookFactory.create(fis2);
		
		// Get access of Sheet
		Sheet sheet = wb.getSheet("testdata");
		
		// Get access of Row
		Row row = sheet.getRow(2);
		
		// Get access of Cell
		Cell cell = row.getCell(2);

		// Get the data
		String data = cell.getStringCellValue();

		System.out.println(data);

		// To close the workbook
		wb.close();

		String prodName = data;
		prodField.sendKeys(prodName);

		// Assigned To Radio Button
		driver.findElement(By.xpath("//input[@type='radio'][1]"));

		driver.findElement(By.cssSelector("input[type='submit'][value='  Save  ']")).click();

//		verify Opportunity
		String actProdName = driver.findElement(By.id("dtlview_Product Name")).getText();

		if (actProdName.equals(prodName)) {
			System.out.println("Product created successfullyy !!!!");
		} else {
			System.out.println("Better luck next time... Dingeshhh");
		}

//		Logout
		WebElement profileIcon = driver.findElement(By.cssSelector("img[src='themes/softed/images/user.PNG']"));
		profileIcon.click();

		driver.findElement(By.linkText("Sign Out")).click();

//		Browser close		
		Thread.sleep(3000);
		driver.quit();
	}
}
