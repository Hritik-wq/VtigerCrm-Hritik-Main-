package crm.vtiger.opportunities;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

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

		// Store the String Opportunity Name
		String opporName = "21 din me paisa double";
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
		driver.findElement(By.linkText("vtiger")).click();

		Thread.sleep(3000);

		// Switch back to parent
		driver.switchTo().window(parentWindow);

		// Assigned To Radio Button
		driver.findElement(By.xpath("//input[@type='radio'][1]"));

		// To select Specific Value from Sales Stage Dropdown
		WebElement salesStage = driver.findElement(By.name("sales_stage"));

		Select sel = new Select(salesStage);

		sel.selectByValue("Qualification");

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
