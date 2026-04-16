package crm.vtiger.Sales;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import generic_utility.FileUtility;

public class CreateSales {
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

//		Click More for Sales
		driver.findElement(By.linkText("More")).click();

		driver.findElement(By.linkText("Sales Order")).click();

		// Create Sales Order
		driver.findElement(By.cssSelector("img[alt='Create Sales Order...']")).click();

//		fill form
		WebElement salesField = driver.findElement(By.name("subject"));

		// Select Subject from Excel File for Sales Order
		String salesName = fUtil.getDatafromExcelFile("testdata", 2, 5);
		salesField.sendKeys(salesName);

		// To select Specific Value from Status
		WebElement status = driver.findElement(By.name("sostatus"));
		
		// Select Status from Excel File -nGeneric Utility Package ---> Used Fileutility.java
		String Status = fUtil.getDatafromExcelFile("testdata", 2, 9);

		Select sel = new Select(status);

		sel.selectByValue(Status);

		// Assigned To Radio Button
		driver.findElement(By.xpath("//input[@type='radio'][1]"));

		// Change window and Click

		String parentWindow = driver.getWindowHandle();

		// WebElement relatedField =
		// driver.findElement(By.cssSelector("img[alt='Select']"));

		WebElement relatedField = driver.findElement(
				By.xpath("//img[@src='themes/softed/images/select.gif' and contains(@onclick, 'window.open')]"));

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

		// Enter Organization Name from Excel - Generic Utility Package ---> Used Fileutility.java
		String orgName = fUtil.getDatafromExcelFile("testdata", 2, 1);

		// Interact In Popup
		driver.findElement(By.linkText(orgName)).click();
		Alert ale = driver.switchTo().alert();
		ale.accept();

		Thread.sleep(3000);

		// Switch back to parent
		driver.switchTo().window(parentWindow);

		// Shipping Address changes according to Organization Name Selected
		// driver.findElement(By.name("ship_street")).sendKeys("Tokyo");

		// item name in item details open window

		String parentWindow2 = driver.getWindowHandle();

		String itemName = fUtil.getDatafromExcelFile("testdata", 4, 7);

		driver.findElement(By.id("searchIcon1")).click();
		Thread.sleep(2000);

		Set<String> allWindows2 = driver.getWindowHandles();

		for (String j : allWindows2) {

			driver.switchTo().window(j);

		}

		for (String windowHandle : allWindows2) {
			if (!windowHandle.equals(parentWindow2)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}

		// Select item name in Select Product Popup
		driver.findElement(By.linkText(itemName)).click();

		Thread.sleep(3000);

		// Switch back to parent
		driver.switchTo().window(parentWindow);

		// Enter Quantity from Excel File - Generic Utility Package ---> Used Fileutility.java
		String qty = fUtil.getDatafromExcelFile("testdata", 2, 8);

		driver.findElement(By.id("qty1")).sendKeys(qty);

		driver.findElement(By.cssSelector("input[type='submit'][value='  Save  ']")).click();

//		verify Sales
		String actSaleName = driver.findElement(By.id("dtlview_Subject")).getText();

		if (actSaleName.equals(salesName)) {
			System.out.println("Sales Order created successfullyy !!!!");
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
