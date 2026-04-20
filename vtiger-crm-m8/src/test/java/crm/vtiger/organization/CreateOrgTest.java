package crm.vtiger.organization;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import generic_utility.FileUtility;
import generic_utility.WebDriverUtility;

public class CreateOrgTest {
	public static void main(String[] args) throws InterruptedException, IOException {

//		opening browser		
		WebDriver driver = new ChromeDriver();
		
		WebDriverUtility wdUtil = new WebDriverUtility(driver);
		wdUtil.maxWin();
		wdUtil.waitForPageLoad();

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

//		Click organization
		driver.findElement(By.linkText("Organizations")).click();

		// Create Organization
		driver.findElement(By.cssSelector("img[alt='Create Organization...']")).click();

//		fill form
		WebElement orgField = driver.findElement(By.name("accountname"));
		
		// Load test data from Excel for organizationName
		String orgName = fUtil.getDatafromExcelFile("testdata", 1, 1);

		orgField.sendKeys(orgName);

		driver.findElement(By.cssSelector("input[type='button'][value='  Save  ']")).click();

		// verify opportunity
		String actOppName = driver.findElement(By.id("dtlview_Organization Name")).getText();

		if (actOppName.equals(orgName)) {
			System.out.println("Organization created successfullyy !!!!");
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
