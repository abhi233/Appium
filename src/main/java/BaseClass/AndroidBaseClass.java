package BaseClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.android.FormPage;
import utils.AppiumUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class AndroidBaseClass {

	public static AndroidDriver driver;
	public static  AppiumDriverLocalService service;
	//public static FormPage formPage;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentSparkReporter spark;
	public static ITestResult result;

	@BeforeClass(alwaysRun=true)
	public void ConfigureAppium() throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/data.properties");
		prop.load(fis);
		String ipAddress = prop.getProperty("ipAddress");
		System.out.println(ipAddress);
		String port = prop.getProperty("port");
			
		service = AppiumUtils.startAppiumServer(ipAddress,Integer.parseInt(port));
			
								
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(prop.getProperty("AndroidDeviceNames")); //emulator
			options.setApp(System.getProperty("user.dir")+"//src//main//resources//General-Store.apk");
			 driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
			 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			 //formPage= new FormPage(driver);
	}


	@BeforeSuite(alwaysRun=true)
	public void extentReportSetUp() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String path = System.getProperty("user.dir") + "//reports//DoorApp//TestResult" + now.toString().replaceAll(":", "-") + ".html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(path);
			reporter.config().setReportName("Automation Report");
			reporter.config().setDocumentTitle("Test Results");
			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Tester", System.getProperty("user.name"));
			extent.setSystemInfo("Machine", InetAddress.getLocalHost().getHostName());
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
	}

	@BeforeMethod(alwaysRun=true)
	public void methodSetup(ITestResult result) {
		Activity activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.MainActivity");
		//driver.startActivity(activity);
		test = extent.createTest(result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
        service.stop();
		}

	@AfterMethod(alwaysRun=true)
	public void getResult(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				//MarkupHelper is used to display the output in different colors
				test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
				// test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
				String screenshotPath = AppiumUtils.getScreenshotPath(result.getMethod().getMethodName(), driver);
				test.fail("Test Case Failed Snapshot is Above " + test.addScreenCaptureFromPath(screenshotPath));

			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
			}
			extent.flush();
		} catch (Exception e) {

		}
	}
	}
