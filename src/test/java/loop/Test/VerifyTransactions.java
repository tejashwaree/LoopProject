package loop.Test;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import loop.PageObjects.LandingPage;
import loop.PageObjects.Transactions;
import loop.TestComponents.BaseTest1;

public class VerifyTransactions extends BaseTest1 {
	@Test
	public void verifyTransactions() throws InterruptedException, IOException {
		launchApplication();
		LandingPage landingPage = new LandingPage(driver);
		landingPage.loginApplication("qa-engineer-assignment@test.com","QApassword123$");
		//landingPage.skip(driver);
		landingPage.selectTransaction(driver);
		Transactions transactions = new Transactions(driver);
		transactions.filtertable();
		transactions.writeInCSV();
		System.out.println("data added");
		transactions.sortCSVFile(System.getProperty("user.dir")+"\\Files\\transactions.csv");
		tearDown();
	}
}
