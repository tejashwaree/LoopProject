package loop.PageObjects;

import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import junit.framework.Assert;

public class ChargebacksPage {
	private static final DecimalFormat decfor = new DecimalFormat("0.00");

	public static void countTotal(WebDriver driver) throws InterruptedException {
		Thread.sleep(20000);
		int count = 0;
		int p = 0;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement btn = driver.findElement(By.xpath("(//div[@role='button' and @aria-expanded='false'])"));
		Actions act = new Actions(driver);
		act.click(btn).build().perform();

		Thread.sleep(3000);
		WebElement btn1 = driver.findElement(By.xpath("(//li[@role='option'])[3]"));
		js.executeScript("arguments[0].click();", btn1);

		for (int i = 2; i <= 8; i++) {
			int g = 0;
			double total = 0;
			for (int j = 1; j <= 50; j++) {
				String amt = driver.findElement(By.xpath("//tbody/tr[" + j + "]/td[" + i + "]/h6[1]")).getText();
				String amtneeded = amt.replace("$", "");
				amtneeded = amtneeded.replace(",", "");
				total = total + Double.parseDouble(amtneeded);
				p = j;
			}
			if (p == 50) {
				WebElement btn2 = driver
						.findElement(By.xpath("(//*[name()='svg'][@data-testid='ChevronRightIcon'])[2]"));
				act.click(btn2).build().perform();
				Thread.sleep(3000);
				count += 1;
				for (int l = 1; l <= 50; l++) {
					String amt1 = driver.findElement(By.xpath("//tbody/tr[" + l + "]/td[" + i + "]/h6[1]")).getText();
					String amtneeded1 = amt1.replace("$", "");
					amtneeded1 = amtneeded1.replace(",", "");
					total = total + Double.parseDouble(amtneeded1);

				}
			}

			if (count == 1) {
				WebElement btn3 = driver
						.findElement(By.xpath("(//*[name()='svg'][@data-testid='ChevronRightIcon'])[2]"));
				act.click(btn3).build().perform();
				Thread.sleep(3000);
				for (int k = 1; k <= 6; k++) {
					String amt1 = driver.findElement(By.xpath("//tbody/tr[" + k + "]/td[" + i + "]/h6[1]")).getText();
					String amtneeded1 = amt1.replace("$", "");
					total = (total + Float.parseFloat(amtneeded1));
					g = k;
				}
			}

			String totalamt = "$" + decfor.format(total);
			String month = driver.findElement(By.xpath("(//div[@class='MuiBox-root css-1rr4qq7'])[" + (i + 1) + "]"))
					.getText();
			System.out.println("Conparision of grand total of month : " + month);

			System.out.println("Calculated total :" + totalamt);
			Thread.sleep(3000);

			String ele = driver.findElement(By.xpath("//tbody/tr[" + (g + 2) + "]/td[" + i + "]/h6[1]")).getText();
			String element = ele.replace(",", "");

			System.out.println("Total amt on page :" + element);
			if(element == totalamt)
			{
				System.out.println("Calculated total and total amount on page, both are same");
			}

			WebElement btn4 = driver.findElement(By.xpath("(//*[name()='svg'][@data-testid='ChevronLeftIcon'])[2]"));
			for (int s = 0; s < 2; s++) {

				act.click(btn4).build().perform();
				Thread.sleep(2000);
			}
			count = 0;
		}

	}
}
