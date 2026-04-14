package crm.vtiger.Products;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateProducts {
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

//		Click Product
		driver.findElement(By.linkText("Products")).click();
		
		// Create Products
		driver.findElement(By.cssSelector("img[alt='Create Product...']")).click();

//		fill form
		WebElement prodField = driver.findElement(By.name("productname"));

		String prodName = "Samay Raina";
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
