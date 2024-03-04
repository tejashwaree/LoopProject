package loop.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import loop.AbstarctComponents.AbstractComponents;


public class LandingPage extends AbstractComponents{
WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		//initialization
		super(driver);
		this.driver = driver;
		//PageFactory.initElements(driver, this); //created this to user driver in findby page factory 
	}
	
	public  void loginApplication(String email, String pasword) throws InterruptedException {
		driver.findElement(By.xpath("(//input[@type='text'])[1]")).sendKeys(email);
		driver.findElement(By.xpath("(//input[@type='password'])[1]")).sendKeys(pasword);
		driver.findElement(By.xpath("(//*[@data-testid='login-button']/span)[1]")).click();
		Thread.sleep(3000);
	}

	public void goTo() {
		driver.get("https://app.tryloop.ai/login/password");
	}
	
	public void skip(WebDriver driver) {
		driver.findElement(By.xpath("//button[.='Skip for now']")).click();
	}
	public void selectmenu(WebDriver driver) {
		driver.findElement(By.xpath("//span[.='3P Chargebacks']")).click();
		driver.findElement(By.xpath("//span[.='History by Store']")).click();
	}
	
	public void selectTransaction(WebDriver driver) {
		driver.findElement(By.xpath("//span[.='3P Chargebacks']")).click();
		driver.findElement(By.xpath("//span[.='Transactions']")).click();
		
	}
}
