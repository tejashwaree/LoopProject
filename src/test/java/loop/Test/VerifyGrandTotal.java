package loop.Test;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import loop.PageObjects.ChargebacksPage;
import loop.PageObjects.LandingPage;
import loop.TestComponents.BaseTest1;

public class VerifyGrandTotal extends BaseTest1{
	
	@Test
	public void verifyGrandTotal()throws InterruptedException {
	
		launchApplication();
		LandingPage landingPage = new LandingPage(driver);
		landingPage.loginApplication("qa-engineer-assignment@test.com","QApassword123$");
		//landingPage.skip(driver);
		landingPage.selectmenu(driver);
		ChargebacksPage chargebacksPage = new ChargebacksPage();
		chargebacksPage.countTotal(driver);
		tearDown();
	}
}
