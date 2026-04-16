package crm.vtiger.Products;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import generic_utility.FileUtility;

public class CreateProducts {
	public static void main(String[] args) throws InterruptedException, IOException {
//		opening browser		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

//		We Used Generic Utility Package and Used Fileutility.java to get the data from Properties File in one go
		FileUtility fUtil = new FileUtility();
		String URL = fUtil.getDataFromPropFile("url");
		String USERNAME = fUtil.getDataFromPropFile("un");
		String PASSWORD = fUtil.getDataFromPropFile("pwd");

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

		// Load test data from Excel for productName
		String prodName = fUtil.getDatafromExcelFile("testdata", 2, 2);

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
