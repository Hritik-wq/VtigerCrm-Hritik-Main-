package crm.vtiger.opportunities;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateOpportunities {
	public static void main(String[] args) throws InterruptedException {
//		opening browser		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

//		login
		driver.get("http://localhost:8888");

		WebElement un = driver.findElement(By.name("user_name"));
		un.sendKeys("admin");

		WebElement pwd = driver.findElement(By.name("user_password"));
		pwd.sendKeys("manager");

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
