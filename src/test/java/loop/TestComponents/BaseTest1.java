package loop.TestComponents;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import loop.PageObjects.LandingPage;

public class BaseTest1 {
	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializedriver() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		// implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	public LandingPage launchApplication() {
		driver = initializedriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	
	public void tearDown() {
		driver.quit();
	}

}
