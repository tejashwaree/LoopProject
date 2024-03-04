package loop.PageObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.google.common.io.Files;
import com.opencsv.CSVWriter;

import loop.AbstarctComponents.AbstractComponents;

public class Transactions extends AbstractComponents {
	public WebDriver driver;

	public Transactions(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// #view-table-id
	public void filtertable() throws InterruptedException {
		driver.findElement(By.xpath("(//button[@data-testid='selectBtn'])[1]")).click();
		driver.findElement(By.xpath("//button[.='Clear']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@aria-label='Artisan Alchemy']/span")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@aria-label='Blissful Buffet']/span")).click();
		driver.findElement(By.xpath("(//button[@data-testid='applyBtn'])[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[@data-testid='selectBtn'])[3]")).click();
		driver.findElement(By.xpath("//button[.='Clear']")).click();
		driver.findElement(By.xpath("//div[@aria-label='Grubhub']/span")).click();
		driver.findElement(By.xpath("(//button[@data-testid='applyBtn'])[1]")).click();
	}

	public void writeInCSV() throws IOException, InterruptedException {
		CSVWriter writer = new CSVWriter(new FileWriter("Files\\transactions.csv"));
		String[] data = new String[8];
		// add headings
		for (int i = 0; i < 8; i++) {
			data[i] = driver.findElement(By
					.xpath("//div[@class='MuiBox-root css-mycntp']/table//thead//tr/th[" + (i + 1) + "]//div//div//h6"))
					.getText();
		}
		writer.writeNext(data);
		// add data
		writer.flush();
		// select 20 rows
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement btn = driver.findElement(By.xpath("(//div[@role='button' and @aria-expanded='false'])"));
		Actions act = new Actions(driver);
		act.click(btn).build().perform();
		Thread.sleep(3000);
		WebElement btn1 = driver.findElement(By.xpath("(//li[@role='option'])[2]"));
		js.executeScript("arguments[0].click();", btn1);

		// select order id
		//// div[@class='MuiBox-root css-mycntp']/table/tbody/tr[1]/td[1]/div/h6
		// tr[1]- for 1st order id , the check for type, if it is 3 then next order id
		// will be 1+3=tr[4], if 2 then 1+2=tr[3], next time it will
		// be added in tr

		// first order id
		String[] data1 = new String[8];
		int p = 1;
		int c = 0;
		String type = "REVERSAL";
		for (int i = 0; i < 14; i++) {
			String text = driver
					.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[3]/h6"))
					.getText();
			if (text.equalsIgnoreCase(type)) {
				c = p;
				for (int j = 0; j < 3; j++) { // 0,1,2

					if (j == 0) {
						// c = p;
						// order id
						data1[0] = driver.findElement(By.xpath(
								"//div[@class='MuiBox-root css-mycntp']/table//tbody//tr[" + c + "]//td[1]/div/h6"))
								.getText();
						// location
						data1[1] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[2]/h6"))
								.getText();
						// Order state
						data1[2] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[3]/h6"))
								.getText();
						// type
						data1[3] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 4) + "]/div/span")).getText();
						// lost sale
						data1[4] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 5) + "]/h6")).getText();
						// net payout
						data1[5] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 6) + "]/h6")).getText();
						// payout id
						data1[6] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 7) + "]/h6")).getText();
						// payout date
						data1[7] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 8) + "]/h6")).getText();
						writer.writeNext(data1);
						p += 1; // 2
					} else {

						data1[0] = driver.findElement(By.xpath(
								"//div[@class='MuiBox-root css-mycntp']/table//tbody//tr[" + c + "]//td[1]/div/h6"))
								.getText();
						// location
						data1[1] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[2]/h6"))
								.getText();
						// Order state
						data1[2] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[3]/h6"))
								.getText();
						// type
						data1[3] = driver.findElement(By.xpath(
								"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[1]/div/span"))
								.getText();
						// lost sale
						data1[4] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[2]/h6"))
								.getText();
						// net payout
						data1[5] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[3]/h6"))
								.getText();
						// payout id
						data1[6] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[4]/h6"))
								.getText();
						// payout date
						data1[7] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[5]/h6"))
								.getText();
						writer.writeNext(data1);
						p += 1; // 3, 4
					}

				}
				// p = p + 1; // 4, 8+2+1=11
			} else {
				c = p;
				for (int j = 0; j < 2; j++) {
					// c = p;
					// 2nd order id should be tr4 as tr 3 is for first order line
					// order id
					if (j == 0) {

						data1[0] = driver.findElement(By.xpath(
								"//div[@class='MuiBox-root css-mycntp']/table//tbody//tr[" + c + "]//td[1]/div/h6"))
								.getText();
						// location
						data1[1] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[2]/h6"))
								.getText();
						// Order state
						data1[2] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[3]/h6"))
								.getText();
						// type
						data1[3] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 4) + "]/div/span")).getText();
						// lost sale
						data1[4] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 5) + "]/h6")).getText();
						// net payout
						data1[5] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 6) + "]/h6")).getText();
						// payout id
						data1[6] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 7) + "]/h6")).getText();
						// payout date
						data1[7] = driver.findElement(By.xpath("//div[@class='MuiBox-root css-mycntp']/table/tbody/tr["
								+ p + "]/td[" + (j + 8) + "]/h6")).getText();
						writer.writeNext(data1);
						p += 1;
					} else {

						data1[0] = driver.findElement(By.xpath(
								"//div[@class='MuiBox-root css-mycntp']/table//tbody//tr[" + c + "]//td[1]/div/h6"))
								.getText();
						// location
						data1[1] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[2]/h6"))
								.getText();
						// Order state
						data1[2] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + c + "]/td[3]/h6"))
								.getText();
						// type
						data1[3] = driver.findElement(By.xpath(
								"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[1]/div/span"))
								.getText();
						// lost sale
						data1[4] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[2]/h6"))
								.getText();
						// net payout
						data1[5] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[3]/h6"))
								.getText();
						// payout id
						data1[6] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[4]/h6"))
								.getText();
						// payout date
						data1[7] = driver
								.findElement(By.xpath(
										"//div[@class='MuiBox-root css-mycntp']/table/tbody/tr[" + p + "]/td[5]/h6"))
								.getText();
						writer.writeNext(data1);
						p += 1;
					}

				}
				// p = p + 1; // 4+1+1=6, 6+1+1=8
			}

			// add data
			writer.flush();

			// writer.close();
		}
		writer.close();
	}

	public void sortCSVFile(String filepath) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter("Files\\transactions1.csv"));
		String csvFile = filepath;
		String[] data = new String[8];
		List<String> l = new ArrayList();
		String line = "";
		String cvsSplitBy = ",";
		double a, b;
		List<List<String>> llp = new ArrayList<>();
		List<List<String>> list = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
			llp.add(Arrays.asList(line.split(cvsSplitBy)));
		}
		llp.sort(new Comparator<List<String>>() {
			@Override
			public int compare(List<String> o1, List<String> o2) {
				return o1.get(1).compareTo(o2.get(1));
			}
		});
		// System.out.println(llp);
		for (int i = 0; i <= 32; i++)

		{
			a = Double.parseDouble(llp.get(i).get(0).split("-")[1].split("\"")[0]);
			for (int j = i + 1; j <= 32; j++) {
				// System.out.println(llp.get(j));
				b = Double.parseDouble(llp.get(j).get(0).split("-")[1].split("\"")[0]);
				if (a > b) // 1st < 2nd
				{
					l = llp.get(i);
					llp.set(i, llp.get(j));
					llp.set(j, l);
				}

			}

		}
		//System.out.println(llp);
		for (int k = 0; k <= 7; k++) {
			data[k] = llp.get(33).get(k).split("\"")[1].split("\"")[0];
		}
		writer.writeNext(data);
		writer.flush();

		for (int m = 0; m <= 32; m++) {
			for (int t = 0; t <= 6; t++) {
				data[t] = llp.get(m).get(t).split("\"")[1].split("\"")[0];
			}
			data[7] = llp.get(m).get(7).strip().split("\"")[1].split("\"")[0];
			writer.writeNext(data);
		}

		writer.flush();

	}
}
