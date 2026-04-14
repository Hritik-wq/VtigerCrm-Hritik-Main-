package crm.vtiger.organization;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateOrgTest {
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

//		Click organization
		driver.findElement(By.linkText("Organizations")).click();
		
		// Create Organization
		driver.findElement(By.cssSelector("img[alt='Create Organization...']")).click();

//		fill form
		WebElement orgField = driver.findElement(By.name("accountname"));

		String orgName = "awp";
		orgField.sendKeys(orgName);

		driver.findElement(By.cssSelector("input[type='button'][value='  Save  ']")).click();

//		verify Opportunity
		String actOppName = driver.findElement(By.id("dtlview_Opportunity Name")).getText();

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
