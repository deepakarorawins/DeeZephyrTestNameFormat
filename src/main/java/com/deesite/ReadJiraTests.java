package com.deesite;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.CaseUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadJiraTests {
	public static RemoteWebDriver driver = null;

	public static void main(String[] args) {
		try {
			initializeDriver();
			List<WebElement> testIds = driver.findElements(By.xpath("//table[@class='aui']/tbody/tr/td[3]/a"));
			List<WebElement> tests = driver.findElements(By.xpath("//table[@class='aui']/tbody/tr/td[5]/a/span"));
			// List<WebElement> nextButton =
			// driver.findElements(By.xpath("//button[@aria-label='next']"));

			testNames(testIds, tests);
			testMethodNames(tests);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("finally block executed");
			driver.quit();
		}

	}

	public static void initializeDriver() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
		driver = new ChromeDriver(chromeOptions);
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.switchTo().frame(0);
	}

	public static List<String> testNames(List<WebElement> testIds, List<WebElement> tests) {
		System.out.println("********** Start creating Test Names  **********");
		List<String> testNames = new ArrayList<>();
		for (int i = 0; i < testIds.size(); i++) {
			String testId = testIds.get(i).getText();
			testId = testId.replaceAll("-", "_");

			String testTitle = tests.get(i).getAttribute("title");
			testTitle = " " + testTitle;
			testTitle = testTitle.replaceAll(".*[0]", " ").toLowerCase();
			testTitle = testTitle.replaceAll("-", " ").toLowerCase();
			testTitle = testTitle.replaceAll("\\s+", "_").toLowerCase();

			System.out.println(testId + "" + testTitle);
			String testName = testId + "" + testTitle;
			testNames.add(testName);
		}
		System.out.println("********** End creating Test Names  **********");
		return testNames;
	}

	public static List<String> testMethodNames(List<WebElement> tests) {
		System.out.println("********** Start creating Test Method Names  **********");
		List<String> testMethodNames = new ArrayList<>();
		for (int i = 0; i < tests.size(); i++) {
			String testTitle = tests.get(i).getAttribute("title");
			testTitle = testTitle.replaceAll(".*[0]", " ").toLowerCase();
			testTitle = testTitle.replaceAll("-", " ").toLowerCase();
			testTitle = testTitle.replaceAll("\\s+", "_").toLowerCase();
			testTitle = CaseUtils.toCamelCase(testTitle, false, '_');

			System.out.println(testTitle);
			testMethodNames.add(testTitle);
		}
		System.out.println("********** End creating Test Method Names  **********");
		return testMethodNames;
	}

}
