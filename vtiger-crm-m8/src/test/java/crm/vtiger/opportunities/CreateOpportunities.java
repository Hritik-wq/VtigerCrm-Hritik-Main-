package crm.vtiger.opportunities;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import generic_utility.FileUtility;

public class CreateOpportunities {
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

//		Click Opportunities
		driver.findElement(By.linkText("Opportunities")).click();

		// Create Opportunity
		driver.findElement(By.cssSelector("img[alt='Create Opportunity...']")).click();

//		fill form
		WebElement opporField = driver.findElement(By.name("potentialname"));

		// Load test data from Excel for opportunityName
		String orgName = fUtil.getDatafromExcelFile("testdata", 1, 1);

		opporField.sendKeys(orgName);

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

		// Load test data from Excel for realtedField
		String relatedTo = fUtil.getDatafromExcelFile("testdata", 1, 3);

		driver.findElement(By.linkText(relatedTo)).click();

		Thread.sleep(3000);

		// Switch back to parent
		driver.switchTo().window(parentWindow);

		// Assigned To Radio Button
		driver.findElement(By.xpath("//input[@type='radio'][1]"));

		// To select Specific Value from Sales Stage Dropdown
		WebElement salesStage = driver.findElement(By.name("sales_stage"));

		// Load test data from Excel for Sales Stage
		String stage = fUtil.getDatafromExcelFile("testdata", 2, 4);

		Select sel = new Select(salesStage);

		sel.selectByValue(stage);

		// To Click on Save Button
		driver.findElement(By.cssSelector("input[type='submit'][value='  Save  ']")).click();

		// verify organization
		String actOrgName = driver.findElement(By.id("dtlview_Opportunity Name")).getText();

		if (actOrgName.equals(orgName)) {
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
